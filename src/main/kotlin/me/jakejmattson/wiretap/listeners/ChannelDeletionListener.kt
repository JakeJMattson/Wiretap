package me.jakejmattson.wiretap.listeners

import com.gitlab.kordlib.core.event.channel.TextChannelDeleteEvent
import me.jakejmattson.discordkt.api.dsl.listeners
import me.jakejmattson.wiretap.services.WatchService

fun channelDeletion(watchService: WatchService) = listeners {
    on<TextChannelDeleteEvent> {
        watchService.remove(channel)
    }
}