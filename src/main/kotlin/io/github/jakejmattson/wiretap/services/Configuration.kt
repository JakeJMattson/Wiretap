package io.github.jakejmattson.wiretap.services

import com.google.gson.GsonBuilder
import java.io.File

data class Configuration(val token: String = "insert-token-here", val prefix: String = "?")

private val gson = GsonBuilder().setPrettyPrinting().create()
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