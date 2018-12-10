package io.github.jakejmattson.wiretap

import io.github.jakejmattson.wiretap.listeners.rolePrecondition
import io.github.jakejmattson.wiretap.services.*
import me.aberrantfox.kjdautils.api.startBot

fun main(args: Array<String>) {
	val config = loadConfiguration() ?: return
	start(config)
}

private fun start(config: Configuration) = startBot(config.token) {

	val watchService = WatchService(jda, config)
	val category = jda.getCategoryById(config.watchCategory)

	registerInjectionObject(watchService, category)

	val root = "io.github.jakejmattson.wiretap."
	configure {
		prefix = config.prefix
		commandPath = "${root}commands"
		listenerPath = "${root}listeners"
	}

	registerCommandPreconditions(rolePrecondition(config))
}