// Chapter 7 Exercise 2

// ClassifyIndexException class

public class ClassifyIndexException extends Throwable {
  private int index = -1;    		// Index of array element causing error.
  private String exception;  		// String identifying exception.

  // Default Constructor:
  public ClassifyIndexException() {    
    exception = "Index value is not known.";  	// Set defaults for exception string. 
  }

  // Standard constructor:
  public ClassifyIndexException(String s) {
    super(s);                                         // Call the base constructor.
    exception = "Index value is not known.";          // Set defaults for exception string. 
  }

  public ClassifyIndexException(int index) {
    super("Index is not within scope of array.\n");   // Call the base constructor.
    this.index = index;                               // Set the index value.
    if(index < 0) {       				// Check for negative index.
      exception = "Index to array is negative. Index = " + index;
    } else {                                          // Index value must be too high:
      exception = "Index to array is beyond last array element. Index = "+index;
    }
  }

  // Get the array index value for the error:
  public int getIndex() {
    return index;                                     // Return the index value.
  }

  // Override inherited method for a more informative message string:
  public String toString() {
    return getMessage() + exception;
  }
}

