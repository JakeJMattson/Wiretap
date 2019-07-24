package io.github.jakejmattson.wiretap.extensions

import me.aberrantfox.kjdautils.api.dsl.*

fun CommandEvent.respondEmbed(init: EmbedDSLHandle.() -> Unit) {
    val embed = EmbedDSLHandle()
    embed.init()
    respond(embed.build())
}