package me.jakejmattson.wiretap

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import me.jakejmattson.discordkt.api.dsl.bot
import me.jakejmattson.discordkt.api.extensions.*
import me.jakejmattson.wiretap.services.Configuration
import java.awt.Color

@Serializable
private data class Properties(val version: String, val author: String, val repository: String)

private val propFile = Properties::class.java.getResource("/properties.json").readText()
private val project = Json.decodeFromString<Properties>(propFile)

suspend fun main(args: Array<String>) {
    val token = args.first()

    bot(token) {
        prefix { "?" }

        mentionEmbed {
            val self = it.discord.api.getSelf()
            val configuration = it.discord.getInjectionObjects<Configuration>()

            color = Color(0x00bfff)

            thumbnail {
                url = self.avatar.url
            }

            addField(self.tag, "The friendly eavesdropping bot.")
            addInlineField("Required role", configuration.requiredRoleName)
            addInlineField("Prefix", it.prefix())
            addInlineField("Author", "[${project.author}](https://discordapp.com/users/254786431656919051/)")
            addInlineField("Version", project.version)
            addInlineField("Source", project.repository)
        }
    }
}