// Chapter 25 Exercise 1
/*
  A JComboBox is added to contain the previously-entered queries;
  it is set to be editable so that new queries may also be entered.
  The InteractiveSQL object is set to be its actionListener. 
  When a new query is entered or a previous one is re-selected,
  the actionPerformed() method is called with the JComboBox as its event source.

  The JComboBox getSelectedItem() method is used to find out which entry was selected;
  -1 indicates a new entry. If it is a new entry, it is added to the JComboBox's list 
  of entries at position 0 (the top). If the number of entries is now 11, 
  the final entry (number 10) is removed, resulting in the JComboBox never holding
  more than 10 items.

  Whichever position was occupied by the selected entry, its text is used to form a SQL query.
*/


import java.awt.BorderLayout;
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
    fileMenu.add(exitItem);                      // Add exit item
    menuBar.add(fileMenu);                       // Add menu to the menubar
    setJMenuBar(menuBar);                        // Add menubar to the window

    // Add listeners for text field and menu items
    command.addActionListener(this);
    clearQueryItem.addActionListener(this);
    exitItem.addActionListener(this);

    // Establish a database connection and set up the table
    try {
      Class.forName(driver);                     // Load the driver
      connection = DriverManager.getConnection(url, user, password);
      statement = connection.createStatement();

      model = new ResultsModel();                // Create a table model
      JTable table = new JTable(model);          // Create a table from the model
      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);   // Use scrollbars
      resultsPane = new JScrollPane(table);      // Create scrollpane for table
      getContentPane().add(resultsPane, BorderLayout.CENTER);
    } catch(ClassNotFoundException cnfe) {
      System.err.println(cnfe);                    // Driver not found
    } catch(SQLException sqle) {
      System.err.println(sqle);                    // error connection to database
    }
    pack();
    setVisible(true);
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

  JMenuBar menuBar = new JMenuBar();                        // The menu bar
  JMenuItem clearQueryItem = new JMenuItem("Clear query");  // Clear SQL item
  JMenuItem exitItem = new JMenuItem("Exit");               // Exit item

  Connection connection;                       // Connection to the database
  Statement statement;                         // Statement object for queries
  ResultsModel model;                          // Table model for resultset
}
