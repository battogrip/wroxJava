// Chapter 17 Exercise 2
// Adding six button in a vertical column on the left side

import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.BorderLayout;

public class SquareWindow extends JFrame {
  public SquareWindow(String title) {
    super(title);

    Toolkit theKit = this.getToolkit();
    Dimension wndSize = theKit.getScreenSize();

    // Calculate window side length as half the screen height
    int size = wndSize.width/2;     

    setBounds((wndSize.width - size)/2, (wndSize.height-size)/2,        // Position
                           size, size);                                 // Size
    addButtons();                                      // Add the buttons to the window
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void addButtons() {
      Box vBox = Box.createVerticalBox();        // Create a box to hold the buttons
      
      vBox.add(Box.createVerticalStrut(10));     // Start with a strut for spacing
      vBox.add(Box.createVerticalGlue());        // then glue

      // Add the buttons separated by glue
      JButton button = null;;
      for(int i = 1; i <= 6; i++) {
        vBox.add(button = new JButton("Button" + i));
        vBox.add(Box.createVerticalGlue());
      }
      vBox.add(Box.createVerticalStrut(10));      // Add a strut for end spacing

      // Content pane has BorderLayout by default - add vBox to the WEST
      getContentPane().add(vBox, BorderLayout.WEST);
   }

   public static void main(String[] args) {
      SquareWindow myWindow = new SquareWindow("Chapter 17 Exercise 2 - Look, I have buttons...");
   }
}