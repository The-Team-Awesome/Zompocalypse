# Zompocalypse

Zompocalypse is a single or multi-player Zombie survival game which puts the Players in defense of Maze-town. Zombies navigate the maze for which the town is famed for in an effort to eat the juicy and nutritious brains of its inhabitants and it is up to you to stop them!

While you defend the town and traverse the maze you will find treasures and items which will help you in the defense of the town. Unfortunately, the residents are cheap, so you will still need to purchase supplies from them despite the desperation of their situation. See how long you can stop the flow of Zombies!

## Running the game

To play, download the .jar file, navigate to the directory where you put the file and type into the command line:
\n\tjava -jar zompocalypse.jar <commands>

Commands:
\n\t-help/-h			Prints out a list of commands and their function
\n\t-server/-s <n>		Start a server, expecting n clients to connect
\n\t-client/-c <url>	Create a client connection to the server at url

For example, to start up a locally running client/server connection, in one window you would type:
\n\tjava -jar zompocalypse.jar -server 1

You would then type in another window:
\n\tjava -jar zompocalypse.jar -client 127.0.0.1

## Credits

* Kieran Mckay
* Danielle Emygdio
* David Thomsen
* Pauline Kelly
* Sam Costigan
