# Gaming-Bots
Project developed in a general purpose to create bots for games.
This project is just at the beginning with just a few features.

The base to support this bot is image processing, the bot works basically this way:

1) Take screenshot of a specific rectangle or specific shape area.
2) Process this specific area using some specific algorithm to recognize something (like shapes).
3) Do some action over some game using the processed things in step 2, like press some key, or type something or press mouse, etc.

The main example is the pre-build TibiaBot tha we do all those steps and use step 2 to parse digits and get current life/mana in Tibia.
