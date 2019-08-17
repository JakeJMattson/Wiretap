package io.github.jakejmattson.wiretap.listeners

import io.github.jakejmattson.wiretap.services.Configuration
import me.aberrantfox.kjdautils.api.dsl.CommandEvent
import me.aberrantfox.kjdautils.api.dsl.Precondition
import me.aberrantfox.kjdautils.internal.command.*

@Precondition
fun rolePrecondition(config: Configuration)  = exit@ { event: CommandEvent ->

	val guild = event.message.guild
	val requiredRole = guild.getRolesByName(config.requiredRoleName, true).firstOrNull()
	val memberRoles = guild.getMember(event.author)!!.roles

	requiredRole ?: return@exit Fail("Required role (${config.requiredRoleName}) in config not found in guild!")

	if (requiredRole !in memberRoles)
		return@exit Fail("You do not have the required role: ${requiredRole.name}")

	return@exit Pass
}