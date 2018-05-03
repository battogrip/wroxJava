import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel; 
import java.nio.channels.FileLock; 

public class LockingPrimesRead {
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
      FileLock inLock = null;

      // File reading loop
      while(true) {
        int tryLockCount = 0;

        // Loop to get a lock on the file region we want to read
        while(true) {
          inLock = inChannel.tryLock(inChannel.position(), 
                                     buf.remaining(), 
                                     true);
          if(inLock != null) {                   // If we have a lock
           System.out.println("\nAcquired file lock.");
           break;                               // exit the loop
          }

          if(++tryLockCount>=100) {              // If we've tried too often
            System.out.printf("Failed to acquire lock after %d tries. Terminating...",
                                               tryLockCount);
            System.exit(1);                      // end the program
          }
 
          // Wait 200 msec before the next try for a file lock 
          try {
                Thread.sleep(200);                    // Wait for 200 milliseconds
          } catch(InterruptedException e) {
              e.printStackTrace(System.err);
          }
        }

        // We have a lock so now read the file
        if(inChannel.read(buf) == -1) {
          break;
        }
        inLock.release();                        // Release lock as read is finished
        System.out.println("Released file lock.");
          
        LongBuffer longBuf = ((ByteBuffer)(buf.flip())).asLongBuffer();
        primesRead = longBuf.remaining();
        longBuf.get(primes,0, longBuf.remaining());
        for(int i = 0 ; i< primesRead ; i++) {
          if(i%6 == 0) {
            System.out.println();
          }
          System.out.printf("%12d", primes[i]);
        }
        buf.clear();                             // Clear the buffer for the next read
      }

      System.out.println("\nEOF reached.");
      inFile.close();                            // Close the file and the channel

    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
      System.exit(0);
  }
}
