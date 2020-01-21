package me.jakejmattson.wiretap.services

import com.google.gson.*
import me.jakejmattson.wiretap.extensions.*
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.extensions.jda.fullName
import net.dv8tion.jda.api.entities.*
import java.io.File

data class WatchedUser(val userId: String, val channelId: String) {
    override fun toString() = userId.idToUser()?.fullName() ?: userId
}

data class Watched(val userList: MutableList<WatchedUser> = ArrayList(),
                   val wordList: MutableList<String> = ArrayList())

@Service
class WatchService(private val config: Configuration, val init: JdaInitializer) {
    private val backupFile = File("backup/backup.json")
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val wordLog = config.wordLogChannel.idToChannel()
    private val category = config.watchCategory.idToCategory()
    private val watched: Watched = loadBackup()
    private val userList = watched.userList
    private val wordList = watched.wordList

    fun watchUser(user: User): Boolean {
        if (user.isWatched())
            return false

        category?.createTextChannel(user.name)?.queue { channel ->
            userList.addAndSave(WatchedUser(user.id, channel.id))
        }

        return true
    }

    fun watchWord(word: String) = if (!hasWatchedWord(word)) wordList.addAndSave(word) else false

    fun remove(user: User) = userList.removeAndSave(getWatched(user))
    fun remove(channel: TextChannel) = userList.removeAndSave(getWatched(channel))
    fun remove(word: String) = wordList.removeAndSave(word)

    fun getWatched(user: User) = userList.firstOrNull { it.userId == user.id }
    fun getWatched(channel: TextChannel) = userList.firstOrNull { it.channelId == channel.id }
    fun hasWatchedWord(content: String) = wordList.any { content.contains(it) }

    fun logUser(user: User, embed: MessageEmbed) = getWatched(user)?.channelId!!.idToChannel()?.sendMessage(embed)?.queue()
    fun logWord(embed: MessageEmbed) = wordLog?.sendMessage(embed)?.queue()

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

    private fun <T> MutableList<T>.addAndSave(element: T) = this.add(element).also { save() }
    private fun <T> MutableList<T>.removeAndSave(element: T?) = this.remove(element).also { save() }
    private fun User.isWatched() = userList.any { it.userId == this.id }
}