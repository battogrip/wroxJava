import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadPrimesMixedData2 {
  public static void main(String[] args) {
    File aFile = new File("C:/Beg Java Stuff/primes.txt");
    FileInputStream inFile = null;
    try {
      inFile = new FileInputStream(aFile); 

    } catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    FileChannel inChannel = inFile.getChannel();    
    try {
      ByteBuffer buf = ByteBuffer.allocateDirect(1024);
      buf.position(buf.limit());       // Set the position for the loop operation
      int strLength = 0;               // Stores the string length
      byte[] strChars = null;          // Array for string 

      while(true) {
        if(buf.remaining() < 8) {      // Verify enough bytes for string length
          if(inChannel.read(buf.compact()) == -1)  
            break;                                       
          buf.flip();
         }
         strLength = (int)buf.getDouble();
         
         // Verify enough bytes for complete string
         if(buf.remaining()<2*strLength) { 
           if(inChannel.read(buf.compact()) == -1)  
             break;                                       
           buf.flip();
        }
        strChars = new byte[2*strLength];
        buf.get(strChars);
        
        if(buf.remaining()<8) {        // Verify enough bytes for prime value
          if(inChannel.read(buf.compact()) == -1)  
            break;                                       
          buf.flip();
        }

        System.out.printf("String length: %3s  String: %-12s  Binary Value: %3d%n",
                   strLength, ByteBuffer.wrap(strChars).asCharBuffer(),buf.getLong());
      }
      System.out.println("\nEOF reached.");
      inFile.close();                   // Close the file and the channel
 
    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    System.exit(0);
  }
}