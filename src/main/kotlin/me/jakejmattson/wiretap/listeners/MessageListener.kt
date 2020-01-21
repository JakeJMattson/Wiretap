package me.jakejmattson.wiretap.listeners

import com.google.common.eventbus.Subscribe
import me.jakejmattson.wiretap.services.WatchService
import me.aberrantfox.kjdautils.api.dsl.*
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color

class MessageListener(private val watchService: WatchService) {
    @Subscribe
    fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.author.isBot) return

        val user = watchService.getWatched(event.author)
        val hasWord = watchService.hasWatchedWord(event.message.contentRaw)
        val channel = event.message.channel as TextChannel
        val content = "${channel.asMention}\n ${event.message.contentRaw}"

        if (user != null)
            watchService.logUser(event.author, embed {
                addField("New message in ${channel.name} from watched user.", content, true)

                if (hasWord)
                    createDoubleAlert(this, "This message also contains a watched word.")
            })

        if (hasWord)
            watchService.logWord(embed {
                addField("New message in ${channel.name} containing watched word.", content, true)

                if (user != null)
                    createDoubleAlert(this, "The author of this message is also watched (<#${user.channelId}>).")
            })
    }
}

fun createDoubleAlert(embed: EmbedDSLHandle, message: String) = embed.apply {
    addField("Double alert warning!", message, false)
    color = Color.red
}