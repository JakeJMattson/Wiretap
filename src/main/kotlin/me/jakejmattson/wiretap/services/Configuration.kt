package me.jakejmattson.wiretap.services

import me.aberrantfox.kjdautils.api.annotation.Data

@Data("config/config.json")
data class Configuration(val watchCategory: String = "insert-id-here",
                         val wordLogChannel: String = "insert-id-here",
                         val requiredRoleName: String = "Staff",
                         val recoverWatched: Boolean = true)