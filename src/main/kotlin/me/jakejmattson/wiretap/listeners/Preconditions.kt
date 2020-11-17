package me.jakejmattson.wiretap.listeners

import kotlinx.coroutines.flow.*
import me.jakejmattson.wiretap.services.Configuration
import me.jakejmattson.discordkt.api.dsl.precondition

fun rolePrecondition(config: Configuration) = precondition {
    val guild = guild ?: return@precondition fail()
    val requiredRole = guild.roles.firstOrNull { it.name.equals(config.requiredRoleName, true) }
        ?: return@precondition fail("Required role (${config.requiredRoleName}) in config not found in guild!")

    val memberRoles = author.asMember(guild.id).roles.toList()

    if (requiredRole !in memberRoles)
        fail("You do not have the required role: ${requiredRole.name}")
}