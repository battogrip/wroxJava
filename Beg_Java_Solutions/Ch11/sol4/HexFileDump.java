// Chapter 11 Exercise 4

// Dumps the content of a file as hexadecimal

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class HexFileDump {
  public static void main(String[] args) {
    if(args.length == 0 ) {
      System.out.println("No file specified. Usage is:"
                        +"\n\njava HexFileDump \"PATH_TO_FILE\""
                        +"\n\nwhere PATH_TO_FILE is an absolute or relative path to the file whose you want to dump."
                        +"\nYou should use either forward slashes or double backslashes as separators in the path.");
      System.exit(1);
    }
    String filePath = args[0];
    File file = new File(filePath);
    if(!file.isFile()) {
      System.out.println("File "+filePath+" does not exist.");
      System.exit(1);
    }
    FileInputStream inFile = null;
   
    try  {
      inFile = new FileInputStream(file); 
    }
    catch(FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    FileChannel inChannel = inFile.getChannel(); 
    ByteBuffer buffer = ByteBuffer.allocate(160);     // Buffer for 8 lines of output

    try {
      IntBuffer intBuffer = null;
      while(inChannel.read(buffer) != -1) {
        buffer.flip();                                     // Flip the buffer ready to get the data
        intBuffer = buffer.asIntBuffer();                  // Extract the bytes as int values

        // We can use the 'X' conversion in a printf() statement to output type int as hexadecimal digits
        // by using a flag '0' preceding the field width of 8, the output will be padded with zeros                     
        // Process all the data in the integer buffer
        for(int i = 0 ; i<intBuffer.limit() ; i++) {
          if(i%5 == 0) {
            System.out.println();
          }
          System.out.printf(" %08X", intBuffer.get());
        }
        buffer.clear();                                   // Clear the buffer for the next read
      }
      System.out.println("\nEOF reached.");
      nFile.close();                                  // Close the stream and the channel
    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }
}