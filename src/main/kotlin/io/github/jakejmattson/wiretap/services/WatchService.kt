package io.github.jakejmattson.wiretap.services

import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.entities.*
import java.io.File

data class WatchedUser(val userId: String, val channelId: String)

data class Watched(val userList: MutableList<WatchedUser> = ArrayList<WatchedUser>(),
				   val wordList: MutableList<String> = ArrayList<String>())

class WatchService(private val jda: JDA, private val config: Configuration) {

	private val backupDir = File("backup/")
	private val backupFile = File("${backupDir.name}/backup.json")
	private val wordLog = jda.getTextChannelById(config.wordLogChannel)
	private val category = jda.getCategoryById(config.watchCategory)
	private val watched: Watched = loadBackup()

	fun watchUser(user: User) = category.createTextChannel(user.name).queue { channel ->
		watched.userList.addAndSave(WatchedUser(user.id, channel.id))
	}

	fun watchWord(word: String) = watched.wordList.addAndSave(word)

	fun remove(user: User) = watched.userList.removeAndSave(getWatched(user))
	fun remove(channel: TextChannel) = watched.userList.removeAndSave(getWatched(channel))
	fun remove(word: String) = watched.wordList.removeAndSave(word)

	fun getWatched(user: User) =  watched.userList.firstOrNull { it.userId == user.id }
	fun getWatched(channel: TextChannel) = watched.userList.firstOrNull { it.channelId == channel.id }
	fun hasWatchedWord(content: String) = watched.wordList.any { content.contains(it) }

	fun logUser(user: User, embed: MessageEmbed) = jda.getTextChannelById(getWatched(user)?.channelId).sendMessage(embed).queue()
	fun logWord(embed: MessageEmbed) = wordLog.sendMessage(embed).queue()

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