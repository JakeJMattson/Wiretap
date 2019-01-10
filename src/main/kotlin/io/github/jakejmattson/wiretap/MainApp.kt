package io.github.jakejmattson.wiretap

import io.github.jakejmattson.wiretap.extensions.conversionJDA
import me.aberrantfox.kjdautils.api.startBot

fun main(args: Array<String>) {
	val token = args.first()
	start(token)
}

private fun start(token: String) = startBot(token) {
	conversionJDA = jda

	configure {
		prefix = "?"
		globalPath = "io.github.jakejmattson.wiretap"
	}
}