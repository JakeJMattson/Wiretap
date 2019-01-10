package io.github.jakejmattson.wiretap.extensions

import net.dv8tion.jda.core.JDA

lateinit var conversionJDA: JDA

fun String.idToUser() = conversionJDA.getUserById(this)
fun String.idToChannel() = conversionJDA.getTextChannelById(this)
fun String.idToCategory() = conversionJDA.getCategoryById(this)