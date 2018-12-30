package io.github.jakejmattson.wiretap

import com.google.gson.GsonBuilder
import io.github.jakejmattson.wiretap.Project.config
import io.github.jakejmattson.wiretap.listeners.rolePrecondition
import io.github.jakejmattson.wiretap.services.*
import me.aberrantfox.kjdautils.api.startBot
import net.dv8tion.jda.core.JDA

object Project {
	lateinit var jda: JDA
	lateinit var config: Configuration
	val gson = GsonBuilder().setPrettyPrinting().create()
}

fun main(args: Array<String>) {
	Project.config = loadConfiguration() ?: return
	start()
}

private fun start() = startBot(config.token) {

	Project.jda = jda
	val watchService = WatchService()
	registerInjectionObject(watchService)

	configure {
		prefix = Project.config.prefix
		globalPath = "io.github.jakejmattson.wiretap"
	}

	registerCommandPreconditions(rolePrecondition())
}