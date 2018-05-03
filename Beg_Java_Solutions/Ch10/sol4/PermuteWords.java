// Chapter 10 Exercise 4

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

class PermuteWords {

  // These are static class members so we can reference them in all the methods in the class
  static String phrase = "Mysticism misses the system";
  static ByteBuffer buffer = null;
  static FileChannel wordsChannel = null;

  static String[] words = null;         // Stores reference to array that will hold words from phrase
  static String[] permutation = null;   // Stores reference to array that will hold a permutation of words from the phrase
  static int path = -1;                 // Stores the number of words accumulated in one permutation



  public static void main(String args[]) {
    // Extract the words from phrase into an array. 
    // First figure out how many words there are, assuming words are separated by spaces...
    int fromIndex = -1;
    while(phrase.charAt(++fromIndex) == ' ');          // Skip any leading spaces
   
    int wordCount = 1;                                 // At least one word
    while((fromIndex = phrase.indexOf(' ', fromIndex)) != -1) {
      wordCount++;
      // Skip contiguous spaces     
      while(phrase.charAt(++fromIndex) == ' ');
    }
    words = new String[wordCount];                     // Array to hold the words from the phrase
    permutation = new String[wordCount];               // Array to hold permuted words
    
    // Now get the words    
    fromIndex = -1;
    while(phrase.charAt(++fromIndex) == ' ')           // Skip any leading spaces
      ;
    int toIndex = 0;                                   // Word end position
    int index = 0;                                     // Free element in words array
    while((toIndex = phrase.indexOf(' ', fromIndex)) != -1) {
      words[index++] = phrase.substring(fromIndex,toIndex);
      fromIndex = toIndex;
         
      while(phrase.charAt(++fromIndex) == ' ')         // Skip contiguous spaces
        ;
    }
    if(fromIndex<phrase.length()) {
      words[index] = phrase.substring(fromIndex, phrase.length());
    }

    // Now create and write the file
    String fileName = "permuted.txt";
    String directoryName = "C://Beg Java Stuff";

    // Make sure we have a directory for the file
    File directory = new File(directoryName);
    if(!directory.exists() || (directory.exists() && !directory.isDirectory())) {
      directory.mkdir();
    }
      
    File wordsFile = new File(directory, fileName);               

    // Create the output stream for the file
    FileOutputStream wordsOutputStream = null;
    try {
      wordsOutputStream = new FileOutputStream(wordsFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    wordsChannel = wordsOutputStream.getChannel();
    buffer = ByteBuffer.allocate(2*(phrase.length()+1));  // We add one to allow for '\n' at the beginning
    permute(0);                                           // Create and write all permutations of words array

    try {
      wordsOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }

  // When called with the argument 0 this methods creates all permutations of words.
  /*
     Creating all permutations of the words in the phrase is the most difficult part of the exercise. 
     If you can crack this, then writing the file is trivial :-)
     This method visualizes the permutations of the words as a graph with a dummy node at the top.
     The number of levels below the dummy node corresponds to the number of words in the phrase and each path
     from the dummy node down through the levels defines one permutation of the words in the phrase.
     The argument to the method is the index of the word in the permutation to be assigned. When this method is called with an argument of 0, it traverses 
     each of the paths in the graph and thus creates all permutations. When a path has been traversed,
     it is written to the file. The path variable records the 'length' of a path as it is traversed, 
     where the maximum length is the number of words in the phrase. When path reaches the maximum, a complete
     path has been traversed so it is written to the file.
  */
  private static void permute(int n) {
    permutation[n] = ++path == 0 ? null: words[path-1];
    if(path == words.length) { 
       write();
    } else {
      for (int i = 0; i<words.length ; i++) {
        if(permutation[i] == null) {
           permute(i);
        }
      }
    }
      path--;
      permutation[n] = null;
  }

  // Writes a permutation to the file
  private static void write() {
    CharBuffer charBuffer = buffer.asCharBuffer();
    for(int i = 0 ; i< permutation.length ; i++) 
      charBuffer.put(i==0?'\n':' ').put(permutation[i]);
   
    try {
      wordsChannel.write(buffer);
    } catch (IOException e) {
        e.printStackTrace(System.err);
        System.exit(1);
    } 
    buffer.clear();
  }
}