package io.github.jakejmattson.wiretap.commands

import io.github.jakejmattson.wiretap.services.Watched
import me.aberrantfox.kjdautils.api.dsl.*
import me.aberrantfox.kjdautils.extensions.jda.fullName
import me.aberrantfox.kjdautils.internal.command.arguments.UserArg
import net.dv8tion.jda.core.entities.*
import java.awt.Color

@CommandSet("listen")
fun listenCommands(watchlist: ArrayList<Watched>, category: Category) = commands {

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

				watchlist.add(Watched(user, channel as TextChannel))

				it.respond(embed {
					field {
						name = "Listening to user!"
						value = "Now listening to all messages from ${user.fullName()} (${user.asMention})"
						inline = false
					}
					setColor(Color.green)
				})
			}
		}
	}
}