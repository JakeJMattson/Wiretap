package io.github.jakejmattson.wiretap

import io.github.jakejmattson.wiretap.services.*
import me.aberrantfox.kjdautils.api.startBot

fun main(args: Array<String>) {
	val config = loadConfiguration() ?: return
	start(config)
}

private fun start(config: Configuration) = startBot(config.token) {

	configure {
		prefix = config.prefix
	}
}