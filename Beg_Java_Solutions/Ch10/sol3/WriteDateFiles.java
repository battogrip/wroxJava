// Chapter 10 Exercise 3

// This is essentially the same as the solution to Exercise 1 except for a new class name
// and the additional code that deals with writing the binary file.

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

class WriteDateFiles {                                                          // ***** class name changed *****
  static String[] months = { "January "  , "February ", "March "   , "April "   , 
                             "May "      ,  "June "   , "July "    , "August "  , 
                             "September ", "October " , "November ", "December ",  };                


  public static void main(String args[]) {
    int[][] dates = { {12, 11, 1932}, {2, 29, 1944}, {3, 9,1944} , {4, 1, 1999},   // Dates to be written to file
                      {12,  5, 1939}, {3,  8, 1968}, {1, 1,2000} , {2, 2, 2002},
                      {10, 23, 1946}, {8,  3, 1986} };

    String[] dateStrings = new String[dates.length];                              // Stores date strings
 
    // Convert dates to string representations
    for(int i = 0 ; i<dates.length ; i++) 
      dateStrings[i] = dateToString(dates[i]);

    // Write the date strings to a file
    String fileName = "dates.txt";
    String binaryFileName = "binarydates.bin";                                          // ***** added *****
    String directoryName = "C://Beg Java Stuff";

    // Make sure we have a directory for the file
    File directory = new File(directoryName);
    if(!directory.exists() || (directory.exists() && !directory.isDirectory()))
      directory.mkdir();
      
    File datesFile = new File(directory, fileName);               
    File binaryDatesFile = new File(directory, binaryFileName);                   // ***** added *****        

    // Create the output stream for the file
    FileOutputStream textOutputStream = null;
    FileOutputStream binaryOutputStream = null;                                  // ***** added ***** 

    try {
      textOutputStream = new FileOutputStream(datesFile);
      binaryOutputStream = new FileOutputStream(binaryDatesFile);                // ***** added ***** 
    } catch (FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    FileChannel textChannel = textOutputStream.getChannel();
    FileChannel binaryChannel = binaryOutputStream.getChannel();                 // ***** added ***** 

    // Create an array of two byte buffers. The first buffer will hold the length of
    //  the date string as a binary value and therefore fixed at four bytes long.
    // The second buffer will be created in the loop that follows to suit the
    // length of each date string.
    ByteBuffer[] buffers = {ByteBuffer.allocate(4), null}; 
    ByteBuffer binaryBuffer = ByteBuffer.allocate(12);                            // ***** added ***** 
    try {
      for(int i = 0 ; i<dateStrings.length; i++) {                     // ***** modified *****
        // Write the text file
        buffers[0].putInt(dateStrings[i].length()).flip();             // ***** modified *****
        buffers[1] = ByteBuffer.allocate(2*dateStrings[i].length());   // ***** modified *****
        buffers[1].asCharBuffer().put(dateStrings[i]);                 // ***** modified *****
        textChannel.write(buffers);                                    // Write both buffers to the file
        buffers[0].clear();                                            // Clear ready for next string length

        // Write binary file
        for(int j = 0 ; j<dates[i].length ; j++)                       // ***** added ***** 
          binaryBuffer.putInt(dates[i][j]);                            // ***** added ***** 

        binaryChannel.write((ByteBuffer)(binaryBuffer.flip()));        // ***** added ***** 
        binaryBuffer.clear();                                          // ***** added ***** 
      }
    textOutputStream.close();
    binaryOutputStream.close();                                        // ***** added *****
    } catch (IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    } 
  }

  // Helper method to convert an int[] array with three elements containing month, day, year, to a string
  // representation of the date e.g.  convert array { 4, 14, 1962 } to "14th April 1962"
  private static String dateToString(int[] date) {
    assert date.length == 3;                                  // Array must have three elements
    StringBuffer dateString = new StringBuffer(String.valueOf(date[1]));

    String ending = null;                                    // Day ending, st, nd, or th
    if(date[1] == 1 || date[1] == 21 || date[1] == 31)
      ending = "st ";
    else if(date[1] == 2 || date[1] == 22) 
      ending = "nd ";
    else if(date[1] == 3 || date[1] == 23)
      ending = "rd ";
    else
      ending = "th ";
    return dateString.append(ending).append(months[date[0]-1]).append(String.valueOf(date[2])).toString();
  }
}
