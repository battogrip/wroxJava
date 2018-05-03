// Chapter 17 Exercise 1
// Creating a square window

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;

public class SquareWindow extends JFrame {
  public SquareWindow(String title) {
    super(title);

    Toolkit theKit = this.getToolkit();
    Dimension wndSize = theKit.getScreenSize();

    // Calculate window side length as half the screen height
    int size = wndSize.width/2;     

    setBounds((wndSize.width - size)/2, (wndSize.height-size)/2,        // Position
                           size, size);                                 // Size

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

   public static void main(String[] args) {
      SquareWindow myWindow = new SquareWindow("Chapter 17 Exercise 1 - Look, I'm square...");
   }
}