package io.github.jakejmattson.wiretap.listeners

import io.github.jakejmattson.wiretap.services.Configuration
import me.aberrantfox.kjdautils.api.dsl.CommandEvent
import me.aberrantfox.kjdautils.internal.command.*

fun rolePrecondition(configuration: Configuration) = { event: CommandEvent ->

	val guild = event.message.guild
	val requiredRoleName = configuration.requiredRoleName
	val requiredRole = guild.getRolesByName(requiredRoleName, true).firstOrNull()
	val memberRoles = guild.getMember(event.author).roles

	if (requiredRole != null)
		if (memberRoles.contains(requiredRole))
			Pass
		else
			Fail("You do not have the required role: $requiredRoleName")
	else
		Fail("Required role ($requiredRoleName) in config not found in guild!")
}