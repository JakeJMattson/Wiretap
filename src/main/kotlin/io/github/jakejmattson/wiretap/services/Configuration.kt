package io.github.jakejmattson.wiretap.services

import io.github.jakejmattson.wiretap.Project.gson
import java.io.File

data class Configuration(val token: String = "insert-token-here",
						 val prefix: String = "?",
						 val watchCategory: String = "insert-id-here",
						 val wordLogChannel: String = "insert-id-here",
						 val requiredRoleName: String = "Staff",
						 val recoverWatched: Boolean = true)

private val configDir = File("config/")
private val configFile = File("${configDir.name}/config.json")

fun loadConfiguration() =
	if (!configFile.exists()) {
		configDir.mkdirs()
		configFile.writeText(gson.toJson(Configuration()))
		println("Please fill in the configuration file:\n${configFile.absolutePath}")
		null
	}
	else
		gson.fromJson(configFile.readText(), Configuration::class.java)