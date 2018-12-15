package io.github.jakejmattson.wiretap

import io.github.jakejmattson.wiretap.listeners.rolePrecondition
import io.github.jakejmattson.wiretap.services.*
import me.aberrantfox.kjdautils.api.startBot

const val root = "io.github.jakejmattson.wiretap."

fun main(args: Array<String>) {
	val config = loadConfiguration() ?: return
	start(config)
}

private fun start(config: Configuration) = startBot(config.token) {

	val watchService = WatchService(jda, config)
	registerInjectionObject(watchService)

	configure {
		prefix = config.prefix
		commandPath = "${root}commands"
		listenerPath = "${root}listeners"
	}

	registerCommandPreconditions(rolePrecondition(config))
}