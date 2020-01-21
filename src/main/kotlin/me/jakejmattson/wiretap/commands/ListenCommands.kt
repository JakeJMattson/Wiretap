package me.jakejmattson.wiretap.commands

import me.jakejmattson.wiretap.services.WatchService
import me.aberrantfox.kjdautils.api.dsl.command.*
import me.aberrantfox.kjdautils.api.dsl.embed
import me.aberrantfox.kjdautils.extensions.jda.fullName
import me.aberrantfox.kjdautils.internal.arguments.*
import java.awt.Color

@CommandSet("Listen")
fun listenCommands(watchService: WatchService) = commands {

    command("ListenTo") {
        description = "Listen to a target user."
        execute(UserArg) {
            val user = it.args.first

            if (user.isBot) return@execute it.respond("Invalid ID (bot)")

            val response = if (watchService.watchUser(user))
                createSuccessEmbed("Now listening to all messages from ${user.fullName()} (${user.asMention})")
            else
                createFailureEmbed("Messages from ${user.fullName()} (${user.asMention}) are already being listened for.")

            it.respond(response)
        }
    }

    command("ListenFor") {
        description = "Listen for a target word."
        execute(SentenceArg) {
            val word = it.args.first

            val response = if (watchService.watchWord(word))
                createSuccessEmbed("Now listening for all messages containing \"$word\"")
            else
                createFailureEmbed("Messages containing \"$word\" are already being listened for.")

            it.respond(response)
        }
    }

    command("IgnoreUser") {
        description = "Ignore previously listened user."
        execute(UserArg) {
            val user = it.args.first
            val name = user.fullName()

            val response = if (watchService.remove(user))
                createSuccessEmbed("Successfully removed $name from the watchlist")
            else
                createFailureEmbed("Failed to remove $name from the watchlist")

            it.respond(response)
        }
    }

    command("IgnoreWord") {
        description = "Ignore previously listened word."
        execute(SentenceArg) {
            val word = it.args.first
            val display = "\"$word\""

            val response = if (watchService.remove(word))
                createSuccessEmbed("Successfully removed $display from the watchlist")
            else
                createFailureEmbed("Failed to remove $display from the watchlist")

            it.respond(response)
        }
    }

    command("ListUsers") {
        description = "List currently watched users."
        execute {
            val users = watchService.getUsersAsString()

            it.respond(
                if (users.isNotEmpty()) "**Watching the following users:**\n$users" else "Not currently watching any users.")
        }
    }

    command("ListWords") {
        description = "List currently watched words."
        execute {
            val words = watchService.getWordsAsString()

            it.respond(
                if (words.isNotEmpty()) "**Watching the following words:**\n$words" else "Not currently watching any words.")
        }
    }
}

fun createSuccessEmbed(message: String) =
    embed {
        addField("Success!", message, false)
        color = Color.green
    }

fun createFailureEmbed(message: String) =
    embed {
        addField("Failure!", message, false)
        color = Color.red
    }