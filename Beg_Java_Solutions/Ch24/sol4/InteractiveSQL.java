// Chapter 24 Exercise 4
/*
  JTextField and JLabel components are added at the top of the user interface,
  to provide for the necessary input. 
  A JButton component is also added, and when this is clicked the executeSQL() method
  calls the createQuery() method, which assembles the SQL command from the contents of
  the individual JTextField components.
  I used Box containers to arrange all the components at the top of the window for a change.
  Note how you can use struts to space components out.
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
import javax.swing.JButton;               
import javax.swing.JTable;       
import javax.swing.JLabel;       
import javax.swing.JOptionPane;       
import javax.swing.Box;       
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

     Box top = Box.createVerticalBox();
    // Add the input components for SQL statements at the top
    Box commandBox = Box.createHorizontalBox();
    commandBox.add(Box.createHorizontalStrut(5));
    commandBox.add(label1);
    commandBox.add(Box.createHorizontalStrut(3));
    commandBox.add(columnsToSelect);
    columnsToSelect.setToolTipText("Enter columns to select");
    
    commandBox.add(Box.createHorizontalStrut(5));
    commandBox.add(label2);
    commandBox.add(Box.createHorizontalStrut(3));
    commandBox.add(tablesToSelectFrom);
    tablesToSelectFrom.setToolTipText("Enter tables to select from");

    commandBox.add(Box.createHorizontalStrut(5));
    commandBox.add(label3);
    commandBox.add(Box.createHorizontalStrut(3));
    commandBox.add(selectionCriteria);
    selectionCriteria.setToolTipText("Enter row selection criteria (optional)");

    commandBox.add(Box.createHorizontalStrut(5));
    commandBox.add(label4);
    commandBox.add(Box.createHorizontalStrut(3));
    commandBox.add(ordering);
    ordering.setToolTipText("Enter ordering criteria (optional)");
    commandBox.add(Box.createHorizontalStrut(5));
    
    Box goBox = Box. createHorizontalBox();
    goBox.add(goButton);
    goButton.setToolTipText("Click to perform query");
    goButton.addActionListener(this);
    


    driverClassField.setText(driver);
    databaseField.setText(url);
    usernameField.setText(user);
    passwordField.setText(password);

    Box dbBox = Box.createHorizontalBox();
    Box dbLabels = Box.createVerticalBox();
    Box dInputs = Box.createVerticalBox();
    dbBox.add(Box.createHorizontalStrut(10));
    dbBox.add(dbLabels);
    dbBox.add(Box.createHorizontalStrut(10));
    dbBox.add(dInputs);
    dbLabels.add(driverClassLabel);
    dbLabels.add(databaseLabel);
    dbLabels.add(usernameLabel);
    dbLabels.add(passwordLabel);
    dInputs.add(driverClassField);
    dInputs.add(databaseField);
    dInputs.add(usernameField);
    dInputs.add(passwordField);

    top.add(Box.createVerticalStrut(10));
    top.add(dbBox);
    top.add(Box.createVerticalStrut(10));
    top.add(commandBox);
    top.add(Box.createVerticalStrut(10));
    top.add(goBox);
    top.add(Box.createVerticalStrut(10));
    getContentPane().add(top, BorderLayout.NORTH);


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
    goButton.addActionListener(this);
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
    if(source == goButton) {                     // Enter key for text field input
      executeSQL();
    } else if(source == clearQueryItem) {        // Clear query menu item
      columnsToSelect.setText("");              // Clear SQL entries
      tablesToSelectFrom.setText("");
      selectionCriteria.setText("");
      ordering.setText("");
    } else if(source == exitItem) {              // Exit menu item
      dispose();                                 // Release the window resources
      System.exit(0);                            // End the application
    }    
  }

  // Executes an SQL command entered in the text field
  public void executeSQL() {
    String query = createQuery();                // Get the SQL statement
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

  public String createQuery() {
    String columns = columnsToSelect.getText();
    String tables = tablesToSelectFrom.getText();

    if ((columns == null) || (tables == null) 
                          || (columns.length() == 0) || (tables.length() == 00)) {
      JOptionPane.showMessageDialog(this, "You must enter values in the first two fields",
                                    "SQL Error", JOptionPane.ERROR_MESSAGE);
      return null;
    }

    StringBuffer query = new StringBuffer("SELECT ");
    query.append(columns);
    query.append(" FROM ");
    query.append(tables);

    String criteria = selectionCriteria.getText();

    if ((criteria != null) && (criteria.length() != 0)) {
      query.append(" WHERE ");
      query.append(criteria);
    }

    String order = ordering.getText();

    if ((order != null) && (order.length() != 0)) {
      query.append(" ORDER BY ");
      query.append(order);
    }
    return query.toString();
  }

  JLabel label1 = new JLabel("SELECT");
  JTextField columnsToSelect = new JTextField(15);
  JLabel label2 = new JLabel("FROM");
  JTextField tablesToSelectFrom = new JTextField(15);
  JLabel label3 = new JLabel("WHERE");
  JTextField selectionCriteria = new JTextField(15);
  JLabel label4 = new JLabel("ORDER BY");
  JTextField ordering = new JTextField(15);

  JButton goButton = new JButton("Execute Query");

  JLabel driverClassLabel = new JLabel("JDBC Driver class:");
  JLabel databaseLabel = new JLabel("JDBC database URL:");
  JLabel usernameLabel = new JLabel("Username:");
  JLabel passwordLabel = new JLabel("Password:");
  JLabel commandLabel = new JLabel("SQL command:");
  
  JTextField driverClassField = new JTextField();
  JTextField databaseField = new JTextField();      // Input area for database name
  JTextField usernameField = new JTextField();
  JPasswordField passwordField = new JPasswordField();

  JTextArea status = new JTextArea(3,1);      // Output area for status and errors
  JScrollPane resultsPane;

  JMenuBar menuBar = new JMenuBar();                        // The menu bar
  JMenuItem clearQueryItem = new JMenuItem("Clear query");  // Clear SQL item
  JMenuItem exitItem = new JMenuItem("Exit");               // Exit item

  Connection connection;                       // Connection to the database
  Statement statement;                         // Statement object for queries
  ResultsModel model;                          // Table model for resultset
}
