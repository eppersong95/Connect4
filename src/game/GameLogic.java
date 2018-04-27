package game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//class for controlling game results
public class GameLogic
{
  private char[][] board;
  private char player;
  private int row;
  public final int xIncr = 90;
  public final int yIncr = 80;
  public final int BASEX = 90;
  public final int BASEY = 152;
  
  public GameLogic()
  {
    board = new char[6][7];
    player = 'r';
  }
  
  //returns piece object if move valid
  public Piece newMove(int column)
  {
    int row;
    for(row = 5; row >= 0; row--)
    {
      if (board[row][column] == 0)
      {
        board[row][column] = player;
        int x = BASEX + (column*xIncr);
        int y = BASEY + (row * yIncr);
        char oldPlayer = player;
        this.row = row;
        return (new Piece(x,y,oldPlayer));
      }
    }
    
    return null;
  }
  
  //swap player
  public void swapPlayer() {
	    if(player == 'r')
	    {
	      player = 'b';
	    }
	    
	    else
	    {
	      player = 'r';
	    }
  }
  
  //checks for tie
  public boolean isTie() {
	  for(int i = 0; i < 6; i++) {
		  for(int j = 0; j<7; j++) {
			  if(board[i][j] == 0) {
				  return false;
			  }
		  }
	  }
	  
	  return true;
  }
  
  //checks for win
  public boolean isWin(int col)
  {
    boolean lr = checkLR(row, col, player);
    boolean ud = (checkUD(row,col,player,0) > 3); 
    boolean rd = checkRD(row,col,player);
    boolean ld = checkLD(row,col,player);
    swapPlayer();
    return (lr|ud|rd|ld);
  }
  
  //checks win from left to right
  public boolean checkLR(int row, int col, char player)
  {
    int inARow = 0;
    
    while(col > 0)
    {
      if(board[row][col-1] == player)
      {
        col--;
      }
      else {break;}
    }
    
    while(col < 7)
    {
      if(board[row][col] == player)
      {
        col++;
        inARow++;
      }
      else {break;}
    }
    
    return (inARow > 3);
  }
  
  //checks win from up to down
  public int checkUD(int row, int col, char player, int inARow)
  {
    if(row < 5)
    {
      if(board[row+1][col] == player)
      {
        inARow = checkUD(row+1, col, player, inARow);
      }
    }
    
    return inARow + 1;
  }
  
  //checks right diagonal win
  public boolean checkRD(int row, int col, char player)
  {
    int inARow = 0;
    
    while((row < 5) && (col > 0))
    {
      if(board[row+1][col-1] == player)
      {
        row++;
        col--;
      }
      else {break;}
    }
    
    while((row >= 0) && (col < 7 ))
    {
      if(board[row][col] == player)
      {
        row--;
        col++;
        inARow++;
      }
      else {break;}
    }
    
    return (inARow > 3);
  }
  
  //checks left diagonal win
  public boolean checkLD(int row, int col, char player)
  {
    int inARow = 0;
    
    while((row > 0) && (col > 0))
    {
      if(board[row-1][col-1] == player)
      {
        row--;
        col--;
      }
      else {break;}
    }
    
    while((row < 6) && (col < 7))
    {
      if(board[row][col]==player)
      {
        row++;
        col++;
        inARow++;
      }
      else {break;}
    }
    
    return(inARow > 3);
  }
}

