# Setup guide

## Installation guide

### Server Setup
If you don't already have one, create a Discord server for the bot to run on. 
Follow the [official guide](https://support.discordapp.com/hc/en-us/articles/204849977-How-do-I-create-a-server-) if needed.

### Bot Account
Create a bot account in the [developers](https://discordapp.com/developers/applications/me) section of the Discord website.
- Create an application
- Under "General Information" 
	- Enter an app icon and a name.
	- You will need the client ID for later in this guide; copy it somewhere.
- Under "Bot"
	- Create a bot.
	- Give it a username, app icon, and record the token for future use.
		- Note: This is a secret token, don't reveal it!
	- Uncheck the option to let it be a public bot so that only you can add it to servers.
- Save changes

### Add Bot
- Visit the [permissions](https://discordapi.com/permissions.html) page. Required permissions have been configured for you.
- Under "OAth URL Generator" enter the bot's client ID that you got earlier.
- Click the link to add it to your server.
- It is recommended to place it at the top of your server so members can see it.

## Configuration
Below, you can find an explanation of each configuration field.

```json
{
  "token": "Your private bot token.",
  "prefix": "The prefix required to invoke the bot. Default: ?",
  "watchCategory": "The category ID in which new channels will be created when a user is watched.",
  "wordLogChannel": "The ID of the channel where detections of watched words will be logged.",
  "requiredRoleName": "The role required to access the bot (typically a staff role)."
  "recoverWatched": "Whether or not a backup should be kept in case the bot goes offline."
}
```
