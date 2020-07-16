/** Player.java: 
  * An interface that represents the methods a Player
  * must implement in order to work with Connect4.java
  * 
  * @author Ashley Hansberry (Ashley_Hansberry@loomis.org)
  * @version AP CS Spring 2015, 5/12/15
  */
public interface Player {
    
    /** move(int[][] B) should return the next move
      * that the computer will make
      * @param B the 2 dimensional array that represents the board
      * @return the number corresponding to the space to move in
      */
    public int move(int[][] B); 
    
}

