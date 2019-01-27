/** AIPlayer.java:
  * Artificial intelligence (decision-maker for machine/computer player).
  * Implements Player interface.
  * 
  * @auther Ifteda Ahmed-Syed (iftedanas@gmail.com)
  * Date: 05-21-2015
  */

public class AIPlayer implements Player
{
  private final static int Inf = Integer.MAX_VALUE; 
  
  /* Checks each available space in board B
   * Decides a move using method minMax()
   * Prioritizes winning move or blocking losing move.
   * @param int[][] B: playing board
   * @return int: column index
   */
  public int move(int[][] B)
  {
    //choosing winning or blocking losing moves
    //by row (from top)
    for(int row = 0; row < B.length; row++)
    {
      for(int col = 0; col < B[0].length - 3; col++)
      {
        int set = B[row][col] + B[row][col+1] + B[row][col+2];
        if(set == 30 || set == 3)
        {
          if( col != 0 && B[row][col-1] == 0)
          {
            return col - 1;
          }
          if(B[row][col+3] ==0)
          {
            return col + 3;
          }
        }
      }
    }
    //by column (from left)
    for(int col = 0; col < B.length; col++)
    {
      for(int row = 0; row < B.length - 3; row++)
      {
        int set = B[row][col] + B[row+1][col] + B[row+2][col];
        if(set == 30 || set == 3)
        {
          if(row != 0 && B[row-1][col] == 0)
          {
            return col - 1;
          }
          if(B[row+3][col] ==0)
          {
            return col + 3;
          }
        }
      }
    }
    //by daigonal (from top left)
    for(int row = 0; row < B.length - 3; row++)
    {
      for(int col = 0; col < B[0].length - 3; col++)
      {
        int set = B[row][col] + B[row+1][col+1] + B[row+2][col+2];
        if(set == 30 || set == 3)
        {
          if(row != 0 && col != 0 && B[row-1][col-1] == 0)
          {
            return col - 1;
          }
          if( B[row+3][col+3] ==0)
          {
            return col + 3;
          }
        }
      }
    }
    //by diagonal (from bottom left)
    for(int row = B.length - 1; row > 3; row--)
    {
      for(int col = 0; col < B[0].length - 3; col++)
      {
        int set = B[row][col] + B[row-1][col+1] + B[row-2][col+2];
        if(set == 30 || set == 3)
        {
          if(row != B.length - 1 && col != 0 && B[row+1][col-1] == 0)
          {
            return col - 1;
          }
          if(B[row-3][col+3] ==0)
          {
            return col + 3;
          }
        }
      }
    }
    
    int max = -Inf;     
    int bestMove = -1;
    int length = B.length;
    int width = B[0].length;
    for(int move = 0; move < (length * width); ++move)
    { 
      if(B[move/length][move%width] == 0) //move/length gets row, move%width gets length
      {   // if move is available         //such that move would equal the index if B were a single long 1-dimensional array
                
        B[move/length][move%width] = 8;       // make the move
                
        int val = minMax( B, 1 ); 
                
        if(val > max)           // if evaluation of B through minMax(B, 1) is better than current board
        {                       // if better move, save it
          bestMove = move; 
          max = val; 
        }
                
        B[move/length][move%width] = 0;        // undo the move
                
      }  
    }  
    return bestMove % width; //return column 
  } 
  
  /* Recursive method.
   * If depth is even, evaluates to find max.
   * If depth is odd, evaluates to find min.
   * Evaluates only up to a limited depth.
   * @param int[][] B: playing board
   * @param depth: number of moves from current board
   * @return int: max or min
   */
  public static int minMax(int[][] B, int depth)
  {
    int length = B.length;
    int width = B[0].length;
    if(eval(B) == Inf || eval(B) == -Inf || depth == 5)  //if winning or losing board, or depth limit reached
    {
      return eval(B); 
    }
    else if( depth % 2 == 0 )
    {       // even level of depth is max mode, AI player           
      int max = -Inf; 
      for(int move = 0; move < length * width; ++move)
      {
        if(B[move/length][move%width] == 0)
        {   // if move is available
          B[move/length][move%width] = 10;       // make the move   
                    
          int val = minMax( B, depth + 1 );  //recursion
                    
          if(val > max)
          {
            max = val; 
          }
                    
          B[move/length][move%width] = 0;       // undo the move and try next move (for loop increments)
        }
      }  
      return max; 
    } 
    else 
    {                         // odd level of depth is min node, human player
      int min = 1000; 
      for(int move = 0; move < 9; ++move)
      {
        if(B[move/length][move%width] == 0)
        {   // if move is available
                    
          B[move/length][move%width] = 1;       // make the move  
                    
          int val = minMax( B, depth + 1 );  //recursion
                    
          if(val < min)
          { 
            min = val; 
          }
                    
          B[move/length][move%width] = 0;       // undo the move and try next move (for loop increments)  
        }
      }
      return min;
    }
  }
  
