package me.jakejmattson.wiretap.services

import me.jakejmattson.discordkt.api.dsl.Data

data class Configuration(val watchCategory: String = "insert-id-here",
                         val wordLogChannel: String = "insert-id-here",
                         val requiredRoleName: String = "Staff",
                         val recoverWatched: Boolean = true) : Data("config/config.json")