package io.github.jakejmattson.wiretap

import me.aberrantfox.kjdautils.api.startBot

fun main(args: Array<String>) {
	val token = args.first()

	startBot(token) {
		configure {
			prefix = "?"
			globalPath = "io.github.jakejmattson.wiretap"

			//Move the help command from the internal "utility" category, to the local "Utility" category
			container.commands.getValue("help").category = "Utility"
		}
	}
}