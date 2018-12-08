package io.github.jakejmattson.wiretap.services

import net.dv8tion.jda.core.entities.*

data class WatchedUser(val user: User, val channel: TextChannel)

class WatchService(val wordLog: TextChannel) {
	private val userList: ArrayList<WatchedUser> = ArrayList<WatchedUser>()
	private val wordList = ArrayList<String>()

	fun add(user: User, channel: TextChannel) = userList.add(WatchedUser(user, channel))
	fun add(word: String) = wordList.add(word)

	fun isUserWatched(user: User) =  userList.firstOrNull { user == it.user }
	fun hasWatchedWord(content: String) = wordList.any { content.contains(it) }
}