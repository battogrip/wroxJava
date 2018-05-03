// Chapter 10 Exercise 1

// *** indicates modified or added code

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;

public class WriteProverbs {
  public static void main(String[] args) {
    String dirName = "c:/Beg Java Stuff";   // Directory for the output file
    String fileName = "Proverbs.txt";       // Name of the output file
    String[] sayings = {
      "Indecision maximizes flexibility.",
      "Only the mediocre are always at their best.",
      "A little knowledge is a dangerous thing.",
      "Many a mickle makes a muckle.",
      "Who begins too much achieves little.",
      "Who knows most says least.",
      "A wise man sits on the hole in his carpet."
    };
    File aFile = new File(dirName, fileName);

    FileOutputStream outputFile = null;
    try {
      outputFile = new FileOutputStream(aFile, true);
    } catch (FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    } 
    FileChannel outChannel = outputFile.getChannel();

    // Create a buffer to accommodate the longest string + a separator character
    int maxLength = 0;
    for (String saying : sayings) { 
      if (maxLength < saying.length()) 
        maxLength = saying.length ();
    }

    ByteBuffer buf = ByteBuffer.allocate(2 * (maxLength + 1));                // ***
    CharBuffer charBuf = buf.asCharBuffer();                                  // ***

    char separator = '*';                                                     // ***

    // Write the file
    boolean firstSaying = true;                                               // ***
    try {
      for (String saying : sayings) {
        if(firstSaying) {                                                     // ***
          firstSaying = false;                                                // ***
        } else {                                                              // ***
          charBuf.put(separator);                                             // ***
        }
        charBuf.put(saying);                                                  // ***
        buf.position(2 * charBuf.position()).flip();                          // ***
        outChannel.write(buf);   // Write the buffer to the file channel
        charBuf.clear();
        buf.clear();
      }
      outputFile.close();        // Close the output stream & the channel
      System.out.println("Proverbs written to file.");
 

    } catch (IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    System.exit(0);
  }
}

