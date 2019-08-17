package io.github.jakejmattson.wiretap.extensions

import me.aberrantfox.kjdautils.api.dsl.*
import java.awt.Color

fun CommandEvent.respondEmbed(init: EmbedDSLHandle.() -> Unit) {
    val embed = EmbedDSLHandle()
    embed.color = Color(0x00bfff)
    embed.init()
    respond(embed.build())
}