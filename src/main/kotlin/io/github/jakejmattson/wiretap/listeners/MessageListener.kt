package io.github.jakejmattson.wiretap.listeners

import com.google.common.eventbus.Subscribe
import io.github.jakejmattson.wiretap.services.Watched
import me.aberrantfox.kjdautils.api.dsl.embed
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent

class MessageListener(private val watchlist: ArrayList<Watched>) {
	@Subscribe
	fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
		if (event.author.isBot)
			return

		val watched = watchlist.firstOrNull { event.author == it.user } ?: return
		val channel = event.message.channel as TextChannel

		watched.channel.sendMessage(embed {
			field {
				name = "New message ${channel.name}"
				value = "${channel.asMention}\n ${event.message.contentRaw}"
				inline = false
			}
		}).queue()
	}
}