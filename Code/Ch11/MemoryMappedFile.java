import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import static java.nio.channels.FileChannel.MapMode.READ_WRITE;

public class MemoryMappedFile {
  public static void main(String[] args) {
    File aFile = new File("C:/Beg Java Stuff/primes_backup.bin");
    RandomAccessFile ioFile = null;
    try {
      ioFile = new RandomAccessFile(aFile,"rw");
    } catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    FileChannel ioChannel = ioFile.getChannel();
    final int PRIMESREQUIRED = 10;
    long[] primes = new long[PRIMESREQUIRED];

    int index = 0;                               // Position for a prime in the file
    final long REPLACEMENT = 999999L;            // Replacement for a selected prime

    try {
      final int PRIMECOUNT = (int)ioChannel.size()/8;
      MappedByteBuffer buf = ioChannel.map(READ_WRITE, 0L, ioChannel.size()).load();
      ioChannel.close();                         // Close the channel
      for(int i = 0 ; i<PRIMESREQUIRED ; i++) {
        index = 8*(int)(PRIMECOUNT*Math.random());    
        primes[i] = buf.getLong(index);
        buf.putLong(index, REPLACEMENT);
      }
      int count = 0;                             // Count of primes written                    
      for(long prime : primes) {
        System.out.printf("%12d", prime);
        if(++count%5 == 0) {
          System.out.println();
        }
      }
      ioFile.close();                            // Close the file and the channel
    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
      System.exit(0);
  }
}
