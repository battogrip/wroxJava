// Chapter 11 Exercise 1

// Reads the file containing proverbs separated by '*'
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.BufferUnderflowException;
import java.nio.channels.FileChannel;

public class ReadProverbs {

  public static void main(String[] args) {
    String dirName = "c:/Beg Java Stuff";            // Directory for the output file
    String fileName = "Proverbs.txt";                // Name of the output file

    File aFile = new File(dirName, fileName);

    FileInputStream inputFile = null;
    try {
      inputFile = new FileInputStream(aFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    } 

    FileChannel inChannel = inputFile.getChannel();
    buf = ByteBuffer.allocate(100);
    while(true) {
      try{
        if(inChannel.read(buf) < 0)                  // If we read EOF
          break;                                     // break out of the loop
      } catch(IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
      // Buffer-full read - so process it      
      buf.flip();                                    // Flip buffer ready for extracting proverb(s)
      try {
        getProverbs();
      }catch(BufferUnderflowException bue) {         // We get to here when we reach the end of the buffer
          buf.compact();                             // so compact the buffer ready to read some more
      }
    }

    // Buffer partially filled - last block of one or more proverbs
    // The last proverb has no separator at the end so we must provide for
    // displaying it when file reading is finished.
    buf.flip();
    try {
        getProverbs();
      }catch(BufferUnderflowException bue) {         // We get to here when we reach the end of the buffer
        if(proverb.length()>0) {
          System.out.println(proverb.toString());
        }
      }
  }             

  static void getProverbs() throws BufferUnderflowException {
    char ch = 0;                                     // Stores a character from the buffer                                             
    while(true) { 
          if((ch=buf.getChar()) != separator) {      // If the character from the buffer is not a separator
            proverb.append(ch);                      // append it to the proverb string
          } else {                                   // Otherwise we have a complete proverb
            System.out.println(proverb.toString());  // so display it
            proverb.delete(0,proverb.length());      // and remove it from the string buffer
          }
        }

  }
  private static ByteBuffer buf;                                  // File input buffer             
  private static StringBuffer proverb = new StringBuffer();       // Will hold a proverb                                
  private static char separator = '*';                            // Separator between proverbs in the file
}