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

			if (user.isBot) {
				it.respond("Invalid ID (bot)")
				return@execute
			}

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
}

fun createSuccessEmbed(message: String) =
	embed {
		field {
			name = "Success!"
			value = message
			inline = false
		}
		color(Color.green)
	}

fun createFailureEmbed(message: String) =
	embed {
		field {
			name = "Failure!"
			value = message
			inline = false
		}
		color(Color.red)
	}