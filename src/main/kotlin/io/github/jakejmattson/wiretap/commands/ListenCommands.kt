package io.github.jakejmattson.wiretap.commands

import io.github.jakejmattson.wiretap.services.WatchService
import me.aberrantfox.kjdautils.api.dsl.*
import me.aberrantfox.kjdautils.extensions.jda.fullName
import me.aberrantfox.kjdautils.internal.command.arguments.*
import net.dv8tion.jda.core.entities.User
import java.awt.Color

@CommandSet("listen")
fun listenCommands(watchService: WatchService) = commands {

	command("ListenTo") {
		description = "Listen to a target user."
		expect(UserArg)
		execute {
			val user = it.args.component1() as User

			if (user.isBot) return@execute it.respond("Invalid ID (bot)")

			it.respond(
				if (watchService.watchUser(user))
					createSuccessEmbed("Now listening to all messages from ${user.fullName()} (${user.asMention})")
				else
					createFailureEmbed("Messages from ${user.fullName()} (${user.asMention}) are already being listened for.")
			)
		}
	}

	command("ListenFor") {
		description = "Listen for a target word."
		expect(SentenceArg)
		execute {
			val word = it.args.component1() as String

			it.respond(
				if (watchService.watchWord(word))
					createSuccessEmbed("Now listening for all messages containing \"$word\"")
				else
					createFailureEmbed("Messages containing \"$word\" are already being listened for.")
			)
		}
	}

	command("IgnoreUser") {
		description = "Ignore previously listened user."
		expect(UserArg)
		execute {
			val user = it.args.component1() as User
			val name = user.fullName()

			it.respond(
				if (watchService.remove(user))
					createSuccessEmbed("Successfully removed $name from the watchlist")
				else
					createFailureEmbed("Failed to remove $name from the watchlist")
			)
		}
	}

	command("IgnoreWord") {
		description = "Ignore previously listened word."
		expect(SentenceArg)
		execute {
			val word = it.args.component1() as String
			val display = "\"$word\""

			it.respond(
				if (watchService.remove(word))
					createSuccessEmbed("Successfully removed $display from the watchlist")
				else
					createFailureEmbed("Failed to remove $display from the watchlist")
			)
		}
	}

	command("ListUsers") {
		description = "List currently watched users."
		execute {
			val users = watchService.getUsersAsString()

			it.respond(
				if (users.isNotEmpty()) "**Watching the following users:**\n$users" else "Not currently watching any users.")
		}
	}

	command("ListWords") {
		description = "List currently watched wards."
		execute {
			val words = watchService.getWordsAsString()

			it.respond(
				if (words.isNotEmpty()) "**Watching the following words:**\n$words" else "Not currently watching any words.")
		}
	}
}

fun createSuccessEmbed(message: String) =
	embed {
		addField("Success!", message, false)
		color(Color.green)
	}

fun createFailureEmbed(message: String) =
	embed {
		addField("Failure!", message, false)
		color(Color.red)
	}