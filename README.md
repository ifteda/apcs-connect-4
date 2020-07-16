# Connect 4
Assignment for AP Computer Science class (2015)

This package contains files necessary to run a game of Connect 4.
	Files in this code distribution:
		- AIPlayer
		- Connect4
		- Player
		- JBox
		- JCanvas
		- JEventQueue

## Installation
	Open and compile the .java files in a Java Integrated Development Environment (e.g., Dr. Java, Eclipse, NetBeans, etc.). To play the game, run the Connect4 class.

## AIPlayer
	AIPlayer is an artificial intelligence player for Connect4 that implements the Player interface. It uses the MiniMax algorithm to choose its moves. Methods in this class were inspired by methods in the class TicTacToe by Wayne Snyder (waynsnyder@gmail.com).

## minMax()
	The method minMax() uses recursive backtracking and the MiniMax algorithm to make a move decision. The goal of this algorithm is to minimize loss in a potential scenario.
	Pseudocode and further explanation of the MiniMax algorithm can be found at http://en.wikipedia.org/wiki/Minimax#Pseudocode.

## evalHelper()
	The method evalHelper() is designed to minimize code repetition in the method eval(), as some steps were repeated. This provides for more comprehensible code. However, it may slightly negatively impact time efficiency.

## Other bugs
	Though the method move() in AIPlayer prioritizes winning moves and blocking losing moves, it can only handle this effectively if there are three consecutive pieces of the same color. This method is also desigend to first check each row for three consecutive pieces of the same color and place a piece to either complete the set and win or block the set from one of the sides. However, this method appears to not always work.


## Contact & Acknowledgements
	AIPlayer class: Ifteda Ahmed-Syed (ifteda@gmail.com)
	Connect 4 class:
		- Ashley Hansberry (Ashley_Hansberry@loomis.org)
		- Wayn Snyder (waynsnyder@gmail.com)
	Player interface: Ashley Hansberry (Ashley_Hansberry@loomis.org)
	JBox, JCanvas, & JEventQueue: Mads Rosendahl (madsr@ruc.dk)
Rough pseudocode used for eval() found at http://stackoverflow.com/questions/10985000/how-should-i-design-a-good-evaluation-function-for-connect-4?rq=1.

