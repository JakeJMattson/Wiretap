package io.github.jakejmattson.wiretap.commands

import io.github.jakejmattson.wiretap.services.WatchService
import me.aberrantfox.kjdautils.api.dsl.*
import me.aberrantfox.kjdautils.extensions.jda.fullName
import me.aberrantfox.kjdautils.extensions.stdlib.isLong
import me.aberrantfox.kjdautils.internal.command.arguments.*
import net.dv8tion.jda.core.entities.*
import java.awt.Color

@CommandSet("listen")
fun listenCommands(watchService: WatchService, category: Category) = commands {

	command("ListenTo") {
		description = "Listen to a target user."
		expect(UserArg)
		execute {
			val user = it.args.component1() as User

			if (user.isBot) {
				it.respond("Invalid ID (bot)")
				return@execute
			}

			category.createTextChannel(user.name).queue { channel ->
				watchService.add(user, channel as TextChannel)

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
	}

	command("ListenFor") {
		description = "Listen for a target word."
		expect(SentenceArg)
		execute {
			val word = it.args.component1() as String
			watchService.add(word)

			it.respond(embed {
				addField("Listening for word!", "Now listening for all messages containing \"$word\"", false)
				color(Color.green)
			})
		}
	}

	command("Ignore") {
		description = "Ignore previously listened target."
		expect(SentenceArg)
		execute {
			val arg = it.args.component1() as String
			val display: String

			val result =
				if (arg.isLong()) {
					val user = watchService.jda.getUserById(arg.toLong())
					display = user.fullName()
					watchService.remove(user)
				}
				else {
					display = "\"$arg\""
					watchService.remove(arg)
				}

			it.respond(embed {
				if (result) {
					addField("Success!", "Successfully removed $display from the watchlist", false)
					color(Color.green)
				}
				else {
					addField("Failure!", "Failed to remove $display from the watchlist", false)
					color(Color.red)
				}}
			)
		}
	}
}