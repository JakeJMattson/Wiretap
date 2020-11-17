package me.jakejmattson.wiretap.listeners

import com.gitlab.kordlib.core.entity.channel.TextChannel
import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.jakejmattson.discordkt.api.dsl.listeners
import me.jakejmattson.discordkt.api.extensions.*
import me.jakejmattson.wiretap.services.WatchService
import java.awt.Color

fun messageListener(watchService: WatchService) = listeners {
    on<MessageCreateEvent> {
        if (getGuild() == null)
            return@on

        val author = message.author!!

        if (author.isBot == true) return@on

        val user = watchService.getWatched(author)
        val hasWord = watchService.hasWatchedWord(message.content)
        val channel = message.channel as TextChannel
        val content = "${channel.mention}\n ${message.content}"

        if (user != null)
            watchService.logUser(author) {
                addInlineField("New message in ${channel.name} from watched user.", content)

                if (hasWord)
                    createDoubleAlert("This message also contains a watched word.")
            }

        if (hasWord)
            watchService.logWord {
                addInlineField("New message in ${channel.name} containing watched word.", content)

                if (user != null)
                    createDoubleAlert("The author of this message is also watched (<#${user.channelId}>).")
            }
    }
}

fun EmbedBuilder.createDoubleAlert(message: String) = this.apply {
    addField("Double alert warning!", message)
    color = Color.red
}