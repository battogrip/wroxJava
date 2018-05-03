// Chapter 25 Exercise 3
/*
  This development of solution 2 introduces an additional class, ErrorWindow, that extends JFrame.
  It contains a JScrollPane containing a JTextArea, and a JPanel with two JButtons. 
  It has two methods of interest to our InteractiveSQL class: addSQLException(), which takes
  a SQLException object and writes details of it to the JTextArea, and addMessage(), which 
  adds a String message. One button clears the text and the other hides the window.

  The InteractiveSQL object now calls addSQLException() or addMessage() when an error 
  condition occurs. By default the ErrorWindow is invisible, but calling either of these methods
  makes it visible until the Dismiss button is clicked. 
 
*/


import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;          
import java.awt.event.WindowEvent;          
import java.awt.event.ActionEvent;          
import java.awt.event.ActionListener;          
import javax.swing.JFrame;               
import javax.swing.JTextField;               
import javax.swing.JTextArea;               
import javax.swing.JMenu;               
import javax.swing.JMenuBar;               
import javax.swing.JMenuItem;               
import javax.swing.JScrollPane;               
import javax.swing.JTable;       
import javax.swing.JComboBox;       
import java.sql.DriverManager;                  
import java.sql.Connection;                  
import java.sql.Statement;                  
import java.sql.SQLException;                  

public class InteractiveSQL extends JFrame implements ActionListener {
  public static void main(String[] args) {
   // Set default values for the command line args
   String user     = "guest";
   String password = "guest";
   String url      = "jdbc:odbc:technical_library";
   String driver   = "sun.jdbc.odbc.JdbcOdbcDriver";

   // Up to 4 arguments in the sequence database url,driver url, user ID, password
   switch(args.length) {
     case 4:                                 // Start here for four arguments
       password = args[3];
       // Fall through to the next case
     case 3:                                 // Start here for three arguments
       user = args[2];
       // Fall through to the next case
     case 2:                                 // Start here for two arguments
       driver = args[1];
       // Fall through to the next case
     case 1:                                 // Start here for one argument
       url = args[0];
   }
   InteractiveSQL theApp = new InteractiveSQL(driver, url, user, password);
  }

  public InteractiveSQL(String driver, String url, 
                        String user , String password) {
    super("InteractiveSQL");                     // Call base constructor
    setBounds(0, 0, 400, 300);                   // Set window bounds
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // Close window operation
    addWindowListener(new WindowAdapter() {      // Listener for window close
            // Handler for window closing event
            public void windowClosing(WindowEvent e) {
              dispose();                         // Release the window resources
              System.exit(0);                    // End the application
            }
          } );

    // Add the input for SQL statements at the top
    command.setToolTipText("Key SQL commmand and press Enter");
    command.setEditable(true);
    command.addActionListener(this);
    getContentPane().add(command, BorderLayout.NORTH);

    // Add the status reporting area at the bottom
    status.setLineWrap(true);
    status.setWrapStyleWord(true);
    getContentPane().add(status, BorderLayout.SOUTH);

    // Create the menubar from the menu items
    JMenu fileMenu = new JMenu("File");          // Create File menu
    fileMenu.setMnemonic('F');                   // Create shortcut
    fileMenu.add(clearQueryItem);                // Add clear query item
    fileMenu.add(changeDatabaseItem);            // Menu item to change the DB
    fileMenu.add(exitItem);                      // Add exit item
    menuBar.add(fileMenu);                       // Add menu to the menubar
    setJMenuBar(menuBar);                        // Add menubar to the window

    databaseDialog = new DatabaseDialog(this, driver, url, user, password);
    errorWindow = new ErrorWindow(this);

    // Add listeners for text field and menu items
    command.addActionListener(this);
    clearQueryItem.addActionListener(this);
    changeDatabaseItem.addActionListener(this);
    exitItem.addActionListener(this);

    // Establish a database connection and set up the table
    connectToDatabase(driver, url, user, password);

    model = new ResultsModel();                        // Create a table model
    JTable table = new JTable(model);                  // Create a table from the model
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);   // Use scrollbars
  
    resultsPane = new JScrollPane(table);              // Create scrollpane for table
    getContentPane().add(resultsPane, BorderLayout.CENTER);
    pack();
    setVisible(true);
  }

  // Connect to the database
  public void connectToDatabase(String driverClass, String database, String username,
                          String password) {
    try {
      Class.forName(driverClass);                  // Load the driver
      connection = DriverManager.getConnection(database, username, password);
      statement = connection.createStatement();
      status.setText("Connected to database");
    } catch (ClassNotFoundException cnfe) {
      errorWindow.addMessage("JDBC Driver class " + driverClass + " not found.");
      status.setText("Not connected");
    } catch (SQLException sqle) {
      errorWindow.addSQLException(sqle);
      status.setText("Not connected");
    }
  }

  // Handles action events for menu items and the text field
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if(source == command) {                      // Enter key for text field input
      if (!(command.getSelectedItem().equals(""))) {

        if (command.getSelectedIndex() == -1) {
          command.insertItemAt(command.getSelectedItem(), 0);
          if (command.getItemCount() == 11) {
            command.removeItemAt(10);
          }
        }      
      executeSQL((String)command.getSelectedItem());
      }
    } else if(source == clearQueryItem) {        // Clear query menu item
      command.setSelectedItem("");               // Clear SQL entry
    } else if(source == exitItem) {              // Exit menu item
      dispose();                                 // Release the window resources
      System.exit(0);                            // End the application
    } else if(source == changeDatabaseItem) {
      Rectangle bounds = getBounds();
      databaseDialog.setLocation(bounds.x + bounds.width/3, bounds.y + bounds.height/3);
      databaseDialog.setVisible(true);
    }
   
  }

  // Executes an SQL command entered in the text field
  public void executeSQL(String query) {
    if(query == null||query.length() == 0) {     // If there's nothing we are done
      return;
    }
    try {
      model.setResultSet(statement.executeQuery(query));
      status.setText("Resultset has " + model.getRowCount() + " rows.");
    } catch (SQLException sqle) {
      status.setText("An SQL exception occurred");  // Display error message
      errorWindow.addSQLException(sqle);
    }
  }

  JComboBox command = new JComboBox();        // Receives and stores SQL input
  JTextArea status = new JTextArea(3,1);      // Output area for status and errors
  JScrollPane resultsPane;

  JMenuBar menuBar = new JMenuBar();                                // The menu bar
  JMenuItem clearQueryItem = new JMenuItem("Clear query");          // Clear SQL item
  JMenuItem changeDatabaseItem = new JMenuItem("Change database");  // Change DB item
  JMenuItem exitItem = new JMenuItem("Exit");                       // Exit item

  Connection connection;                       // Connection to the database
  Statement statement;                         // Statement object for queries
  ResultsModel model;                          // Table model for resultset

  DatabaseDialog databaseDialog;               // A reference to the DB dialog
  ErrorWindow errorWindow;                     // Reference to the window displaying errors
}
