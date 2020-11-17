package me.jakejmattson.wiretap.commands

import me.jakejmattson.discordkt.api.dsl.commands
import me.jakejmattson.discordkt.api.extensions.*
import java.util.*
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime

private val startTime = Date()

@ExperimentalTime
fun utilityCommands() = commands("Utility") {
    guildCommand("Status", "Ping") {
        description = "Display network status and total uptime."
        execute {
            respond {
                val seconds = (Date().time - startTime.time) / 1000

                addField("Gateway Ping", discord.api.gateway.averagePing.inMilliseconds.roundToInt().toString())
                addField("Total Uptime", seconds.toTimeString())
            }
        }
    }
}