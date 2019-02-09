package io.github.jakejmattson.wiretap.extensions

import me.aberrantfox.kjdautils.api.annotation.Service
import net.dv8tion.jda.core.JDA

private lateinit var jda: JDA

@Service
class JdaInitializer(jdaInstance: JDA) { init { jda = jdaInstance } }

fun String.idToUser() = jda.getUserById(this)
fun String.idToChannel() = jda.getTextChannelById(this)
fun String.idToCategory() = jda.getCategoryById(this)