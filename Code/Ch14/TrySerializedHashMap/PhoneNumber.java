import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Serializable;

class PhoneNumber implements Serializable {
  public PhoneNumber(String areacode, String number) {
    this.areacode = areacode;
    this.number = number;
 }

  public String toString() {
    return areacode + " " + number;  
  }

  // Read a phone number from the keyboard
  public static PhoneNumber readNumber() {
    String area = null;                               // Stores the area code
    String localcode = null;                          // Stores the local code
    try {
      
      System.out.print("Enter area code: ");
      area = keyboard.readLine().trim();   
       System.out.print("Enter local code: ");
      localcode = keyboard.readLine().trim(); 
      System.out.print("Enter the number: ");
      localcode += " "+keyboard.readLine().trim(); 
    } catch(IOException e) {
      System.err.println("Error reading a phone number.");
      e.printStackTrace();
      System.exit(1);
    }
    return new PhoneNumber(area,localcode);
  }

  private static BufferedReader keyboard = new BufferedReader(
                                         new InputStreamReader(System.in));
  private String areacode;
  private String number;
}
