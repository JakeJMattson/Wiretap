package io.github.jakejmattson.wiretap.commands

import com.google.gson.Gson
import me.aberrantfox.kjdautils.api.dsl.*
import me.aberrantfox.kjdautils.extensions.jda.fullName
import java.awt.Color

private data class Properties(val version: String, val author: String, val repository: String)
private val propFile = Properties::class.java.getResource("/properties.json").readText()
private val Project = Gson().fromJson(propFile, Properties::class.java)

@CommandSet("utility")
fun utilityCommands() = commands {

	command("Author") {
		description = "Display project author."
		execute {
			it.respond("**Project Author**: ${Project.author}")
		}
	}

	command("Source") {
		description = "Display the repository link."
		execute {
			it.respond("**Project Repository**: ${Project.repository}")
		}
	}

	command("Version") {
		description = "Display the bot version."
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
				setColor(Color.green)
				setThumbnail(it.jda.selfUser.effectiveAvatarUrl)
				addField("Creator", Project.author, false)
				addField("Source", Project.repository, false)
				addField("Version", Project.version, false)
			})
		}
	}
}