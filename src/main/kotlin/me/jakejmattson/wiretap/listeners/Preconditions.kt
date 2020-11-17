package me.jakejmattson.wiretap.listeners

import me.jakejmattson.wiretap.services.Configuration
import me.jakejmattson.discordkt.api.dsl.command.CommandEvent
import me.jakejmattson.discordkt.api.dsl.preconditions.*

class RolePrecondition(private val config: Configuration) : Precondition() {
    override fun evaluate(event: CommandEvent<*>): PreconditionResult {
        val guild = event.message.guild
        val requiredRole = guild.getRolesByName(config.requiredRoleName, true).firstOrNull()
        val memberRoles = guild.getMember(event.author)!!.roles

        requiredRole ?: return Fail("Required role (${config.requiredRoleName}) in config not found in guild!")

        if (requiredRole !in memberRoles)
            return Fail("You do not have the required role: ${requiredRole.name}")

        return Pass
    }
}