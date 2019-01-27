/* Title:  TicTacToe.java
 * Author: Wayne Snyder (waysnyder@gmail.com)
 * Date: 11/21/13
 * Purpose: This is basic example of a game program
 */ 

import java.util.*;

public class TicTacToe {
    
    private static int[][] B = new int[3][3];
    
    private final static int X = 10;    
    private final static int O = 1; 
    private final static int Blank = 0; 
    
    private final static int Inf = 1000000; 
    
    private static String[] Piece = new String[11];    // for print names of pieces
    
    // static initialization code
    static {
        Piece[0] = " ";                 // will only use indices 0, 1, & 10
        Piece[X] = "X";
        Piece[O] = "O";
    }
    
    private static final int D = 10; 
    
    
    // moves are 0 .. 9 (counting slots across rows and down columns)
    // empty slot is 0, O is 1 and X is 10
    
    private static  int column(int move) {
        return move % 3;
    }
    
    private static  int row(int move) {
        return move / 3;
    }
    
    private static  int chooseMove(int[][] B) { 
        int max = -1000;     
        int bestMove = -1;  
        for(int move = 0; move < 9; ++move) { 
            
            if(B[row(move)][column(move)] == Blank) {   // move is available
                
                B[row(move)][column(move)] = X;       // make the move
                
                int val = minMax( B, 1 ); 
                
                if(val > max) {                       // if better move, remember it
                    bestMove = move; 
                    max = val; 
                }
                
                B[row(move)][column(move)] = Blank;        // undo the move
                
            }  // end if
        }  // end for
        return bestMove; 
    } 
    
    
// return true if no blank slots
    
