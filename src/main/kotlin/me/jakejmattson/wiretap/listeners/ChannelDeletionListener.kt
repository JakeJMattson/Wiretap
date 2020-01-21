package me.jakejmattson.wiretap.listeners

import com.google.common.eventbus.Subscribe
import me.jakejmattson.wiretap.services.WatchService
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent

class ChannelDeletionListener(private val watchService: WatchService) {
    @Subscribe
    fun onTextChannelDelete(event: TextChannelDeleteEvent) = watchService.remove(event.channel)
}