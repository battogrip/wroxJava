// Chapter 4 Exercise 6
/*
 This program removes line numbers inserted by the solution to Chapter 4 Exercise 5.
 It will leave files that do not have line numbers unchanged.
 
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RemoveLineNumbers {
  public static void main(String[] args) {
    // Get the file name and path from the command line
    if(args.length == 0 ) {
      System.out.println("No file specified. Usage is:"
                        +"\n\njava RemoveSpaces \"PATH_TO_FILE\""
                        +"\n\nwhere PATH_TO_FILE is an absolute path to the file whose you want to dump."
                        +"\nYou should use either forward slashes or double backslashes as separators in the path.");
      System.exit(1);
    }
    String filePath = args[0];                 // Get the file path
    File fileIn = new File(filePath);
    if(!fileIn.isFile()) {                     // Check that there is a file for the path
      System.out.println("File "+filePath+" does not exist.");
      System.exit(1);
    }
    FileInputStream inFile = null;
   
    // Create the file input stream
    try {
      inFile = new FileInputStream(fileIn); 
    } catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    FileChannel inChannel = inFile.getChannel();     // File channel for input
    ByteBuffer inBuffer = ByteBuffer.allocate(512);  // Buffer for 512 ASCII characters   
        
    // The regular expression to match a line that may or may not contain a line number
    Pattern line = Pattern.compile("(^\\d+ )?(.*\\r?\\n)",Pattern.MULTILINE); 
    
    // Create a new file for output
    File fileOut = createCopyFile(fileIn);
    FileOutputStream outFile = null;
    System.out.println("Copy file is: "+fileOut.getName());               
   
    // Create the output file stream
    try {
      outFile = new FileOutputStream(fileOut); 
    } catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }

    FileChannel outChannel = outFile.getChannel();         // Channel for output 
    ByteBuffer outBuffer = ByteBuffer.allocate(512);       // Buffer holds 512 ASCII characters
    
    Matcher lineMatch = null;                // Matcher for a line
    Matcher lineNumberMatch = null;          // Matcher for a line number
    String inStr = null;                     // Stores input the buffer data as Unicode
    String numberStr = null;                 // Stores a line number as a string
    int offset = 0;                          // Offset for part of a line at end of inStr
    byte[] asBytes = null;                   // Input buffer contents as byte array
    try {
      // Read the file a buffer at a time
      while(inChannel.read(inBuffer) != -1) {
        inBuffer.flip();                             // Flip the buffer ready to get the data
        asBytes = new byte[inBuffer.remaining()];
        inBuffer.get(asBytes);
        inStr = new String(asBytes);
        outBuffer.clear();

        // Match complete lines from the buffer data that is now in inStr
        lineMatch = line.matcher(inStr);
        while(lineMatch.find()) {                      // While we match a line with a line 
          outBuffer.put(lineMatch.group(2).getBytes());
          offset = lineMatch.end();                    // Record where unprocessed data starts
        }             
        // We are finished with the input buffer contents 
        inBuffer.clear();      // Clear the input buffer ready for next cycle                                

        // Put any leftover from instr (any part of a line) back in input buffer
        inBuffer.put(inStr.substring(offset).getBytes());

        // Write the contents of the output buffer to the file
        outBuffer.flip();
        outChannel.write(outBuffer);        
     }

     // Write any residue left in the input buffer to the output file
     // - without a line number
     // If we got to here, the residue has no end-of-line character, so we need 
     // to match a pattern for a line with a line number but no EOL
     if(inBuffer.flip().hasRemaining()) {
       outBuffer.clear();
       asBytes = new byte[inBuffer.remaining()];
       inBuffer.get(asBytes,0,asBytes.length);
       lineMatch = Pattern.compile("(^\\d+ )?(.*)").matcher(new String(asBytes)); 
       if(lineMatch.matches()) {
         outBuffer.put(lineMatch.group(2).getBytes());
       } else {
         outBuffer.put(inBuffer);
       }
 
       outBuffer.flip();
       outChannel.write(outBuffer);
      }
     System.out.println("\nEOF reached on input.");
     inFile.close();                                // Close the stream and the channel
     outFile.close();
    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }

  // Method to create a unique backup File object
  // This appends _copy1 or _copy2, etc. to the input file name.
  // The integer part is incremented until we get a name that does not already exist.
  // This method will not work if you try to create more than 2,147,483,647 copies of the same file.
  public static File createCopyFile(File aFile) {
     aFile = aFile.getAbsoluteFile();                 // Ensure we have an absolute path
     File parentDir = new File(aFile.getParent());    // Get the parent directory
     String name = aFile.getName();                   // Get the file name
     int period = name.indexOf('.');                  // Find the extension separator    
     if(period == -1) {                               // If there isn't one    
       period = name.length();                        // set it to the end of the string
      }

     // Create a File object that is unique by appending _copyn where n is an integer
     int copyNumber = 0;                              // Copy number
     String nameAdd = null;                           // String to be appended                
     File copy = null;
     do {
       nameAdd = "_copy"+Integer.toString(++copyNumber);   // String to be appended
       copy = new File(name.substring(0,period) + nameAdd 
                          + name.substring(period));
     } while(copy.exists());                        // If the name already exists, go again

     return copy;   
  }
}