    private static  boolean isLeaf(int[][] B) {
        if(winForX(B) || winForO(B))
            return true; 
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 3; ++col) {
                if(B[row][col] == Blank)
                    return false;
            }
        }
        return true;
    }
    
    private static boolean winForX(int[][] B) {
        return (
                ((B[0][0]+B[0][1]+B[0][2]) == 30) ||
                ((B[1][0]+B[1][1]+B[1][2]) == 30) ||
                ((B[2][0]+B[2][1]+B[2][2]) == 30) ||
                ((B[0][0]+B[1][0]+B[2][0]) == 30) ||
                ((B[0][1]+B[1][1]+B[2][1]) == 30) ||
                ((B[0][2]+B[1][2]+B[2][2]) == 30) ||
                ((B[0][0]+B[1][1]+B[2][2]) == 30) ||
                ((B[2][0]+B[1][1]+B[0][2]) == 30) 
               );
    }
    
    private static boolean winForO(int[][] B) {
        return (
                ((B[0][0]+B[0][1]+B[0][2]) == 3) ||
                ((B[1][0]+B[1][1]+B[1][2]) == 3) ||
                ((B[2][0]+B[2][1]+B[2][2]) == 3) ||
                ((B[0][0]+B[1][0]+B[2][0]) == 3) ||
                ((B[0][1]+B[1][1]+B[2][1]) == 3) ||
                ((B[0][2]+B[1][2]+B[2][2]) == 3) ||
                ((B[0][0]+B[1][1]+B[2][2]) == 3) ||
                ((B[2][0]+B[1][1]+B[0][2]) == 3) 
               );
    }
    
    private static  int eval(int[][] B) {
        
        if(winForO(B))
            return -Inf;
        else if(winForX(B))
            return Inf; 
        
        int sum = 0;
        
        // count rows
        for(int r = 0; r < 3; ++r) 
            sum += countPieces(B[r][0],B[r][1],B[r][2]);
        
        // count columns
        for(int c = 0; c < 3; ++c) 
            sum += countPieces(B[0][c],B[1][c],B[2][c]);
        
        // count diagonals
        sum += countPieces(B[0][0],B[1][1],B[2][2]);
        
        sum += countPieces(B[0][2],B[1][1],B[2][0]);
        
        return sum;
        
    }
    
    
    // Given three board values (where X = 10, O = 1, blank  = 0), 
    // counts number of X's if only X's occur, result is positive;
    // counts number of O's if only O's occur, result is negative. 
    // Sum of these two is returned.
    
    private static int countPieces(int a, int b, int c) {
        int n = a + b + c;
        int numX = n / 10;
        int numO = n % 10;
        if(numX > 0 && numO > 0)
            return 0;        // no move for either in this row, return 0
        else if(numO == 0)  // only X's in this sequence
            return numX;
        else if(numX == 0)  // only O's in this sequence
            return -numO; 
        return 0;           // needed for compilation
    }
    
    
    private static int minMax(int[][] B, int depth) { 
        if( isLeaf(B) || depth == D)  
            return eval(B);  
        else if( depth % 2 == 0 ) {       // even levels are max, X player           
            int max = -Inf; 
            for(int move = 0; move < 9; ++move) {
                if(B[row(move)][column(move)] == 0) {   // move is available  //col(move) == move % 3
                    
                    B[row(move)][column(move)] = X;       // make the move    //row(move) == move / 3
                    
                    int val = minMax( B, depth + 1 );  
                    
                    if(val > max) { 
                        max = val; 
                    }
                    
                    B[row(move)][column(move)] = Blank;       // undo the move and try next move 
                }
            }
            return max; 
        } else {                          // is a min node, O player
            int min = 1000; 
            for(int move = 0; move < 9; ++move) {
                if(B[row(move)][column(move)] == 0) {   // move is available
                    
                    B[row(move)][column(move)] = O;       // make the move  
                    
                    int val = minMax( B, depth + 1 ); 
                    
                    if(val < min) { 
                        min = val; 
                    }
                    
                    B[row(move)][column(move)] = Blank;       // undo the move and try next move    
                }
            }
            return min; 
        }
    }
    
    private static void printBoard(int [][] B) {
        printBoard(B,0);
    }
    
    private static void printBoard(int [][] B, int ind) {
        
        String indent = "";
        for(int i = 0; i < ind; ++i)
            indent += "\t";
        
        System.out.println(indent + "\t-------------");
        System.out.println(indent + "\t| " + Piece[B[0][0]] + " | " + Piece[B[0][1]] + " | " + Piece[B[0][2]] + " |"); 
        System.out.println(indent + "\t-------------");
        System.out.println(indent + "\t| " + Piece[B[1][0]] + " | " + Piece[B[1][1]] + " | " + Piece[B[1][2]] + " |"); 
        System.out.println(indent + "\t-------------");
        System.out.println(indent + "\t| " + Piece[B[2][0]] + " | " + Piece[B[2][1]] + " | " + Piece[B[2][2]] + " |"); 
        System.out.println(indent + "\t-------------"); 
    }
    
    
    
    public static void main(String [] args) {
        
        System.out.println("Tic-Tac-Toe Game: You are playing O, and the computer");
        System.out.println("\twill play X; type in a move using digits 0 .. 8:");
        System.out.println("\t\t-------------");
        System.out.println("\t\t| 0 | 1 | 2 |"); 
        System.out.println("\t\t-------------");
        System.out.println("\t\t| 3 | 4 | 5 |");  
        System.out.println("\t\t-------------");
        System.out.println("\t\t| 6 | 7 | 8 |");  
        System.out.println("\t\t-------------"); 
        System.out.println("\tTo end the game early, type Control-D."); 
        int moveX  = chooseMove(B);         
        B[row(moveX)][column(moveX)] = X;
        
        System.out.println("\nX's Move:");
        printBoard(B); 
        Scanner sc = new Scanner(System.in); 
        System.out.println("Make your move:"); 
        while(sc.hasNextInt()) { 
            // O's move
            int moveO = sc.nextInt(); 
            if(B[row(moveO)][column(moveO)] == Blank) {
                B[row(moveO)][column(moveO)] = O;
                printBoard(B);
                if(winForO(B)) {
                    System.out.println("Win for O!");
                    break;
                }
                moveX  = chooseMove(B);         
                B[row(moveX)][column(moveX)] = X;
                System.out.println("\nX's Move:");
                printBoard(B);
                if(winForX(B)) {
                    System.out.println("Win for X!");
                    break;
                }
                if(isLeaf(B)) {
                    System.out.println("Tie Game!"); 
                    break; 
                }
            }
            else {
                System.out.println("Illegal move, try again...."); 
            }
            
        }
        
        
    }
    
    
}