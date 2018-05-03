// Chapter 20 Exercise 6
// Add a scale menu item to the element context menu
/*
  The obvious mechanism to use for scaling in the scaling affine transform capability.
  This requires modifying the Element class to include a scale tranform when drawing
  an element. It is important not to forget to scale the bounding rectangle too!
  
  To implement scaling I used the mouseDragged() method in the MouseHandler inner class
  to SketchView. I chose to implement scaling in x and y independently. You could 
  implement scaling such that scaling in x and y  would always be the same and this 
  would be easier, but a lot less interesting.
  
  The tricky part to using mouse dragging for scaling is to devise a scheme such that the
  scaling does not run out of control or result in a scale of zero. To determine the scale
  factor, I used the ratio of the distance from the start position of the cursor to the 
  element origin, and the distance between the last position and the  element origin,
  but updating the start position to the last position for each event. This results in
  a scale factor that is always close to 1.0, either slightly over or slightly under.
  
  
  To implement independent x and y scaling, I calculate the x and y ratios separately.
  
  You can see that the line thickness is also scale by the scaling transformation. Note
  scaling only applies to geometry, not Text elements. To scale text you would need to
   modify the point size. 
  
  
  SketchView class:
   - added the scaleItem field
   - created the scale action item and added a menu item for it to the element menu
   - modified the actionPerformed() method to process action events from the
     Scale menu item.
   - added an import for Point2D
   - added a field, elementPosition, of type Point2D>Double to the MouseHandler
     class to record the position of the current element 
   - modified the mousePressed() method in MouseHandler to obtain the position of
     the current element when SCALE mode is in effect.
   - modified the mouseDragged() method in MouseHandler to do the element scaling
     when SCALE mode is in effect and the element is a geometric element.
   - modified the processPopupTrigger() method in the inner MouseHandler class to
     show the Scale menu item in the popup menu as long as the element is not Text
     
  SketcherConstants class:      
  - added the definition of the SCALE mode constamt
  
  Element class:
  - added xScale and yScale fields to record the x and y scale factors for an element.
    with a default scale of 1.0 in both x and y. This allows independent scaling
    in x and y.
  - added a scale() method that applies a scaling to the existing scale factors.
  - modified the draw() method to apply a scaling transform
  - modified the getBounds() method to apply a scaling transform.
  
  I did not update the getInfo() method in the element classes. You could easily amend
  these to output the scale factor. A slightly complication arises with circles. If 
  the x and y scales are not the same then the circle will be an ellipse so you
  should then output the width and height.
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
