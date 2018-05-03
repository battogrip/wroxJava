import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadPrimesMixedData {
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
      ByteBuffer lengthBuf = ByteBuffer.allocate(8);
      int strLength = 0;            // Stores the string length 
      ByteBuffer buf = null;        // Stores a reference to the second byte buffer 
      byte[] strChars = null;       // A reference to an array to hold the string
 
      while(true) {
        if(inChannel.read(lengthBuf) == -1)  // Read the string length, 
          break;                             // if its EOF exit the loop

        lengthBuf.flip();

        // Extract the length and convert to int
        strLength = (int)lengthBuf.getDouble();

        // Buffer for the string & the prime
        buf = ByteBuffer.allocate(2*strLength+8);

        if(inChannel.read(buf) == -1) {   // Read the string & binary prime value
          assert false;                   // Should not get here!
          break;                          // Exit loop on EOF
        }
        buf.flip();
 
        // First version of code to extract the string       
        strChars = new byte[2*strLength]; // Create the array for the string
        buf.get(strChars);                // Extract string
          
        System.out.printf("String length: %3s  String: %-12s  Binary Value: %3d%n",
                   strLength, ByteBuffer.wrap(strChars).asCharBuffer(),buf.getLong());

/*
        // First alternative for extracting the string 
        char[] str = new char[strLength]; // Array to hold the string
        buf.asCharBuffer().get(str);
        System.out.printf("String length: %3s  String: %-12s  Binary Value: %3d%n",
              strLength, new String(str), ((ByteBuffer)(buf.position(2*strLength))).getLong());
*/
        
/*
        // Second alternative for extracting the string 
        char[] str = new char[strLength]; // Array to hold the string
        for(int i = 0 ; i<str.length ; i++) {
          str[i] = buf.getChar();
        }
        System.out.printf("String length: %3s  String: %-12s  Binary Value: %3d%n",
                                     strLength, new String(str), buf.getLong());
*/        

        lengthBuf.clear();                // Clear the buffer for the next read
      }

      System.out.println("\nEOF reached.");
      inFile.close();                     // Close the file and the channel

    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    System.exit(0);
  }
}
