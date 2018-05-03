// Chapter 25 Exercise 2
/*
  This is a development of the solution to Exercise 1, which also incorporates a 
  DatabaseDialog class that extends JDialog to allows the driver class, database URL, 
  username, and password to be selected. 
  The DatabaseDialog constructor requires a InteractiveSQL argument, so that 
  the DatabaseDialog can call the InteractiveSQL object's connectToDatabase() method. 
  The DatabaseDialog constructor also requires four String arguments to set its initial values.
  
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

    // Add listeners for text field and menu items
    command.addActionListener(this);
    clearQueryItem.addActionListener(this);
    changeDatabaseItem.addActionListener(this);
    exitItem.addActionListener(this);

    // Establish a database connection and set up the table
    try {
      connectToDatabase(driver, url, user, password);
    } catch(ClassNotFoundException cnfe) {
      System.err.println(cnfe);                        // Driver not found
    } catch(SQLException sqle) {
      System.err.println(sqle);                        // error connection to database
    }
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
                          String password) throws ClassNotFoundException, SQLException {
    Class.forName(driverClass);                  // Load the driver
    connection = DriverManager.getConnection(database, username, password);
    statement = connection.createStatement();
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
      status.setText(sqle.getMessage());        // Display error message
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
}
