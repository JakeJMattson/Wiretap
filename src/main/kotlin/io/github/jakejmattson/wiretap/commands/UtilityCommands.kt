package io.github.jakejmattson.wiretap.commands

import com.google.gson.Gson
import me.aberrantfox.kjdautils.api.dsl.*
import me.aberrantfox.kjdautils.extensions.jda.fullName
import java.awt.Color
import java.util.*

private data class Properties(val version: String, val author: String, val repository: String)
private val propFile = Properties::class.java.getResource("/properties.json").readText()
private val Project = Gson().fromJson(propFile, Properties::class.java)
private val startTime = Date()

@CommandSet("utility")
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
			it.respond(embed {
				title(it.jda.selfUser.fullName())
				description("The friendly eavesdropping bot.")
				color(Color.green)
				setThumbnail(it.jda.selfUser.effectiveAvatarUrl)
				addField("Author", Project.author, false)
				addField("Source", Project.repository, false)
				addField("Version", Project.version, false)
			})
		}
	}

	command("Ping") {
		description = "Display network status."
		execute {
			it.respond("**Pinged in**: ${it.jda.ping}ms")
		}
	}

	command("Uptime") {
		description = "Display how long the bot has been running."
		execute {
			val milliseconds = Date().time - startTime.time
			val seconds = (milliseconds / 1000) % 60
			val minutes = (milliseconds / (1000 * 60)) % 60
			val hours = (milliseconds / (1000 * 60 * 60)) % 24
			val days = (milliseconds / (1000 * 60 * 60 * 24))

			it.respond(embed {
				title("I have been running since")
				description(startTime.toString())
				color(Color.WHITE)

				field {
					name = "That's been"
					value = "$days day(s), $hours hour(s), $minutes minute(s) and $seconds second(s)"
				}
			})
		}
	}
}