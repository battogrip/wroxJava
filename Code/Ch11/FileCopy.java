import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCopy {
  public static void main(String[] args) {
    if(args.length==0) {
      System.out.println("No file to copy. Application usage is:\n"+
                         "java -classpath . FileCopy \"filepath\"" );
      System.exit(1);
    }
    File fromFile = new File(args[0]);

    if(!fromFile.exists()) {
      System.out.printf("File to copy, %s, does not exist.",
                                       fromFile.getAbsolutePath());
      System.exit(1);
    }
    
    File toFile = createBackupFile(fromFile);
    FileInputStream inFile = null;
    FileOutputStream outFile = null;
    try {
      inFile = new FileInputStream(fromFile); 
      outFile = new FileOutputStream(toFile);

    } catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      assert false;
    }

    FileChannel inChannel = inFile.getChannel();    
    FileChannel outChannel = outFile.getChannel(); 

    try {
      int bytesWritten = 0;
      long byteCount = inChannel.size();
      while(bytesWritten<byteCount) {
        bytesWritten += inChannel.transferTo(bytesWritten, 
                                             byteCount-bytesWritten,
                                             outChannel); 
      }
      
      System.out.printf("File copy complete. %d bytes copied to %s%n",
                                     byteCount, toFile.getAbsolutePath());
      inFile.close();
      outFile.close();

    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    System.exit(0); 
  }
 
  // Method to create a unique backup File object  
  public static File createBackupFile(File aFile) {
     aFile = aFile.getAbsoluteFile();          // Ensure we have an absolute path
     File parentDir = new File(aFile.getParent());    // Get the parent directory
     String name = aFile.getName();                   // Get the file name
     int period = name.indexOf('.');           // Find the extension separator
     if(period == -1) {                        // If there isn't one
       period = name.length();                 // set it to the end of the string
     }
     String nameAdd = "_backup";               // String to be appended

     // Create a File object that is unique
     File backup = aFile;
     while(backup.exists()) {                  // If the name already exists...
        name = backup.getName();               // Get the current name of the file
       backup = new File(parentDir, name.substring(0,period) // add _backup again
                       + nameAdd + name.substring(period));
        period += nameAdd.length();            // Increment separator index    
     }
     return backup;   
  }
}
