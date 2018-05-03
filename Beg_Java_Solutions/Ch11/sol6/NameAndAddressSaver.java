// Chapter 11 Exercise 6
/*
   This is a modification of the Ch11 ex 5 solution to make
   use of an index file for finding name & address records.
   This uses a different name and address file to avoid
   incompatibilities with the previous exercise solution.
   This exercise would be a lot easier after you learn the
   stuff in Chapter 12!

   The index file will contain entries in alphabetical order.
   Each entry will consist of a second name followed by a character representation 
   of the position in the file of the name and address. An '*' will separate the name
   from the position and '\n' will mark the end of an entry, like this:

        "secondName*indexValue\n"

   You may think maintaining an index file to the main name and address file
   does not help very much, if at all. This is true is this case, but where you have very 
   large files with long records an index can make random accesses to the
   file much faster.
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.StringTokenizer;

public class NameAndAddressSaver {
  // Directory and file to store names & addresses
  private String directory = "C:/Beg Java Stuff";
  private String filename = "IndexedNamesAndAddresses.bin";

  private String indexFilename = "IndexFile.bin";

  // Buffered keyboard input stream
  private BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

  // I/O streams and channels for file
  private FileInputStream inStream = null;
  private FileOutputStream outStream = null;
  private FileChannel inChannel = null;
  private FileChannel outChannel = null;
  private FileChannel indexInChannel = null;
  private FileChannel indexOutChannel = null;

  // Store for index to main name and address file
  private StringBuffer fileIndex = new StringBuffer(); 

  // Constructor - sets up streams files and channels
  public NameAndAddressSaver(){
    File dir = new File(directory);                            // Directory File object
    if(!dir.isDirectory()) {                                   // If there's no directory
      dir.mkdir();                                             // create one
    }
    File file = new File(dir, filename);                       // Name & address file
    File indexFile = new File(dir, indexFilename);             // Index to name & address file

    // Create input and output streams for file
    // and corresponding input and output channels
    FileInputStream indexInStream = null;
    FileOutputStream indexOutStream = null;
    try {
      // Since the file may not exist, we create the output stream first
      // as this will create an empty file if it's not there already
      outStream = new FileOutputStream(file, true);            // To write name & address records
      inStream = new FileInputStream(file);                    // To read name & address records
      indexOutStream = new FileOutputStream(indexFile, true);  // To write index entries
      indexInStream = new FileInputStream(indexFile);          // To read index entries

      // Get the channels for the streams
      inChannel = inStream.getChannel();
      outChannel = outStream.getChannel();
      indexInChannel = indexInStream.getChannel();
      indexOutChannel = indexOutStream.getChannel();
    } catch(FileNotFoundException fnfe) {
      fnfe.printStackTrace();
      System.exit(1);
    }

    // If the index file already contains entries
    // we must load them into the fileIndex StringBuffer. 
    if(indexFile.exists()&&indexFile.length()>0){
      ByteBuffer buf = ByteBuffer.allocate((int)indexFile.length());
      try{
        indexInChannel.read(buf);
        buf.flip();
        fileIndex.append(buf.asCharBuffer().toString());
      }catch(IOException e){
        e.printStackTrace();
        System.exit(1); 
      }
    }

  }

  public static void main(String[] args) {
    NameAndAddressSaver saver = new NameAndAddressSaver();
    if(args.length>0) {
      saver.find(args[0].trim());
    }
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
            indexInChannel.close();
            indexOutChannel.close();
          }catch(IOException e){
            e.printStackTrace();
          }
          System.exit(0);
          break;

        case 'F': case 'f':  // Find all entries for a second name
          find();
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
                      +"\n D or d to display the contents of the file."
                      +"\n F or f to search for a name.\n");
                            
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

  // Method to add a new namw and address to the file
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
    addIndexEntry(secondName);
 
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

  // Method to add an index entry for a name that will be added at the current
  // position in the name and address file
  private void addIndexEntry(String name) {
    // First create the index entry
    StringBuffer indexEntry = new StringBuffer(name);
    try { 
      indexEntry.append('*').append(Long.toString(outChannel.position())).append('\n');
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1); 
    }   

    // Now figure out where in fileIndex the new entry should go.
    // To maintain the index in ascending alphabetical order, we can
    // insert a new entry immediately preceding the first exist entry
    // that is greater than the new entry.
    boolean inserted = false;          // Flag indicating entry inserted or not
    if(fileIndex.length() == 0) {      // First check for empty index
      fileIndex.append(indexEntry);    // if so, we just append the new entry
      inserted = true;                 // and set the flag
    } else {                           // we must search for where to put it
      int start = 0;                   // Search start position
      int end = 0;
      String entry = indexEntry.toString();
      while((end = fileIndex.indexOf("\n",start)) >= 0 && !inserted) {
        if(entry.compareToIgnoreCase(fileIndex.substring(start,++end)) <= 0) {
          // We now know where to insert it
          fileIndex.insert(start,entry);
          inserted = true;
        } else {
            start = end;
        }
      }
      // It is possible that the new entry comes after all the existing entries
      if(!inserted)                        // If it was not inserted
        fileIndex.append(indexEntry);      // add it at the end
    }
    // We should write the revised index back to the file
    ByteBuffer buf = ByteBuffer.allocate(2*fileIndex.length());
    buf.asCharBuffer().put(CharBuffer.wrap(fileIndex.toString()));
    buf.limit(buf.capacity());             // We want to write the whole buffer
    try {
      indexOutChannel.truncate(0L).write(buf);  // Overwrite the existing file contents
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  // Method to read a name from the k/b and output entries for that name
  private void find() {
    try {
      System.out.println("Enter second name to be found:");
      String name = kb.readLine();
      find(name.trim());
    } catch(IOException e) {
      System.err.println("Error reading from keyboard\n");
      e.printStackTrace();
      System.exit(1); 
    }   
  }

  // Method to output entries for a given name
  private void find(String name) {
    int[] positions = findPositions(name);
    if(positions.length > 0) {
      for(int pos : positions) {
        try {
          ByteBuffer lengthBuf = ByteBuffer.allocate(4);  // Buffer for record length in characters

          inChannel.position(pos).read(lengthBuf);
          lengthBuf.flip();
          displayEntry(lengthBuf.getInt());
        }catch(IOException e){
          e.printStackTrace();
          System.exit(1);
        }
      }
    }
    else
      System.out.println(name + " not found.");
  }

  private int[] findPositions(String name) {
  int[] positions = new int[0]; 
  int start = 0;
  int position = 0;
  do {     
    position = -1;
    start = fileIndex.indexOf(name, start);
    if(start >= 0) {
      start+= name.length()+1;
      int end = fileIndex.indexOf("\n",start);
      position = Integer.parseInt(fileIndex.substring(start, end));
      start = ++end;
      int[] temp = new int[positions.length+1];
      for(int i = 0 ; i<positions.length ; i++)
        temp[i] = positions[i];
      temp[positions.length] = position;
      positions = temp;
    }
  }while(position >= 0);
    return positions;
  }
}