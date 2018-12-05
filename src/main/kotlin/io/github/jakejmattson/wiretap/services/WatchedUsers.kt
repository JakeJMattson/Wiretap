package io.github.jakejmattson.wiretap.services

import net.dv8tion.jda.core.entities.*

data class WatchedUser(val user: User, val channel: TextChannel)

class WatchedUsers(private val watchlist: ArrayList<WatchedUser> = ArrayList<WatchedUser>()) {

	fun add(user: User, channel: TextChannel) = watchlist.add(WatchedUser(user, channel))

	fun isUserWatched(user: User) =  watchlist.firstOrNull { user == it.user }
}