import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.util.Formatter;

public class UsingAFormatter {
  public static void main(String[] args) {
    String[] phrases = {"Rome wasn't burned in a day.",
                        "It's a bold mouse that sits in the cat's ear.",
                        "An ounce of practice is worth a pound of instruction."
                       };
    String dirname = "C:/Beg Java Stuff";   // Directory name
    String filename = "Phrases.txt";        // File name

    File dir = new File(dirname);           // File object for directory

    // Now check out the directory
    if (!dir.exists()){                     // If directory does not exist
      if (!dir.mkdir()){                    // ...create it
        System.out.println("Cannot create directory: " + dirname);
        System.exit(1);
      } 
    } else if (!dir.isDirectory()) {
      System.err.println(dirname + " is not a directory");
      System.exit(1);
    } 

    // Create the filestream
    File aFile = new File(dir, filename);   // File object for the file path
    FileOutputStream outputFile = null;     // Place to store the stream reference
    try {
      outputFile = new FileOutputStream(aFile, true);
      System.out.println("File stream created successfully.");
    } catch (FileNotFoundException e) {
      e.printStackTrace(System.err);
    } 

    // Create the file output stream channel
    FileChannel outChannel = outputFile.getChannel();
    
    // Create byte buffer to hold data to be written
    ByteBuffer buf = ByteBuffer.allocate(1024);
    System.out.println("\nByte buffer:");
    System.out.printf("position = %2d  Limit = %4d  capacity = %4d%n",
                             buf.position(), buf.limit(), buf.capacity());

    // Create a view buffer
    CharBuffer charBuf = buf.asCharBuffer();
    System.out.println("Char view buffer:");
    System.out.printf("position = %2d  Limit = %4d  capacity = %4d%n",
                            charBuf.position(),charBuf.limit(),charBuf.capacity());
    Formatter formatter = new Formatter(charBuf);

    // Write to the view buffer using a formatter
    int number = 0;                    // Proverb number
    for(String phrase : phrases) {    
      formatter.format("%nProverb%3d: %s", ++number, phrase);
      System.out.println("\nView buffer after loading:");
      System.out.printf("position = %2d  Limit = %4d  capacity = %4d%n",
                             charBuf.position(), charBuf.limit(),charBuf.capacity());
      charBuf.flip();                        // Flip the view buffer
      System.out.println("View buffer after flip:");
      System.out.printf("position = %2d  Limit = %4d  length = %4d%n",
                      charBuf.position(),charBuf.limit(),charBuf.length());
      buf.limit(2*charBuf.length());        // Set byte buffer limit

      System.out.println("Byte buffer after limit update:");
      System.out.printf("position = %2d  Limit = %4d  length = %4d%n",
                              buf.position(),buf.limit(), buf.remaining());

      // Write the file
      try {
        outChannel.write(buf);                // Write the buffer to the file channel
        System.out.println("Buffer contents written to file.");
        buf.clear();
        charBuf.clear();
      } catch (IOException e) {
       e.printStackTrace(System.err);
       System.exit(1);
      }
    }
    try {
      outputFile.close();                   // Close the O/P stream & the channel
    } catch (IOException e) {
       e.printStackTrace(System.err);
    }
  }
}
