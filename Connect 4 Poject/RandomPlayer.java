import java.util.*;
public class RandomPlayer implements Player
{
  public int move(int[][] B)
  {
    Random randomCol = new Random();
    boolean placed = false;
    int col = 0;
    while(placed == false)
    {
      col = randomCol.nextInt(B[0].length);
      if(B[0][col] == 0)
      {
        placed = true;
      }
    }
    return col;
  }
}