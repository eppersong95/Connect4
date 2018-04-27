package game;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//sent to clients to display on gamepanel
public class Piece extends JLabel implements Serializable
{
  String path;
  private int x;
  private int y;
  public final int WIDTH = 80;
  public final int HEIGHT = 80;
  
  
  //sets JLabel icon and bounds
  public Piece(int x, int y, char player)
  {
    if (player == 'r')
    {
      path = "resources/images/red.png";
    }
    
    else
    {
      path = "resources/images/black.png";
    }
    
    this.x = x;
    this.y = y;
    
	this.setIcon(new ImageIcon(path));

  }
  
  public int getX() {
	  return x;
  }
  
  public int getY() {
	  return y;
  }
}
