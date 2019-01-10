package io.github.jakejmattson.wiretap.services

import me.aberrantfox.kjdautils.api.annotation.Data

@Data("config/config.json")
data class Configuration(val token: String = "insert-token-here",
						 val prefix: String = "?",
						 val watchCategory: String = "insert-id-here",
						 val wordLogChannel: String = "insert-id-here",
						 val requiredRoleName: String = "Staff",
						 val recoverWatched: Boolean = true)