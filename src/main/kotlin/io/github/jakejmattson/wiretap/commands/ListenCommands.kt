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

			watchService.watchUser(user)

			it.respond(embed {
				field {
					name = "Listening to user!"
					value = "Now listening to all messages from ${user.fullName()} (${user.asMention})"
					inline = false
				}
				color(Color.green)
			})
		}
	}

	command("ListenFor") {
		description = "Listen for a target word."
		expect(SentenceArg)
		execute {
			val word = it.args.component1() as String
			watchService.watchWord(word)

			it.respond(embed {
				addField("Listening for word!", "Now listening for all messages containing \"$word\"", false)
				color(Color.green)
			})
		}
	}

	command("IgnoreUser") {
		description = "Ignore previously listened user."
		expect(UserArg)
		execute {
			val user = it.args.component1() as User
			val name = user.fullName()
			val wasRemoved = watchService.remove(user)

			it.respond(if (wasRemoved) createSuccessEmbed(name) else createFailureEmbed(name))
		}
	}

	command("IgnoreWord") {
		description = "Ignore previously listened word."
		expect(SentenceArg)
		execute {
			val word = it.args.component1() as String
			val display = "\"$word\""
			val wasRemoved = watchService.remove(word)

			it.respond(if (wasRemoved) createSuccessEmbed(display) else createFailureEmbed(display))
		}
	}
}

fun createSuccessEmbed(target: String) =
	embed {
		field {
			name = "Success!"
			value = "Successfully removed $target from the watchlist"
			inline = false
		}
		color(Color.green)
	}

fun createFailureEmbed(target: String) =
	embed {
		field {
			name = "Failure!"
			value = "Failed to remove $target from the watchlist"
			inline = false
		}
		color(Color.red)
	}