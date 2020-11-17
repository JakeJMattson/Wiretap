package me.jakejmattson.wiretap.services

import com.google.gson.Gson
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.annotations.Service
import me.jakejmattson.discordkt.api.extensions.jda.fullName
import java.awt.Color

@Service
class StartupService(configuration: Configuration, discord: Discord) {
    private data class Properties(val version: String, val author: String, val repository: String)

    private val propFile = Properties::class.java.getResource("/properties.json").readText()
    private val project = Gson().fromJson(propFile, Properties::class.java)

    init {
        with(discord.configuration) {
            prefix { "?" }

            mentionEmbed {
                val self = it.guild!!.jda.selfUser

                color = Color(0x00bfff)
                thumbnail = self.effectiveAvatarUrl
                addField(self.fullName(), "The friendly eavesdropping bot.")
                addInlineField("Required role", configuration.requiredRoleName)
                addInlineField("Prefix", "?")
                addInlineField("Author", "[${project.author}](https://discordapp.com/users/254786431656919051/)")
                addInlineField("Version", project.version)
                addInlineField("Source", project.repository)
            }
        }
    }
}