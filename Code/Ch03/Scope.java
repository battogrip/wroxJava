public class Scope {
  public static void main(String[] args) {
    int outer = 1;                                // Exists throughout the method

    {
      // You cannot refer to a variable before its declaration
      // System.out.println("inner = " + inner);  // Uncomment this for an error

      int inner = 2;
      System.out.println("inner = " + inner);     // Now it is OK
      System.out.println("outer = " + outer);     // and outer is still here

      // All variable defined in the enclosing outer block still exist,
      // so you cannot redefine them here
      // int outer = 5;                           // Uncomment this for an error
    }

    // Any variables declared in the previous inner block no longer exist
    // so you cannot refer to them
    // System.out.println("inner = " + inner);    // Uncomment this for an error

    // The previous variable, inner, does not exist so you can define a new one
    int inner = 3;
    System.out.println("inner = " + inner);       // ... and output its value
    System.out.println("outer = " + outer);       // outer is still around
  }
}
