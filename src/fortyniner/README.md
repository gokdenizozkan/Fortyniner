# Fortyniner 
### A text-based video game inspired by Minesweeper.

## Features
- GAMEMODES! You can create your own board or select pre-made ones!
- REVEAL CHAIN! If you reveal a tile, and it has no deathspots around it, all neighbouring "plain tiles" will reveal themselves.
- TRUELY RANDOM! No matter what you choose as a side, your board will have truely random deathspot placements!
- DIFFICULTIES! You can adjust the difficulty to your liking! Less deathspots or ultimate death?

## How it works
### GameManager.java
Game is launched and set-up here. You may simply call the public static method named "launch" and voila!
### GameLogic.java
Controls the game state. Gameloop, win and lose conditions, player inputs... all are handled here.
### Board.java
All board-related stuff is done here within Board.java.
### Tile.java
Tiles are elements of the board, which is a 2D array of integer values. It holds the data whether "that tile" is a deathspot or not, its coordinates, etc, every detail and every method controlling itself.
### Radar.java
As the name suggests, it acts like a radar for the board. If you want to get neighbouring tiles or count neighbouring deathspots, you should refer to Radar class and its static methods.

## Further information can be found within the codes, as comments.
