package io.github.jakejmattson.wiretap.commands

import com.google.gson.Gson
import io.github.jakejmattson.wiretap.extensions.asTimeString
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
			val seconds = (Date().time - startTime.time) / 1000

			it.respond(embed {
				title("I have been running since")
				description(startTime.toString())
				color(Color.WHITE)
				addField("That's been", seconds.asTimeString(), false)
			})
		}
	}
}