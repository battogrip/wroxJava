import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class RandomFileRead {
  public static void main(String[] args) {
    File aFile = new File("C:/Beg Java Stuff/primes.bin");
    FileInputStream inFile = null;
    FileOutputStream outFile = null;
   
    try {
      inFile = new FileInputStream(aFile); 

    } catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    FileChannel inChannel = inFile.getChannel();
    
    final int PRIMESREQUIRED = 10;
    ByteBuffer buf = ByteBuffer.allocate(8*PRIMESREQUIRED);  

    long[] primes = new long[PRIMESREQUIRED];
    int index = 0;                           // Position for a prime in the file

    try {
      // Count of primes in the file
      final int PRIMECOUNT = (int)inChannel.size()/8;  

      // Read the number of random primes required
      for(int i = 0 ; i<PRIMESREQUIRED ; i++) {
        index = 8*(int)(PRIMECOUNT*Math.random());  
        inChannel.read(buf, index);              // Read the value 
        buf.flip();
        primes[i] = buf.getLong();               // Save it in the array
        buf.clear();
      }

      // Output the selection of random primes 5 to a line in field width of 12
      int count = 0;                             // Count of primes written                    
      for(long prime : primes) {
        System.out.printf("%12d", prime);
        if(++count%5 == 0) {
          System.out.println();
        }
      }
      inFile.close();                            // Close the file and the channel
 
    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }
}