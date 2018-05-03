import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class RandomReadWrite {
  public static void main(String[] args)
  {
    File aFile = new File("C:/Beg Java Stuff/primes_backup.bin");
    FileInputStream inFile = null;
    FileOutputStream outFile = null;
   
    try {
      inFile = new FileInputStream(aFile); 
      outFile = new FileOutputStream(aFile, true); 

    } catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    FileChannel inChannel = inFile.getChannel();
    FileChannel outChannel = outFile.getChannel();
    
    final int PRIMESREQUIRED = 10;
    ByteBuffer buf = ByteBuffer.allocate(8);  

    long[] primes = new long[PRIMESREQUIRED];
    int index = 0;                            // Position for a prime in the file
    final long REPLACEMENT = 99999L;          // Replacement for a selected prime   

    try {
      final int PRIMECOUNT = (int)inChannel.size()/8;
      System.out.println("Prime count = "+PRIMECOUNT);
      for(int i = 0 ; i<PRIMESREQUIRED ; i++) {
        index = 8*(int)(PRIMECOUNT*Math.random());  
        inChannel.read(buf, index);              // Read at a random position  
        buf.flip();                              // Flip the buffer
        primes[i] = buf.getLong();               // Extract the prime
        buf.flip();                              // Flip to ready for insertion
        buf.putLong(REPLACEMENT);                // Replacement into buffer
        buf.flip();                              // Flip ready to write
        outChannel.write(buf, index);            // Write the replacement to file
        buf.clear();                             // Reset ready for next read
      }
 
      int count = 0;                             // Count of primes written                    
      for(long prime : primes) {
        System.out.printf("%12d", prime);
        if(++count%5 == 0) {
          System.out.println();
        }
      }
      inFile.close();                   // Close the file and the channel
      outFile.close();

    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
      System.exit(0);
  }
}
