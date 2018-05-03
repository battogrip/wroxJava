import java.util.Scanner;
import java.util.InputMismatchException;

public class TryScanner {
  public static void main(String[] args) {
    Scanner kbScan = new Scanner(System.in);     // Create the scanner
    int selectRead = 1;                          // Selects the read operation
    final int MAXTRIES = 3;                      // Maximum attempts at input
    int tries = 0;                               // Number of input attempts

    while(tries<MAXTRIES) {
      try {
        switch(selectRead) {
          case 1:                                
          System.out.print("Enter an integer: ");
          System.out.println("You entered: "+ kbScan.nextLong());
          ++selectRead;                          // Select next read operation
          tries = 0;                             // Reset count of tries

          case 2: 
          System.out.print("Enter a floating-point value: ");
          System.out.println("You entered: "+ kbScan.nextDouble());
          ++selectRead;                          // Select next read operation
          tries = 0;                             // Reset count of tries

          case 3: 
          System.out.print("Enter a boolean value(true or false): ");
          System.out.println("You entered: "+ kbScan.nextBoolean());
        }
        break;
      } catch(InputMismatchException e) {
          String input = kbScan.next();
          System.out.println("\""+ input +"\" is not valid input.");
          if(tries<MAXTRIES) {
            System.out.println("Try again.");
          } else {
            System.out.println(" Terminating program.");
            System.exit(1);
        }
      }
    }
  }
}