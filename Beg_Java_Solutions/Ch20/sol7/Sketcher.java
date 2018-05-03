// Chapter 20 Exercise 7
// Add a main menu item and a toolbar button for implementing a custom color.

/*
  You already have a Custom Color... menu item in the popup context menu but the point
  here is that you can't just add that menu item to another menu because a JMenuItem 
   object can only appear in one menu. A menu item that you want to appear in more
   than one menu needs an Action object as its origin. 
  
  I just deleted the ColorDialogAction class (in SketchFrame) that I used in my solution
  for Exercise 1 along with the toolbar button that used it, and replaced it with a 
  CustomColorAction inner class that I use here to create an item in three places: 
     - in the Color submenu in the main menu,
     - in the popup menu, 
     - and as a toolbar button. 
  I used the .gif from Exercise 1 to save having to think up a new one :-)
  
  Of course, it was also necessary to remove the code from the actionPerformed method
  in SketchFrame that handled the original popup item for custom colors.
  
  The original field for the popup menu item has been replaced by a field of type
  CustomColorAction. This action object now supports two menu items plus the toolbar
  button.

*/

// Sketching application
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class Sketcher {
  public static void main(String[] args) {
    theApp = new Sketcher();
    SwingUtilities.invokeLater(
       new Runnable() {                          // Anonymous Runnable class object
                        public void run() {      // Run method executed in thread
                          theApp.creatGUI();     // Call static GUI creator
                        }
                      }       );
  }

  // Method to create the application GUI
  private void creatGUI() {
    window = new SketchFrame("Sketcher", this);  // Create the app window
    Toolkit theKit = window.getToolkit();        // Get the window toolkit
    Dimension wndSize = theKit.getScreenSize();  // Get screen size

    // Set the position to screen center & size to half screen size
    window.setBounds(wndSize.width/4, wndSize.height/4,        // Position
                      wndSize.width/2, wndSize.height/2);      // Size

    window.addWindowListener(new WindowHandler());// Add window listener
    sketch = new SketchModel();                  // Create the model
    view = new SketchView(this);                 // Create the view
    sketch.addObserver(view);                    // Register view with the model
    window.getContentPane().add(view, BorderLayout.CENTER);
    window.setVisible(true);
  }

  // Return a reference to the application window
  public SketchFrame getWindow() { 
     return window; 
  }

  // Return a reference to the model
  public SketchModel getModel() { 
     return sketch; 
  }

  // Return a reference to the view
  public SketchView getView() { 
     return view; 
  }

  // Handler class for window events
  class WindowHandler extends WindowAdapter {
    // Handler for window closing event
    public void windowClosing(WindowEvent e) {
    }
  }

  private SketchModel sketch;                    // The data model for the sketch
  private SketchView view;                       // The view of the sketch

  private  SketchFrame window;                   // The application window
  private static  Sketcher theApp;               // The application object
}
