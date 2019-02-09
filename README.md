<p align="center">
  <a href="https://kotlinlang.org/">
    <img src="https://img.shields.io/badge/Kotlin-1.3.21-blue.svg" alt="Kotlin 1.3.21">
  </a>
  <a href="https://gitlab.com/Aberrantfox/KUtils">
    <img src="https://img.shields.io/badge/KUtils-0.9.14-blue.svg" alt="KUtils 0.9.14">
  </a>
  <a href="https://discordapp.com/users/254786431656919051/">
    <img src="https://img.shields.io/badge/Discord-JakeyWakey%231569-lightgrey.svg" alt="Discord JakeyWakey#1569">
  </a>
  <a href="https://GitHub.com/JakeJMattson/Wiretap/releases/">
    <img src="https://img.shields.io/github/release/JakeJMattson/Wiretap.svg" alt="release">
  </a>
  <a href="LICENSE.md">
    <img src="https://img.shields.io/github/license/JakeJMattson/Wiretap.svg" alt="license">
  </a>
</p>

# Wiretap
<p align="justify">
Wiretap provides a way for the staff of a Discord server to monitor a target user's server activity from a single channel.
It was made for large servers, but can be used at any scale.
Consider the following: A user joins your server and says something concerning or perhaps breaks server rules.
How do you make sure they stay in line? Do you set a reminder to check their history every few minutes? 
Do you hope that staff is watching every channel? Wiretap solves this.
</p>

### How does it work?
<p align="justify">
First, you track a target user - a private text channel is created on the server automatically.
Now, all you have to do is wait.
If the user sends a message in any channel throughout the server, it will be logged in their private channel.
Now your staff can quickly and easily review a user through a single channel without the extra work or hassle.
</p>

### Commands: 

#### Listen

| Command   | Arguments | Effect                                    |
| ------    | ------    | ------                                    |
| ListenTo  | User      | Listen to a target user.                  |
| ListenFor | Sentence  | Listen for a target word.                 |
| IgnoreUser| User      | Ignore previously listened user.          |
| IgnoreWord| Sentence  | Ignore previously listened word.          |
| ListUsers | (none)    | List currently watched users.             |
| ListWords | (none)    | List currently watched words.             |

#### Utility

| Command   | Arguments | Effect                                    |
| ------    | ------    | ------                                    |
| Author    | (none)    | Display the author of the bot.            |
| Source    | (none)    | Display the source code via a GitLab link.|
| Version   | (none)    | Display the current running version.      |
| BotInfo   | (none)    | Display a summary or bot information.     |
| Ping      | (none)    | Display the network ping of the bot.      |
| Uptime    | (none)    | Display how long the bot has been running.|

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
