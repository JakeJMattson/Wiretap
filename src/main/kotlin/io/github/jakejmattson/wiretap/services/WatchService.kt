package io.github.jakejmattson.wiretap.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.jakejmattson.wiretap.extensions.*
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.extensions.jda.fullName
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.entities.*
import java.io.File

data class WatchedUser(val userId: String, val channelId: String) {
	override fun toString() = userId.idToUser().fullName()
}

data class Watched(val userList: MutableList<WatchedUser> = ArrayList(),
				   val wordList: MutableList<String> = ArrayList())

@Service
class WatchService(jda: JDA, private val config: Configuration) {
	private val backupFile = File("backup/backup.json")
	private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
	private val wordLog = jda.getTextChannelById(config.wordLogChannel)
	private val category = jda.getCategoryById(config.watchCategory)
	private val watched: Watched = loadBackup()
	private val userList = watched.userList
	private val wordList = watched.wordList

	fun watchUser(user: User): Boolean {
		if (user.isWatched())
			return false

		category.createTextChannel(user.name).queue { channel ->
			userList.addAndSave(WatchedUser(user.id, channel.id))
		}

		return true
	}
	fun watchWord(word: String) = if (!hasWatchedWord(word)) wordList.addAndSave(word) else false

	fun remove(user: User) = userList.removeAndSave(getWatched(user))
	fun remove(channel: TextChannel) = userList.removeAndSave(getWatched(channel))
	fun remove(word: String) = wordList.removeAndSave(word)

	fun getWatched(user: User) =  userList.firstOrNull { it.userId == user.id }
	fun getWatched(channel: TextChannel) = userList.firstOrNull { it.channelId == channel.id }
	fun hasWatchedWord(content: String) = wordList.any { content.contains(it) }

	fun logUser(user: User, embed: MessageEmbed) = getWatched(user)?.channelId!!.idToChannel().sendMessage(embed).queue()
	fun logWord(embed: MessageEmbed) = wordLog.sendMessage(embed).queue()

	fun getUsersAsString() = userList.joinToString("\n")
	fun getWordsAsString() = wordList.joinToString("\n")

	private fun save() {
		if (config.recoverWatched)
			backupFile.writeText(gson.toJson(watched))
	}

	private fun loadBackup(): Watched {
		val parent = backupFile.parentFile

		if (config.recoverWatched && !backupFile.exists()) {
			parent.mkdirs()
			backupFile.writeText(gson.toJson(Watched()))
		}

		if (!config.recoverWatched && parent.exists())
			parent.deleteRecursively()

		return if (config.recoverWatched && backupFile.exists())
			gson.fromJson(backupFile.readText(), Watched::class.java)
		else
			Watched()
	}

	private fun MutableList<WatchedUser>.addAndSave(watchedUser: WatchedUser) = add(watchedUser).apply { save() }
	private fun MutableList<WatchedUser>.removeAndSave(watchedUser: WatchedUser?) = remove(watchedUser).apply { save() }
	private fun MutableList<String>.addAndSave(string: String) = add(string).apply { save() }
	private fun MutableList<String>.removeAndSave(string: String) = remove(string).apply { save() }
	private fun User.isWatched() = userList.any { it.userId == this.id }
}