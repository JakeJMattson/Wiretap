package me.jakejmattson.wiretap.commands

import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.commands
import me.jakejmattson.discordkt.api.extensions.addField
import me.jakejmattson.wiretap.services.WatchService
import java.awt.Color

fun listenCommands(watchService: WatchService) = commands("Listen") {
    command("ListenTo") {
        description = "Listen to a target user."
        execute(UserArg) {
            val user = args.first

            if (user.isBot == true) {
                respond("Invalid ID (bot)")
                return@execute
            }

            respond {
                if (watchService.watchUser(user))
                    createSuccessEmbed("Now listening to all messages from ${user.tag} (${user.mention})")
                else
                    createFailureEmbed("Messages from ${user.tag} (${user.mention}) are already being listened for.")
            }
        }
    }

    command("ListenFor") {
        description = "Listen for a target word."
        execute(EveryArg) {
            val word = args.first

            respond {
                if (watchService.watchWord(word))
                    createSuccessEmbed("Now listening for all messages containing \"$word\"")
                else
                    createFailureEmbed("Messages containing \"$word\" are already being listened for.")
            }
        }
    }

    command("IgnoreUser") {
        description = "Ignore previously listened user."
        execute(UserArg) {
            val user = args.first
            val name = user.tag

            respond {
                if (watchService.remove(user))
                    createSuccessEmbed("Successfully removed $name from the watchlist")
                else
                    createFailureEmbed("Failed to remove $name from the watchlist")
            }
        }
    }

    command("IgnoreWord") {
        description = "Ignore previously listened word."
        execute(EveryArg) {
            val word = args.first
            val display = "\"$word\""

            respond {
                if (watchService.remove(word))
                    createSuccessEmbed("Successfully removed $display from the watchlist")
                else
                    createFailureEmbed("Failed to remove $display from the watchlist")
            }
        }
    }

    command("ListUsers") {
        description = "List currently watched users."
        execute {
            val users = watchService.getUsersAsString()

            respond(
                if (users.isNotEmpty()) "**Watching the following users:**\n$users" else "Not currently watching any users.")
        }
    }

    command("ListWords") {
        description = "List currently watched words."
        execute {
            val words = watchService.getWordsAsString()

            respond(
                if (words.isNotEmpty()) "**Watching the following words:**\n$words" else "Not currently watching any words.")
        }
    }
}

fun EmbedBuilder.createSuccessEmbed(message: String) = apply {
    addField("Success!", message)
    color = Color.green
}

fun EmbedBuilder.createFailureEmbed(message: String) = apply {
    addField("Failure!", message)
    color = Color.red
}