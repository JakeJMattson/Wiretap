package io.github.jakejmattson.wiretap.listeners

import com.google.common.eventbus.Subscribe
import io.github.jakejmattson.wiretap.services.WatchService
import me.aberrantfox.kjdautils.api.dsl.*
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

				if (hasWord)
					createDoubleAlert(this, "This message also contains a watched word.")
			})

		if (hasWord)
			watchService.logWord(embed {
				field {
					name = "New message in ${channel.name} containing watched word."
					value = "${channel.asMention}\n ${event.message.contentRaw}"
					inline = false
				}

				if (user != null)
					createDoubleAlert(this, "The author of this message is also watched (<#${user.channelId}>).")
			})
	}
}

fun createDoubleAlert(embed: EmbedDSLHandle, message: String) {
	embed.addField("Double alert warning!", message, false)
	embed.color(Color.red)
}