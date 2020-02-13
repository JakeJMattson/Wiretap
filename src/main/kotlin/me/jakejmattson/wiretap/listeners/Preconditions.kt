package me.jakejmattson.wiretap.listeners

import me.aberrantfox.kjdautils.api.annotation.Precondition
import me.jakejmattson.wiretap.services.Configuration
import me.aberrantfox.kjdautils.api.dsl.*
import me.aberrantfox.kjdautils.internal.command.*

@Precondition
fun rolePrecondition(config: Configuration) = precondition {
    val guild = it.message.guild
    val requiredRole = guild.getRolesByName(config.requiredRoleName, true).firstOrNull()
    val memberRoles = guild.getMember(it.author)!!.roles

    requiredRole ?: return@precondition Fail("Required role (${config.requiredRoleName}) in config not found in guild!")

    if (requiredRole !in memberRoles)
        return@precondition Fail("You do not have the required role: ${requiredRole.name}")

    return@precondition Pass
}