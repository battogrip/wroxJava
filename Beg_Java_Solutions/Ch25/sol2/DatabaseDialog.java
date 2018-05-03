import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;                    
import javax.swing.JPanel;                    
import javax.swing.JLabel;                    
import javax.swing.JTextField;                    
import javax.swing.JPasswordField;                    
import javax.swing.JButton; 
                   
import java.sql.SQLException;

public class DatabaseDialog extends JDialog implements ActionListener {
  public DatabaseDialog(InteractiveSQL window,
           String driver, String url, String user, String password) {
    super(window, "Database Selection", true);
    this.window = window;

    JPanel buttonPane = new JPanel();
    buttonPane.add(ok = createButton("OK"));
    buttonPane.add(cancel = createButton("Cancel"));
    getContentPane().add(buttonPane, BorderLayout.SOUTH);

    driverClassField.setText(driver);
    databaseField.setText(url);
    usernameField.setText(user);
    passwordField.setText(password);

    driverClassField.setToolTipText("Key JDBC driver class and press Enter");
    databaseField.setToolTipText("Key JDBC database URL and press Enter");
    usernameField.setToolTipText("Key database username and press Enter");
    passwordField.setToolTipText("Key database password and press Enter");

    topPanel.setLayout(new GridLayout(4,2));
    topPanel.add(driverClassLabel);
    topPanel.add(driverClassField);
    topPanel.add(databaseLabel);
    topPanel.add(databaseField);
    topPanel.add(usernameLabel);
    topPanel.add(usernameField);
    topPanel.add(passwordLabel);
    topPanel.add(passwordField);

    getContentPane().add(topPanel, BorderLayout.CENTER);
    getRootPane().setDefaultButton(ok);

    pack();
    setVisible(false);
  }

  JButton createButton(String label) {
    JButton button = new JButton(label);
    button.setPreferredSize(new Dimension(80,20));
    button.addActionListener(this);
    return button;
  }

  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source == ok) {

      try {
        window.connectToDatabase(driverClassField.getText(),
                                 databaseField.getText(),
                                 usernameField.getText(),
                                 new String(passwordField.getPassword()));
      } catch(ClassNotFoundException cnfe) {
        System.err.println(cnfe);                    // Driver not found
      } catch(SQLException sqle) {
        System.err.println(sqle);                    // error connection to database
      }

      setVisible(false);
    }
    else if (source == cancel) {
      setVisible(false);
    }
  }

  InteractiveSQL window;
  JButton ok;
  JButton cancel;

  JPanel topPanel = new JPanel();              // Panel for command string and database name
  JLabel driverClassLabel = new JLabel("JDBC Driver class:");
  JLabel databaseLabel = new JLabel("JDBC database URL:");
  JLabel usernameLabel = new JLabel("Username:");
  JLabel passwordLabel = new JLabel("Password:");
  JTextField driverClassField = new JTextField();
  JTextField databaseField = new JTextField();      // Input area for database name
  JTextField usernameField = new JTextField();
  JPasswordField passwordField = new JPasswordField();
}