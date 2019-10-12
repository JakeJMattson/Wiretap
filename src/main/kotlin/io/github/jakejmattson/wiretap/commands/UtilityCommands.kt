package io.github.jakejmattson.wiretap.commands

import com.google.gson.Gson
import io.github.jakejmattson.wiretap.extensions.*
import me.aberrantfox.kjdautils.api.dsl.command.*
import me.aberrantfox.kjdautils.extensions.jda.fullName
import java.util.*

private data class Properties(val version: String, val author: String, val repository: String)
private val propFile = Properties::class.java.getResource("/properties.json").readText()
private val Project = Gson().fromJson(propFile, Properties::class.java)
private val startTime = Date()

@CommandSet("Utility")
fun utilityCommands() = commands {
	command("Author") {
		description = "Display project author."
		execute {
			it.respond("**Project Author**: ${Project.author}")
		}
	}

	command("Source") {
		description = "Display repository link."
		execute {
			it.respond("**Project Repository**: ${Project.repository}")
		}
	}

	command("Version") {
		description = "Display bot version."
		execute {
			it.respond("**Running Version**: ${Project.version}")
		}
	}

	command("BotInfo") {
		description = "Display various bot information."
		execute {
			it.respondEmbed {
				title = it.discord.jda.selfUser.fullName()
				description = "The friendly eavesdropping bot."
				thumbnail = it.discord.jda.selfUser.effectiveAvatarUrl
				addField("Author", Project.author, false)
				addField("Source", Project.repository, false)
				addField("Version", Project.version, false)
			}
		}
	}

	command("Ping") {
		description = "Display network status."
		execute {
			it.respond("**Pinged in**: ${it.discord.jda.gatewayPing}ms")
		}
	}

	command("Uptime") {
		description = "Displays how long the bot has been running."
		execute {
			val seconds = (Date().time - startTime.time) / 1000

			it.respondEmbed {
				title = "I have been running since"
				description = startTime.toString()

				field {
					name = "That's been"
					value = seconds.toMinimalTimeString()
				}
			}
		}
	}
}