// Chapter 24 Exercise 3
/*
  A new method, connectToDatabase(), is added to the InteractiveSQL class to connect
  to the specified database using the given driver class, username and password. 
  Four JTextField components, and corresponding JLabels, are added to the top panel
  to receive the requisite information; each has the InteractiveSQL object added
  as an ActionListener, and when Return is pressed the program attempts to connect
  to the currently-specified database.
*/

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;          
import java.awt.event.WindowEvent;          
import java.awt.event.ActionEvent;          
import java.awt.event.ActionListener;          
import javax.swing.JFrame;               
import javax.swing.JTextField;               
import javax.swing.JPasswordField;               
import javax.swing.JTextArea;               
import javax.swing.JMenu;               
import javax.swing.JMenuBar;               
import javax.swing.JMenuItem;               
import javax.swing.JScrollPane;               
import javax.swing.JTable;       
import javax.swing.JPanel;       
import javax.swing.JLabel;       
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

  public InteractiveSQL(String driver, String url, String user , String password) {
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

    driverClassField.setText(driver);
    databaseField.setText(url);
    usernameField.setText(user);
    passwordField.setText(password);

    // Add the input for SQL statements at the top
    command.setToolTipText("Key SQL commmand and press Enter");
    command.addActionListener(this);

    driverClassField.setToolTipText("Key JDBC driver class and press Enter");
    driverClassField.addActionListener(this);

    databaseField.setToolTipText("Key JDBC database URL and press Enter");
    databaseField.addActionListener(this);

    usernameField.setToolTipText("Key database username and press Enter");
    usernameField.addActionListener(this);

    passwordField.setToolTipText("Key database password and press Enter");
    passwordField.addActionListener(this);

    topPanel.setLayout(new GridLayout(5,2));
    topPanel.add(driverClassLabel);
    topPanel.add(driverClassField);
    topPanel.add(databaseLabel);
    topPanel.add(databaseField);
    topPanel.add(usernameLabel);
    topPanel.add(usernameField);
    topPanel.add(passwordLabel);
    topPanel.add(passwordField);
    topPanel.add(commandLabel);
    topPanel.add(command);

    getContentPane().add(topPanel, BorderLayout.NORTH);

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
      connectToDatabase();
    } catch(ClassNotFoundException cnfe) {
      System.err.println(cnfe);                    // Driver not found
    } catch(SQLException sqle) {
      System.err.println(sqle);                    // error connection to database
    }

    model = new ResultsModel();                  // Create a table model
    JTable table = new JTable(model);            // Create a table from the model
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);   // Use scrollbars
  
    resultsPane = new JScrollPane(table);        // Create scrollpane for table
    getContentPane().add(resultsPane, BorderLayout.CENTER);

    pack();
    setVisible(true);
  }

  // Connect to a database
  public void connectToDatabase() throws ClassNotFoundException, SQLException {
    Class.forName(driverClassField.getText());                  // Load the driver
    connection = DriverManager.getConnection(databaseField.getText(),
                                             usernameField.getText(),
                                             new String(passwordField.getPassword()));
    statement = connection.createStatement();
  }

  // Handles action events for menu items and the text field
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if(source == command) {                      // Enter key for text field input
      executeSQL();
    } else if(source == clearQueryItem) {        // Clear query menu item
      command.setText("");                       // Clear SQL entry
    } else if(source == exitItem) {              // Exit menu item
      dispose();                                 // Release the window resources
      System.exit(0);                            // End the application
    }    
  }

  // Executes an SQL command entered in the text field
  public void executeSQL() {
    String query = command.getText();            // Get the SQL statement
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

  JPanel topPanel = new JPanel();              // Panel for command string and database name
  JLabel driverClassLabel = new JLabel("JDBC Driver class:");
  JLabel databaseLabel = new JLabel("JDBC database URL:");
  JLabel usernameLabel = new JLabel("Username:");
  JLabel passwordLabel = new JLabel("Password:");
  JLabel commandLabel = new JLabel("SQL command:");
  
  JTextField driverClassField = new JTextField();
  JTextField databaseField = new JTextField();      // Input area for database name
  JTextField usernameField = new JTextField();
  JPasswordField passwordField = new JPasswordField();
  JTextField command = new JTextField();      // Input area for SQL
  JTextArea status = new JTextArea(3,1);      // Output area for status and errors
  JScrollPane resultsPane;

  JMenuBar menuBar = new JMenuBar();                        // The menu bar
  JMenuItem clearQueryItem = new JMenuItem("Clear query");  // Clear SQL item
  JMenuItem exitItem = new JMenuItem("Exit");               // Exit item

  Connection connection;                       // Connection to the database
  Statement statement;                         // Statement object for queries
  ResultsModel model;                          // Table model for resultset
}
