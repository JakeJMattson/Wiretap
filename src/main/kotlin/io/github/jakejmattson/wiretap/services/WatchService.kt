package io.github.jakejmattson.wiretap.services

import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.entities.*

data class WatchedUser(val user: User, val channel: TextChannel)

data class Watched(val userList: ArrayList<WatchedUser> = ArrayList<WatchedUser>(),
				   val wordList: ArrayList<String> = ArrayList<String>())

class WatchService(jda: JDA, config: Configuration) {
	private val watched = Watched()
	private val userList = watched.userList
	private val wordList = watched.wordList

	private val wordLog = jda.getTextChannelById(config.wordLogChannel)
	private val category = jda.getCategoryById(config.watchCategory)

	fun watchUser(user: User) = category.createTextChannel(user.name).queue { channel ->
		userList.add(WatchedUser(user, channel as TextChannel))
	}

	fun watchWord(word: String) = wordList.add(word)

	fun remove(user: User) = userList.remove(getWatched(user))
	fun remove(channel: TextChannel) = userList.remove(getWatched(channel))
	fun remove(word: String) = wordList.remove(word)

	fun getWatched(user: User) =  userList.firstOrNull { it.user == user }
	fun getWatched(channel: TextChannel) = userList.firstOrNull { it.channel == channel }
	fun hasWatchedWord(content: String) = wordList.any { content.contains(it) }

	fun log(embed: MessageEmbed) = wordLog.sendMessage(embed).queue()
}