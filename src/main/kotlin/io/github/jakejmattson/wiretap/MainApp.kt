package io.github.jakejmattson.wiretap

import io.github.jakejmattson.wiretap.services.*
import me.aberrantfox.kjdautils.api.startBot

fun main(args: Array<String>) {
	val config = loadConfiguration() ?: return
	start(config)
}

private fun start(config: Configuration) = startBot(config.token) {

	val watchlist = ArrayList<Watched>()
	val category = jda.getCategoryById(config.watchCategory)

	registerInjectionObject(watchlist, category)

	val base = "io.github.jakejmattson.wiretap."
	configure {
		prefix = config.prefix
		commandPath = base + "commands"
		listenerPath = base + "listeners"
	}
}