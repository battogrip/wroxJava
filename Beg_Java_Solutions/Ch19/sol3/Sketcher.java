// Chapter 19 Exercise 3
// Add fill support for rectangles, circles, and ellipses
/*
  Elements are created as filled or unfilled depending on the state of the button
  determining fill. The fill button is 'unfilled' initially, and toggles between
  the filled and unfilled state when it is clicked.

 SketchFrame class:
 - added method isFillOn()to return true if fill is on for the fill button. The fill button
   indicates an element is to be filled when it is in its selected state.

 Element class:
 - added a fill member of type boolean with a default of false. This will indicate
   whether or not the element is to be filled
 - added isFilled() method to return the value of fill.
 - added Element constructor to accept an argument of type boolean for fill field
 - added constructors to the Rectangle, Circle, and Ellipse inner classes
   that accepts an additional boolean argument specifying the fill state. 

 SketchView.MouseHandler class:
 - modified the switch statement in the createElement() method to call constructors
   for the Element.Rectangle, Element.Circle, and Element.Ellipse classes with a
   value for fill
   
 Sketchview class:
 - modified the paint() method to check the fill state for an element and fill it
   when required
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
