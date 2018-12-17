package io.github.jakejmattson.wiretap.listeners

import com.google.common.eventbus.Subscribe
import io.github.jakejmattson.wiretap.services.WatchService
import me.aberrantfox.kjdautils.api.dsl.embed
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color

class MessageListener(private val watchService: WatchService) {
	@Subscribe
	fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
		if (event.author.isBot)
			return

		val user = watchService.getWatched(event.author)
		val hasWord = watchService.hasWatchedWord(event.message.contentRaw)
		val channel = event.message.channel as TextChannel

		if (user != null)
			watchService.logUser(event.author, embed {
				field {
					name = "New message in ${channel.name} from watched userId."
					value = "${channel.asMention}\n ${event.message.contentRaw}"
					inline = false
				}

				if (hasWord) {
					addField("Double alert warning!", "This message also contains a watched word.", false)
					color(Color.RED)
				}
			})

		if (hasWord)
			watchService.logWord(embed {
				field {
					name = "New message in ${channel.name} containing watched word."
					value = "${channel.asMention}\n ${event.message.contentRaw}"
					inline = false
				}

				if (user != null) {
					field {
						name = "Double alert warning!"
						value = "The author of this message is also watched (<#${user.channelId}>)."
						inline = false
					}
					color(Color.RED)
				}
			})
	}
}