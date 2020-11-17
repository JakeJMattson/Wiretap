package me.jakejmattson.wiretap.extensions

import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.annotations.Service
import net.dv8tion.jda.api.JDA

private lateinit var jda: JDA

@Service
class JdaInitializer(discord: Discord) { init {
    jda = discord.jda
}
}

fun String.idToUser() = jda.getUserById(this)
fun String.idToChannel() = jda.getTextChannelById(this)
fun String.idToCategory() = jda.getCategoryById(this)