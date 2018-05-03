// Version to catch buffer underflow exception
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;                      // Added to previous example code
import java.nio.BufferUnderflowException;        // Added to previous example code
import java.nio.channels.FileChannel;

public class ReadPrimes2 {
  public static void main(String[] args) {
    File aFile = new File("C:/Beg Java Stuff/primes.bin");
    FileInputStream inFile = null;
   
    try {
      inFile = new FileInputStream(aFile); 

    } catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  
    FileChannel inChannel = inFile.getChannel();    
    final int PRIMECOUNT = 6;
    ByteBuffer buf = ByteBuffer.allocate(8*PRIMECOUNT);  
    long[] primes = new long[PRIMECOUNT];
    try {
      int primesRead = 0;
      while(inChannel.read(buf) != -1) {
        try {
          ((ByteBuffer)(buf.flip())).asLongBuffer().get(primes);
          primesRead = primes.length;

        } catch(BufferUnderflowException e) {
          LongBuffer longBuf = buf.asLongBuffer();
          primesRead = longBuf.remaining();
          longBuf.get(primes,0, primesRead);
        }

        // List the primes read on the same line
        System.out.println();
        for(int i = 0 ; i<primesRead ; i++) {
           System.out.printf("%10d", primes[i]);
        }         
         
        buf.clear();                    // Clear the buffer for the next read
      }
      System.out.println("\nEOF reached.");
      inFile.close();                  // Close the file and the channel
 
    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
      System.exit(0);
  }
}
