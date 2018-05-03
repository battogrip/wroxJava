// Chapter 7 Exercise 2

public class TestDivide {
  public static int divide(int[] array, int index) throws ZeroDivideException,
							ClassifyIndexException {
    // Because the original statement in the try block has three array references that
    // could throw an exception, we need a device to determine which one does.
    // We will store each index in a variable that we can access if an exception is thrown:
    int indexUsed = 0;                 // Records the index value for each element reference.
    int numerator;                     // Store for numerator in division.
    int denominator;                   // Store for denominator in division.

    try {

      System.out.println("First try block in divide() entered");
      numerator = array[indexUsed = index];
      denominator = array[indexUsed = index+1];
      array[indexUsed = index+2] = numerator/denominator;
      System.out.println("Code at end of first try block in divide()");
    } catch(ArithmeticException e) {
      System.out.println("Arithmetic exception caught in divide()");
      throw new ZeroDivideException(index+1);		// Throw new exception.
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Out of bounds index exception caught in divide()");
      // indexUsed will contain the value of the index when the exception was thrown
      throw new ClassifyIndexException(indexUsed);	// Throw new exception.
    }

    System.out.println("Executing code after try block in divide()");
    return array[index+2];		
  }

  public static void main(String args[]) {
    int[] x = {10, 5, 0};		// Array of three integers.
    int index = 0;			// Index used to access array.
    int choice = 0;			// Selects the type of exception.

    for(choice = 0; choice<3 ; choice++)
    // This block only throws an exception if method divide() does:
    try {
      switch(choice) {
	      case 0:		// Set conditions for a zero divide exception.
	        index = 0;
	        x[1] = 0;
	        break;
	      case 1:		// Set conditions for an index to be too high.
	        index = 1;
	        break;
	      case 2:		// Set the conditions for a negative array index.
	        index = -1;
	        break;
      }
      System.out.println("\nFirst try block in main()entered");
      System.out.println("result = "+divide(x,index));
    } catch(ZeroDivideException e) {
      int indexUsed = e.getIndex();		// Get the index for the error.
      if(indexUsed>0)	{			// Verify it is valid and fix up the array...
        x[indexUsed] = 1;			//  ...set the divisor to 1...
        x[indexUsed+1] = x[indexUsed-1];	//  ...and set the result.
      }
    } catch(ClassifyIndexException e) {
      System.out.println(e);	// Print the message of the ClassifyIndexExc..
    } catch(ArithmeticException e) {
      System.out.println("Arithmetic exception caught in main()");
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Index out of bounds exception caught in main()");
    }

    System.out.println("Outside first try block in main()");
  }
}

