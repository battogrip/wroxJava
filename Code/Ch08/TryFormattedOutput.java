public class TryFormattedOutput {
  public static void main(String[] args) {


    // The minimum format specifier
    int a = 5, b = 15, c = 255;
    double x = 27.5, y = 33.75;
    System.out.printf("x = %f y = %g", x, y);
    System.out.printf(" a = %d b = %x c = %o", a, b);

  // You can comment out the block above and then
  // uncomment any one of the following blocks that you want to execute.
  // Don't allow two blocks to be uncommented together, otherwise the program won't compile.

/*
    // Using the argument index specifier
    int a = 5, b = 15, c = 255;
    double x = 27.5, y = 33.75;
    System.out.printf("x = %2$f y = %1$g", x, y);
    System.out.printf(" a = %3$d b = %1$x c = %2$o", a, b, c);
    System.out.printf("%n a = %3$d b = %<x c = %<o", a, b, c);
*/

/*
    // Specifying the field width and justification flag
    int a = 5, b = 15, c = 255;
    double x = 27.5, y = 33.75;
    System.out.printf("x = %15f y = %8g", x, y);
    System.out.printf(" a = %1$5d b = %2$5x c = %3$2o", a, b, c);
    System.out.printf("%na = %1$-5d b = %2$-5x c = %3$-5o", a, b, c);
*/

/*
    // Specifying the precision
    double x = 27.5, y = 33.75;
    System.out.printf("x = %15.2f y = %14.3g", x, y);
*/

/*
    // Displaying integers as characters
    int count = 0;
    for(int ch = 'a' ; ch<= 'z' ; ch++) {
      System.out.printf("     %1$4c%1$4x", ch);
      if(++count%6 == 0) {
        System.out.print("\n");
      }
    }
*/

/*
    // Outputting a string
    String str = "The quick brown fox.";
    System.out.printf("%nThe string is:%n%s%n%1$25s", str);
*/

/*
    // Using the static format() method in the String class
    double x = 27.5, y = 33.75;
    String outString = String.format("x = %15.2f y = %14.3g", x, y);
    System.out.print(outString);
 */

/*
    // Using a Formatter object directly
    StringBuffer buf = new StringBuffer();
    java.util.Formatter formatter = new java.util.Formatter(buf);
    double x = 27.5, y = 33.75;
    formatter.format("x = %15.2f y = %14.3g", x, y);
    System.out.print(buf);
*/
  }
}