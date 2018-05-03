// Chapter 9 Exercise 2

// This extends the solution to exercise 1 to append a three digit number to the file name
// *** indicates modified or added code

import java.io.File;
import java.io.FileOutputStream;
 

import java.io.FileNotFoundException;

public class AvoidOverwritingFile {
  public static void main(String[] args) {
    String filepath = "C:/Beg Java Stuff/myFile.txt";

    if(args.length>0) {                            
      filepath = args[0];  
      }                      
      
    File aFile = new File(filepath);
    FileOutputStream outputFile = null;          // Stores the stream reference
    if (aFile.isFile()) {
      File newFile = aFile;                      // Start with the original file

      // We will append three digits to the file to make it unique
      do {
        String name = newFile.getName();         // Get the name of the file
        int period =  name.indexOf('.');         // Find the separator for the extension
        if(period == -1) {                         
          newFile = new File(newFile.getParent(), extendName(name));       // ***
        } else {
          newFile = new File(newFile.getParent(), 
                           extendName(name.substring(0, period)) 
                           + name.substring(period));
        }
      } while(!aFile.renameTo(newFile));         // Stop when renaming works
    } 

    // Now we can create the new file
    try {
      // Create the stream opened to append data
      outputFile = new FileOutputStream(aFile);
      System.out.println(aFile.getName()+" output stream created");
    } catch (FileNotFoundException e) {
      e.printStackTrace(System.err);
    } 
    System.exit(0);
  }
  // *** new method to adding or increment three digits at the end of a name starting at "000"
  private static String extendName(String name) {
    StringBuffer newName = new StringBuffer(name);              // We will create the new name in this buffer
    String digits = newName.substring(newName.length()-3,newName.length());   // Get last three characters
    int number = 0;
    try {
      number = Integer.parseInt(digits);                                      // Parse the last 3 characters
      ++number;                                                               // We got an integer so increment it
      newName.delete(newName.length()-3,newName.length());
    } catch(NumberFormatException nfe) {                                      // Last 3 characters not an integer
      // This will be thrown if digits is not a parsable integer
      // but if we just do nothing here execution will continue
      // with number as its initial value, which is zero.
    }
    digits = String.valueOf(number);                // Get number as a string 
    assert digits.length() < 4;                     // Make sure we don't run over three digits!     
   
    // We can now create the new name by appending "000" and replacing some or all of these
    // by the digits in the String, digits.   
    return newName.append("000").replace(newName.length()-digits.length(),newName.length(), digits).toString();    
  }
}
