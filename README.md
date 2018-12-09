<p align="center">
  <a href="LICENSE.md">
    <img src="https://img.shields.io/github/license/JakeJMattson/Wiretap.svg" alt="license">
  </a>
</p>

# Wiretap
<p align="justify">
Wiretap provides a way for the staff of a Discord server to monitor a target user's server activity from a singe channel.
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

| Command   | Arguments | Effect                                        |
| ------    | ------    | ------                                        |
| ListenTo  | User      | Begin listening to the target user.           |
| ListenFor | Sentence  | Begin listening for the target word or phrase.|
| Ignore    | Sentence  | Stop listening to argument passed.            |

#### Utility

| Command   | Arguments | Effect                                    |
| ------    | ------    | ------                                    |
| Author    | (none)    | Display the author of the bot.            |
| Source    | (none)    | Display the source code via a GitLab link.|
| Version   | (none)    | Display the current running version.      |
| BotInfo   | (none)    | Display a summary or bot information.     |

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
