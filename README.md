# java-othello

This program is an intelligent Othello player written in java. It takes in argument a position representing the othello board, and an integer representing the maximum time it has, and computes the best next move for the current player.

# Execute the program

Compile the project using
```
javac -d bin/ -cp /bin src/othello/*.java
```
from the root directory.

Then you can test the program going to the bin directory and launching :
```
java othello.Othello position time
```

where :
 - position is a 65-character string representing the board. The first character is W or B, and indicates which player is to move (white or black). The remaining characters should be E (for empty), O (for white markers), or X (for black markers). These 64 characters describe the board, with the first character referring to the upper left hand corner, the second character referring to the second square from the left on the uppermost row, and so on. For example, the 10th character describes the second square from the left on the second row from the top.
 - time is the maximum allowed duration to compute the next move (in seconds)

The program prints the position where the next player should place his coin.

----------------------------------------------------------------------------

Additionnal tests can be performed by simulating an entire game against a naive AI. Further information on that can be found later in this readme.

To run a game, go to the test_code directory and run
```
./othello_start ./othellonaive ../othello time
```
Where time is the max duration to compute a move
It will simulate an entire game, our program being the Black player. You can notice that the longer is 'time', the better the program is.



------------------------------------------------------------------------------

# How it works

Our program uses a minimax algorithm with alpha-beta pruning, with a heuristic taking in consideration the number of corners a player has, the number of possible moves after a move, the number of stable coins (coins that cannot change color), and the total number of coins.
The naive program, however, uses a heuristic only based on the number of coins.


