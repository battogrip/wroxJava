// Chapter 24 Exercise 2
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksAndAuthors {
  public static void main(String[] args) {
    // Load the driver
    try {
      // Load the driver class
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

      // Define the data source for the driver
      String sourceURL = "jdbc:odbc:technical_library";

      // Create a connection through the DriverManager 
      Connection databaseConnection = DriverManager.getConnection(sourceURL);

      Statement bookStatement = databaseConnection.createStatement();
      Statement authStatement = databaseConnection.createStatement();

      ResultSet books = bookStatement.executeQuery("SELECT isbn, title FROM books");

      // Output the resultset data
      while(books.next()) {
        String isbn = books.getString("isbn");
        System.out.println("ISBN:       " + isbn);
        System.out.println("Book title: " + books.getString("title"));

        String query = "SELECT firstname, lastname FROM authors, auth_books" +
                       " WHERE authors.authid = auth_books.authid AND auth_books.isbn = '" +
                       isbn + "'";

        ResultSet authors =
          authStatement.executeQuery(query);

        System.out.println("Authors:");

        while(authors.next()) {
          System.out.println("   " + authors.getString("lastname") + " " +                                                                authors.getString("firstname"));
        }

      System.out.println();
      System.out.println();
       
      }
    } catch(ClassNotFoundException cnfe) {
      System.err.println(cnfe);
    } catch(SQLException sqle) {
      System.err.println(sqle);
    }
  }
}
