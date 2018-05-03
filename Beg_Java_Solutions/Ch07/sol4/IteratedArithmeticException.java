//Chapter 7 Exercise 4

// IteratedArithmeticException class

public class IteratedArithmeticException extends Throwable {
  private int iteration = 0;	                    // Iteration number at which exception occurred.

  // Default Constructor:
  public IteratedArithmeticException(){}

  // Standard constructor:
  public IteratedArithmeticException(String s) {
    super(s);                                    // Call the base constructor.
  }

  public IteratedArithmeticException(int iteration) {
    super();                                     // Call the base constructor.
    this.iteration = iteration;                  // Set the iteration number.
  }

  // Get the array index value for the error:
  public int getIteration() {
    return iteration;                            // Return the iteration number.
  }
}

