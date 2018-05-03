Solution 1. This simple JDBC program performs a query to obtain the lastname and firstname of all authors; in processing the ResultSet it compares each author's lastname to check that it is both (1) greater than or equal to "A", and (2) less than "I" before printing it.

Solution 2. This solution contains two nested loops. A query is performed:
  SELECT isbn, title FROM books
to obtain the titles and ISBN numbers of all books. The ResultSet is looped through, and for each row a further query is performed:
  SELECT firstname, lastname FROM authors, auth_books WHERE authors.authid =
    auth_books.authid AND auth_books.isbn = '<whatever the isbn was>'
Each row of the second ResultSet corresponds to an author of that book.

Solution 3. A new method, connectToDatabase(), is added to InteractiveSQL to connect
to the specified database using the given driver class, username and password. 
Four JTextField components, and corresponding JLabels, are added to the top panel
to receive the requisite information; each has the InteractiveSQL object added
 as an ActionListener, and when Return is pressed the program attempts to connect
  to the currently-specified database.

JTextField and JLabel components are added at the top of the user interface,
to provide for the necessary input. 
A JButton component is also added, and when this is clicked the executeSQL() method
calls the createQuery() method, which assembles the SQL command from the contents of
the individual JTextField components.