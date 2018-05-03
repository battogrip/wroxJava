import java.io.IOException;

public class TryBlockTest3 {
  public static void main(String[] args) {
    int[] x = {10, 5, 0};              // Array of three integers

    // This block only throws an exception if method divide() does
    try {
      System.out.println("First try block in main()entered");
      System.out.println("result = " + divide(x,0));  // No error
      x[1] = 0;                        // Will cause a divide by zero
      System.out.println("result = " + divide(x,0));  // Arithmetic error
      x[1] = 1;                        // Reset to prevent divide by zero
      System.out.println("result = " + divide(x,1));  // Index error
    } catch(ZeroDivideException e) {
      int index = e.getIndex();        // Get the index for the error
      if(index > 0) {                  // Verify it is valid and now fix the array
        x[index] = 1;                  // ...set the divisor to 1...
        x[index + 1] = x[index - 1];   // ...and set the result
        System.out.println("Zero divisor corrected to " + x[index]);
      }
    } catch(ArithmeticException e) {
      System.out.println("Arithmetic exception caught in main()");
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Index-out-of-bounds exception caught in main()");
    }
    System.out.println("Outside first try block in main()");
  }


  // Divide method
  public static int divide(int[] array, int index) throws ZeroDivideException {
    try {
      System.out.println("First try block in divide() entered");
      array[index + 2] = array[index]/array[index + 1];
      System.out.println("Code at end of first try block in divide()");
      return array[index + 2];

    } catch(ArithmeticException e) {
      System.out.println("Arithmetic exception caught in divide()");
      throw new ZeroDivideException(index + 1);    // Throw new exception
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Index-out-of-bounds index exception caught in divide()");
    }
    System.out.println("Executing code after try block in divide()");
    return array[index + 2];
  }
}
