package io.github.jakejmattson.wiretap.listeners

import com.google.common.eventbus.Subscribe
import io.github.jakejmattson.wiretap.services.WatchService
import me.aberrantfox.kjdautils.api.dsl.embed
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent

class MessageListener(private val watchService: WatchService) {
	@Subscribe
	fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
		if (event.author.isBot)
			return

		val user = watchService.isUserWatched(event.author)
		val hasWord = watchService.hasWatchedWord(event.message.contentRaw)
		val channel = event.message.channel as TextChannel

		if (user != null)
			user.channel.sendMessage(embed {
				field {
					name = "New message in ${channel.name} from watched user."
					value = "${channel.asMention}\n ${event.message.contentRaw}"
					inline = false
				}
			}).queue()

		if (hasWord)
			watchService.wordLog.sendMessage(embed {
				field {
					name = "New message in ${channel.name} containing watched word."
					value = "${channel.asMention}\n ${event.message.contentRaw}"
					inline = false
				}
			}).queue()
	}
}