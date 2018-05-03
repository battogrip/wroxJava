// Chapter 21 Exercise 1
//Print the title on the top of the printed sketch page.
/*
  The additional code to do this is in the print() method in the SketchView class.
  This requires import statements for java.awt.Color and java.awt.geom.Rectangle2D to
  be added to the SketchView source file.
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

    window.addWindowListener(new WindowAdapter() {// Add window listener
                               public void windowClosing(WindowEvent e) {
                                 window.checkForSave();
                               }
                             }  );
    sketch = new SketchModel();                   // Create the model
    view = new SketchView(this);                  // Create the view
    sketch.addObserver(view);                     // Register view with the model
    sketch.addObserver(window);                   // Register window with the model
    window.getContentPane().add(view, BorderLayout.CENTER);
    window.setVisible(true);
  }

  // Insert a new sketch model
  public void insertModel(SketchModel newSketch) {
    sketch = newSketch;                          // Store the new sketch
    sketch.addObserver(view);                    // Add the view as observer
    sketch.addObserver(window);                  // Add the app window as observer
    view.repaint();                              // Repaint the view
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

  private SketchModel sketch;                    // The data model for the sketch
  private SketchView view;                       // The view of the sketch

  private  SketchFrame window;                   // The application window
  private static  Sketcher theApp;               // The application object
}
