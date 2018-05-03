import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;                    
import javax.swing.JPanel;                    
import javax.swing.JButton;                    
import javax.swing.JTextArea;                    
import javax.swing.JScrollPane;
                    
import java.sql.SQLException;

public class ErrorWindow extends JFrame implements ActionListener {
  public ErrorWindow(JFrame window) {
    super("Error messages");
    
    this.window = window;

    JPanel buttonPane = new JPanel();
    buttonPane.add(dismiss = createButton("Dismiss"));
    buttonPane.add(clear = createButton("Clear"));
    getContentPane().add(buttonPane, BorderLayout.SOUTH);

    errorMessages = new JTextArea(30, 40);
    errorMessages.setEditable(false);
    errorMessages.setLineWrap(true);
    errorMessages.setWrapStyleWord(true);
    scroll = new JScrollPane(errorMessages);

    getContentPane().add(scroll, BorderLayout.CENTER);
    getRootPane().setDefaultButton(dismiss);
    Rectangle bounds = window.getBounds();
    setLocation(bounds.x + bounds.width/3, bounds.y + bounds.height/3);
    pack();
  }

  public void addSQLException(SQLException error) {
    String message = "";
    do {
      addMessage("Exception occurred:\nMessage: " + error.getMessage() + "\n" +
                 "SQL state: " + error.getSQLState() + "\n" +
                 "Vendor code: " + error.getErrorCode());
    } while ((error = error.getNextException()) != null);
  }

  public void addMessage(String message) {
    errorMessages.append(message);
    errorMessages.append("\n----------------\n");

    if(!isVisible()) {   
      setVisible(true);
    }
  }

  JButton createButton(String label) {
    JButton button = new JButton(label);
    button.setPreferredSize(new Dimension(80,20));
    button.addActionListener(this);
    return button;
  }

  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if(source == dismiss) {
      setVisible(false);
    } else if (source == clear) {
      errorMessages.setText("");
    }
  }

  JButton dismiss;
  JButton clear;
  JTextArea errorMessages;
  JScrollPane scroll;
  JFrame window;
}
