// Chapter 7 Exercise 2

// ZeroDivideException class

public class ZeroDivideException extends Throwable {
  private int index = -1;	                       // Index of array element causing error.

  // Default Constructor:
  public ZeroDivideException(){}

  // Standard constructor:
  public ZeroDivideException(String s) {
    super(s);                                    // Call the base constructor.
  }

  public ZeroDivideException(int index) {
    super("/ by zero");                          // Call the base constructor.
    this.index = index;                          // Set the index value.
  }

  // Get the array index value for the error:
  public int getIndex() {
    return index;                                // Return the index value.
  }
}

