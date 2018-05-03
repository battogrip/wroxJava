// Chapter 8 Exercise 1

import java.io.StreamTokenizer;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class ParseString {
  public static void main(String[] args) {
    String str = "Fred, Bill, Jane, Joan, Janet,John";
    char separator = ',';
    StreamTokenizer tokenizer = new StreamTokenizer(new BufferedReader(new StringReader(str)));
    tokenizer.resetSyntax();              
    tokenizer.wordChars('\u0000',(char)(separator-1));                    // Everything is a word character
    tokenizer.wordChars((char)(separator+1),'\u00ff');                    // except for the separator
    tokenizer.whitespaceChars(separator,separator);                       // Whitespace separates words so just the separator
  
    int type = 0;                                                         // Stores the value returned by nextToken()
    String token = null;                                                  // Will store the tokens that we find
    try {
      while((type = tokenizer.nextToken()) != StreamTokenizer.TT_EOF) {   // As long as we don't have EOF
        if(type == StreamTokenizer.TT_WORD) {                             // Check for a word
          System.out.println("  " + tokenizer.sval.trim());               // and output it 
        } else {
          assert false;                                                   // We only expect words
        }
      }
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}