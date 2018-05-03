// Chapter 20 Exercise 3
// Display a special context menu for Element.Text elements that allows the text to
// be updated.
/*
  This is simply a question of explictly looking for an Element.Text item under the
  cursor and displaying a special context menu in response to the popup trigger. 
  You can do this by adding the extra menu item when it is required.

  
  
  SketchView class:
   - added editItem as a field of type JMenuItem to be added to the special
     popup menu for a Text element
   - modified the process PopupTrigger() method in the MouseHandler inner class to deal
     with Element.Text as a special case. We add the editItem to the popup when the
     element is Element.Text, and remove this menu item when it is not. Adding the 
     same menu item more than once has no effect, as does attempting to remove a
     menu item that is not present so we don't need to do any checks before these
     operations 
   - modified the actionPerformed() menu to respond to Edit action events.

  Element.Text class:
   - added a getFont() method to return the font for the element
   - added a getText() method to return the test for the element
   - added a setText() method to set new text for the element
     and update the bounds accordingly
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
