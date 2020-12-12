# Chess

An attempt to recreate chess

This project is just to practice some logic, before trying to recreate this simple game on Unity.

This is an attempt to take some consideration of good coding practices.

## Goals 
1. The game can be played through terminal
2. The game will know when the game is concluded
3. The game will remember all moves done. It will rewrite all inserted moves into proper notation (hopefully)
4. The game will help ensure moves are legal



## Compromises 
1. Rather than typical chess notation, the player will input the original location and the final destination of the piece.
2. The black side is NO LONGER indicated with lower font rather than a colour difference. Enums.Colours are now used to differentiate between each side.
3. The system is designed to trust the user when a check or checkmate has occurred

## Future improvements
1. To be able to differentiate between pieces when it is not immediately obvious which piece is being moved
2. To be able to detect checks and checkmates
3. To allow resigning and draw offers
