// Chapter 11 Exercise 2

// Output the prime specified by a command line argument, or all the primes
// if there is no command line argument.
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadPrimes {
  public static void main(String[] args) {
    int requiredPrime = 0;        // Sequence number of prime required - 1 for the first, 2 for the second etc.
    System.out.println("Arg array length: "+ args.length);   
    if(args.length>= 1) {
      try {
        requiredPrime = Integer.parseInt(args[0]);
        System.out.println("Required prime: "+ requiredPrime);   
      } catch(NumberFormatException nfe) {
          System.out.println("Command line argument " +args[0] 
                             + "not a valid integer. Continuing to list file contents.");
      }
    }
    File aFile = new File("C:/Beg Java Stuff/primes.bin");
    FileInputStream inFile = null;
   
    try  {
      inFile = new FileInputStream(aFile); 
    }
    catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    FileChannel inChannel = inFile.getChannel(); 
    ByteBuffer buf = null;   

    // If a positive index position (starting from 1) for the position of the prime 
    // that is required, go to that position in the file and read it.
    try {
      if(requiredPrime>0) {
        long fileSize = inChannel.size();
        if(8*requiredPrime > fileSize) {
          System.out.println("Prime "+ requiredPrime +" is not in the file. Listing the "+(fileSize/8)
                                + " primes in the file.");
          requiredPrime = 0;
        }
        else {
            inChannel.position(8*(requiredPrime-1));
            buf = ByteBuffer.allocate(8);
            inChannel.read(buf); 
            long prime = ((ByteBuffer)(buf.flip())).asLongBuffer().get();
            System.out.println("Prime "+requiredPrime+" is "+prime);        
            System.exit(0);
        }
      }
    } catch(IllegalArgumentException e)  {
        e.printStackTrace();
        System.exit(1);
    } catch(IOException e) {
        e.printStackTrace();
        System.exit(1);
    }

    // If no specific prime is required, output the lot from the file
    if(requiredPrime == 0) {   
      final int PRIMECOUNT = 6;
      buf = ByteBuffer.allocate(8*PRIMECOUNT);  
      long[] primes = new long[PRIMECOUNT];
      try {
        int primesRead = 0;                              // Number of primes read into the buffer
        while(inChannel.read(buf) != -1) {
         buf.flip();                                     // Flip the buffer ready to get the data
         primesRead = buf.asLongBuffer().limit();        // Get the number of primes in the buffer 
         buf.asLongBuffer().get(primes, 0 , primesRead); // Extract that many primes into the array

         System.out.println();
         for(int i = 0 ; i<primesRead ; i++) {
           System.out.printf(" %10d", primes[i]);
         }
         
          buf.clear();                                   // Clear the buffer for the next read
        }
        System.out.println("\nEOF reached.");
        inFile.close();                                  // Close the file and the channel
      } catch(IOException e) {
        e.printStackTrace(System.err);
        System.exit(1);
      }
    }
  }
}
