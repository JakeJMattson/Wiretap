package io.github.jakejmattson.wiretap

import io.github.jakejmattson.wiretap.services.conversionJDA
import me.aberrantfox.kjdautils.api.startBot
import net.dv8tion.jda.core.JDA

object Project {
	lateinit var jda: JDA
}

fun main(args: Array<String>) {
	val token = args.first()
	start(token)
}

private fun start(token: String) = startBot(token) {
	Project.jda = jda

	configure {
		prefix = "?"
		globalPath = "io.github.jakejmattson.wiretap"
	}
}