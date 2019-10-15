package io.github.jakejmattson.wiretap.services

import com.google.gson.Gson
import me.aberrantfox.kjdautils.api.annotation.Service
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.discord.Discord
import me.aberrantfox.kjdautils.extensions.jda.*
import java.awt.Color

@Service
class StartupService(configuration: Configuration, discord: Discord) {
    private data class Properties(val version: String, val author: String, val repository: String)

    private val propFile = Properties::class.java.getResource("/properties.json").readText()
    private val project = Gson().fromJson(propFile, Properties::class.java)

    init {
        with(discord.configuration) {
            prefix = "?"

            mentionEmbed = {
                embed {
                    val self = it.guild.jda.selfUser

                    color = Color(0x00bfff)
                    thumbnail = self.effectiveAvatarUrl
                    addField(self.fullName(), "The friendly eavesdropping bot.")
                    addInlineField("Required role", configuration.requiredRoleName)
                    addInlineField("Prefix", prefix)
                    addInlineField("Author", "[${project.author}](https://discordapp.com/users/254786431656919051/)")
                    addInlineField("Version", project.version)
                    addInlineField("Source", project.repository)
                }
            }
        }
    }
}