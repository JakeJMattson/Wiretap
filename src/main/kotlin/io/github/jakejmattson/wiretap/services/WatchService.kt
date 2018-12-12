package io.github.jakejmattson.wiretap.services

import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.entities.*

data class WatchedUser(val user: User, val channel: TextChannel)

class WatchService(val jda: JDA, config: Configuration) {
	private val userList: ArrayList<WatchedUser> = ArrayList<WatchedUser>()
	private val wordList = ArrayList<String>()
	val wordLog = jda.getTextChannelById(config.wordLogChannel)

	fun add(user: User, channel: TextChannel) = userList.add(WatchedUser(user, channel))
	fun add(word: String) = wordList.add(word)

	fun remove(watchedUser: WatchedUser) = userList.remove(watchedUser)
	fun remove(user: User) = userList.remove(userList.firstOrNull { it.user == user })
	fun remove(word: String) = wordList.remove(word)

	fun getWatched(user: User) =  userList.firstOrNull { user == it.user }
	fun getWatched(channel: TextChannel) = userList.firstOrNull { channel == it.channel }
	fun hasWatchedWord(content: String) = wordList.any { content.contains(it) }
}