  /* Rates bored based on number of red and blue pieces and possibilities to win.
   * @param int[][] B: playing board
   * @return int: rating for board
   * Rough pseudocode found @ http://stackoverflow.com/questions/10985000/how-should-i-design-a-good-evaluation-function-for-connect-4?rq=1.
   */
  public static int eval(int[][] B)
  {  
    //check if there is a draw
    int notBlank = 0; //counts how many spaces are not empty
    int counter = 0; //counts total number of spaces
    for(int[] row: B)
    {
      for(int space: row)
      {
        if(space != 0) //if space is full
        {
          notBlank++;
        }
        counter++;
      }
    }
    if(notBlank == counter) //if board is full
    {
      return 0; //draw
    }
    
    
    int sum = 0;
    
    //evaluate by row (from top)
    for(int row = 0; row < B.length; row++)
    {
      for(int col = 0; col < B[0].length - 3; col++)
      {
        int set = B[row][col] + B[row][col+1] + B[row][col+2] + B[row][col+3];
        int result = evalHelper(B, set, row, col);
        if(result == Inf || result == -Inf)
        {
          return result;
        }
        else
        {
          sum += result;
        }
      }
    }
    
    //evaluate by column (from left)
    for(int col = 0; col < B.length; col++)
    {
      for(int row = 0; row < B.length - 3; row++)
      {
        int set = B[row][col] + B[row+1][col] + B[row+2][col] + B[row+3][col];
        int result = evalHelper(B, set, row, col);
        if(result == Inf || result == -Inf)
        {
          return result;
        }
        else
        {
          sum += result;
        }
      }
    }
    
    //evaluate by diagonal (from top left)
    for(int row = 0; row < B.length - 3; row++)
    {
      for(int col = 0; col < B[0].length - 3; col++)
      {
        int set = B[row][col] + B[row+1][col+1] + B[row+2][col+2] + B[row+3][col+3];
        int result = evalHelper(B, set, row, col);
        if(result == Inf || result == -Inf)
        {
          return result;
        }
        else
        {
          sum += result;
        }
      }
    }
    
    //evaluate by diagonal (from bottom left)
    for(int row = B.length - 1; row > 3; row--)
    {
      for(int col = 0; col < B[0].length - 3; col++)
      {
        int set = B[row][col] + B[row-1][col+1] + B[row-2][col+2] + B[row-3][col+3];
        int result = evalHelper(B, set, row, col);
        if(result == Inf || result == -Inf)
        {
          return result;
        }
        else
        {
          sum += result;
        }
      }
    }
    return sum;
  }
  
  
  /* Prevents some repetition of code within the eval() method.
   * @param int[][] B: playing board
   * @param sum: sum of values of 4 consecutive spaces on board
   * @param row: row index of 1st of these consecutive spaces
   * @param col: column index of 1st of these consecutive spaces
   * @return int:Inf if win, -Inf if loss, 1 if B[row][col] is Blue, -1 if B[row][col] is Red 
   */
  public static int evalHelper(int[][] B, int sum, int row, int col)
  {
    if(sum == 40)
    {
      return Inf;
    }
    else if(sum == -4)
    {
      return -Inf;
    }
    else
    {
      if(B[row][col] == 10)
      {
        return 1;
      }
      if(B[row][col] == 1)
      {
        return -1;
      }
    }
    return 0; //necessary for compilation
  }
}