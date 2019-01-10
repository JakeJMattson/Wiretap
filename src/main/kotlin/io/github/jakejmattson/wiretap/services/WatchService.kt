package io.github.jakejmattson.wiretap.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.jakejmattson.wiretap.Project.jda
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.extensions.jda.fullName
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.entities.*
import java.io.File

data class WatchedUser(val userId: String, val channelId: String) {
	override fun toString() = jda.getUserById(userId).fullName()
}

data class Watched(val userList: MutableList<WatchedUser> = ArrayList<WatchedUser>(),
				   val wordList: MutableList<String> = ArrayList<String>())

@Service
class WatchService(private val jda: JDA, private val config: Configuration) {
	private val backupDir = File("backup/")
	private val backupFile = File("${backupDir.name}/backup.json")
	private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
	private val wordLog = jda.getTextChannelById(config.wordLogChannel)
	private val category = jda.getCategoryById(config.watchCategory)
	private val watched: Watched = loadBackup()
	private val userList = watched.userList
	private val wordList = watched.wordList

	fun watchUser(user: User): Boolean {
		if (getWatched(user) != null)
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

	fun logUser(user: User, embed: MessageEmbed) = jda.getTextChannelById(getWatched(user)?.channelId).sendMessage(embed).queue()
	fun logWord(embed: MessageEmbed) = wordLog.sendMessage(embed).queue()

	fun getUsersAsString() = userList.joinToString("\n")
	fun getWordsAsString() = wordList.joinToString("\n")

	private fun save() {
		if (config.recoverWatched)
			backupFile.writeText(gson.toJson(watched))
	}

	private fun MutableList<WatchedUser>.addAndSave(watchedUser: WatchedUser): Boolean {
		val result = this.add(watchedUser)
		save()
		return result
	}

	private fun MutableList<WatchedUser>.removeAndSave(watchedUser: WatchedUser?): Boolean {
		val result = this.remove(watchedUser)
		save()
		return result
	}

	private fun MutableList<String>.addAndSave(string: String): Boolean {
		val result = this.add(string)
		save()
		return result
	}

	private fun MutableList<String>.removeAndSave(string: String): Boolean {
		val result = this.remove(string)
		save()
		return result
	}

	private fun loadBackup(): Watched {
		if (config.recoverWatched && !backupFile.exists()) {
			backupDir.mkdirs()
			backupFile.writeText(gson.toJson(Watched()))
		}

		if (!config.recoverWatched && backupDir.exists())
			backupDir.deleteRecursively()

		return if (config.recoverWatched && backupFile.exists())
			gson.fromJson(backupFile.readText(), Watched::class.java)
		else
			Watched()
	}
}