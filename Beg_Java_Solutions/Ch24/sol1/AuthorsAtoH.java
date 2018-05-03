import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorsAtoH {
  public static void main(String[] args) {
    // Load the driver
    try {
      // Load the driver class
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

      // Define the data source for the driver
      String sourceURL = "jdbc:odbc:technical_library";

      // Create a connection through the DriverManager 
      Connection databaseConnection = DriverManager.getConnection(sourceURL);

      Statement statement = databaseConnection.createStatement();

      ResultSet authorNames = statement.executeQuery("SELECT lastname, firstname FROM authors");

      // Output the resultset data
      while(authorNames.next()) {
        String lastname = authorNames.getString("lastname");
        if ((lastname.compareToIgnoreCase("A") >= 0) && (lastname.compareToIgnoreCase("I") < 0)) {
          System.out.println(lastname + " " + authorNames.getString("firstname"));
        }
      }
    } catch(ClassNotFoundException cnfe) {
        System.err.println(cnfe);
    } catch(SQLException sqle) {
        System.err.println(sqle);
    }
  }
}
