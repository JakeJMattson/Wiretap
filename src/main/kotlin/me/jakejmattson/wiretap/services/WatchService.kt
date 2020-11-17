package me.jakejmattson.wiretap.services

import com.gitlab.kordlib.core.behavior.channel.createMessage
import com.gitlab.kordlib.core.behavior.createTextChannel
import com.gitlab.kordlib.core.entity.User
import com.gitlab.kordlib.core.entity.channel.*
import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.annotations.Service
import me.jakejmattson.discordkt.api.extensions.toSnowflake
import java.io.File

data class WatchedUser(val userId: String, val channelId: String) {
    override fun toString() = userId
}

data class Watched(val userList: MutableList<WatchedUser> = mutableListOf(),
                   val wordList: MutableList<String> = mutableListOf())

@Service
class WatchService(private val discord: Discord,
                   private val config: Configuration) {
    private val backupFile = File("backup/backup.json")
    private val wordLog = runBlocking { discord.api.getChannelOf<TextChannel>(config.wordLogChannel.toSnowflake()) }
    private val category = runBlocking { discord.api.getChannelOf<Category>(config.watchCategory.toSnowflake()) }
    private val watched = loadBackup()
    private val userList = watched.userList
    private val wordList = watched.wordList

    suspend fun watchUser(user: User): Boolean {
        if (user.isWatched())
            return false

        val channel = category?.guild?.createTextChannel {
            name = user.username
            parentId = category.id
        } ?: return false

        userList.addAndSave(WatchedUser(user.id.value, channel.id.value))
        return true
    }

    fun watchWord(word: String) = if (!hasWatchedWord(word)) wordList.addAndSave(word) else false

    fun remove(user: User) = userList.removeAndSave(getWatched(user))
    fun remove(channel: TextChannel) = userList.removeAndSave(getWatched(channel))
    fun remove(word: String) = wordList.removeAndSave(word)

    fun getWatched(user: User) = userList.firstOrNull { it.userId == user.id.value }
    fun getWatched(channel: TextChannel) = userList.firstOrNull { it.channelId == channel.id.value }
    fun hasWatchedWord(content: String) = wordList.any { content.contains(it) }

    suspend fun logUser(user: User, embed: EmbedBuilder.() -> Unit) {
        val watchedUser = getWatched(user) ?: return
        val channel = user.kord.getChannelOf<TextChannel>(watchedUser.channelId.toSnowflake()) ?: return

        channel.createMessage {
            embed(embed)
        }
    }

    suspend fun logWord(embed: EmbedBuilder.() -> Unit) = wordLog?.createMessage {
        embed(embed)
    }

    fun getUsersAsString() = userList.joinToString("\n")
    fun getWordsAsString() = wordList.joinToString("\n")

    private fun save() {
        if (config.recoverWatched)
            backupFile.writeText(Json.encodeToString(watched))
    }

    private fun loadBackup(): Watched {
        val parent = backupFile.parentFile

        if (config.recoverWatched && !backupFile.exists()) {
            parent.mkdirs()
            backupFile.writeText(Json.encodeToString(Watched()))
        }

        if (!config.recoverWatched && parent.exists())
            parent.deleteRecursively()

        return if (config.recoverWatched && backupFile.exists())
            Json.decodeFromString(backupFile.readText())
        else
            Watched()
    }

    private fun <T> MutableList<T>.addAndSave(element: T) = this.add(element).also { save() }
    private fun <T> MutableList<T>.removeAndSave(element: T?) = this.remove(element).also { save() }
    private fun User.isWatched() = userList.any { it.userId == id.value }
}