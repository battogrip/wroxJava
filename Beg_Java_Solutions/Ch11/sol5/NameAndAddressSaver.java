// Chapter 11 Exercise 5

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.util.StringTokenizer;

public class NameAndAddressSaver {
  // Directory and file to store names & addresses
  private String directory = "C:/Beg Java Stuff";
  private String filename = "NamesAndAddresses.bin";


  // Buffered keyboard input stream
  private BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

  // I/O streams and channels for file
  private FileInputStream inStream = null;
  private FileOutputStream outStream = null;
  private FileChannel inChannel = null;
  private FileChannel outChannel = null;

  // Constructor - sets up streams files and channels
  public NameAndAddressSaver(){
    File dir = new File(directory);                            // Directory File object
    if(!dir.isDirectory()) {                                   // If there's no directory
      dir.mkdir();                                             // create one
    }
    File file = new File(dir, filename);                       // Name & address file

    // Create input and output streams for file
    // and corresponding input and output channels
    try {
      // Since the file may not exist, we create the output stream first
      // as this will create an empty file if it's not there already
      outStream = new FileOutputStream(file, true);            // To write name & address records
      inStream = new FileInputStream(file);                    // To read name & address records

      // Get the channels for the streams
      inChannel = inStream.getChannel();
      outChannel = outStream.getChannel();
    } catch(FileNotFoundException fnfe) {
      fnfe.printStackTrace();
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    NameAndAddressSaver saver = new NameAndAddressSaver();
    saver.operate();
  }

  // Runs the name & address saver
  private void operate(){
    // Get operation selection from the keyboard
    while(true) {  
      switch(selectOperation()) {

        case 'N': case 'n':  // Read and add a new name and address
          addNameAndAddress();
          break;

        case 'D': case 'd':  // Display the contents of the file
         
          displayNamesAndAddresses();
          break;

        case 'Q': case 'q':  // Quit the program, but first close all channels
          try {
            inChannel.close();
            outChannel.close();
          }catch(IOException e){
            e.printStackTrace();
          }
          System.exit(0);
          break;

        default:
          System.out.println("Invalid Input. Try again.");
          break;
      }
    }
  }

  // Method to read a character entered on the keyboard
  private char selectOperation() {
    System.out.println("\nEnter a letter to select an operation:"
                      +"\n Q or q to quit the program."
                      +"\n N or n to enter a new name and address."
                      +"\n D or d to display the contents of the file.");
                            
    String s = null;
    try {
      s = kb.readLine();
    } catch (IOException e) {
        e.printStackTrace(System.err);
        System.exit(1);
    }
    
    return s.trim().charAt(0);  // Return the first non-blank letter
  }

  // Method to output the contents of the name and address file to the command line
  private void displayNamesAndAddresses() {
    ByteBuffer lengthBuf = ByteBuffer.allocate(4);  // Buffer for record length in characters
    ByteBuffer dataBuf = null;                      // Reference to a buffer to hold a record

    try {    
      inChannel.position(0L);                       // Go to the beginning
      while(inChannel.read(lengthBuf) != -1) {      // Loop until EOF                                 // Loop until EOF found
        lengthBuf.flip();                           // Ready to get length
        displayEntry(lengthBuf.getInt());
        lengthBuf.clear();                          // Clear length buffer for next iteration
      }
    }catch(IOException e) {
      e.printStackTrace();
      System.exit(1); 
    }
    return;    
  }

  // Method to display the entry at the current position in the file
  private void displayEntry(int entryLength) throws IOException {
      ByteBuffer  dataBuf = ByteBuffer.allocate(2*entryLength);  // Create record buffer of appropriate size
      inChannel.read(dataBuf);                    // Read the next record
      dataBuf.flip();                             // Flip to access buffer contents

      // Since the lines in the name and address are separated by '\n'
      // we can use a tokenizer to extract each line in turn
      // We just need to get the buffer contents in the form of a string
      // and pass that to the tokenizer constructor
      StringTokenizer st = new StringTokenizer(dataBuf.asCharBuffer().toString(), "\n");

      System.out.println();                      // Blank line as separator
      while(st.hasMoreTokens())                  // While there are more lines
        System.out.println(st.nextToken());      // Output the next line
      return;    
  }

  // Method to add a new name and address to the file
  private void addNameAndAddress() {
    StringBuffer sb = new StringBuffer();    // Will hold the name & address
    String secondName = null;
    try {
      // The name will be stored in sb as a single line:
      //               "firstname secondname\n"
      System.out.println("Enter first name:");
      sb.append(kb.readLine().trim()).append(' ');    
      System.out.println("Enter second name:");
      secondName = kb.readLine().trim();
      sb.append(secondName).append('\n');
      
      // We will add each address line to sb terminated by '\n'
      System.out.println("Enter address one line at a time. Enter ! to end:");
      String s = null; 
      while((s = kb.readLine().trim()).charAt(0) != '!')
       sb.append(s).append('\n');
    } catch(IOException e) {
      System.err.println("Error reading from keyboard\n");
      e.printStackTrace();
      System.exit(1); 
    }   
 
    // Now we can write the name and address to the file
    // We will write the length(character count) of the name and address first,
    // then the name and address itself
    ByteBuffer buf = ByteBuffer.allocate(2*sb.length()+4);
    buf.putInt(sb.length());
    char[] nameAndAddress = sb.toString().toCharArray();
    for(char ch : nameAndAddress) {
      buf.putChar(ch);
    }
    buf.flip();
    try {
      outChannel.write(buf);
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
 }