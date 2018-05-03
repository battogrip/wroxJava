import java.io.StreamTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class FormattedInput {

  // Method to read an int value
  public int readInt() throws InvalidUserInputException {
    if (readToken() != tokenizer.TT_NUMBER) {
      throw new InvalidUserInputException(" readInt() failed. " 
                                          + "Input data not numeric");
    } 

    if (tokenizer.nval > (double) Integer.MAX_VALUE 
            || tokenizer.nval < (double) Integer.MIN_VALUE) {
      throw new InvalidUserInputException(" readInt() failed. " 
                                          + "Input outside range of type int ");
    } 

    if (tokenizer.nval != (double) (int) tokenizer.nval) {
      throw new InvalidUserInputException(" readInt() failed. " 
                                          + "Input not an integer");
    } 
    return (int) tokenizer.nval;
  }

  // Method to read a long value
  public long readLong() throws InvalidUserInputException {
    if (readToken() != tokenizer.TT_NUMBER) {
      throw new InvalidUserInputException(" readLong() failed. " 
                                          + "Input data not numeric");
    } 

    if (tokenizer.nval > (double) Long.MAX_VALUE 
            || tokenizer.nval < (double) Long.MIN_VALUE) {
      throw new InvalidUserInputException(" readLong() failed. " 
                                          + "Input outside range of type long ");
    } 

    if (tokenizer.nval != (double) (long) tokenizer.nval) {
      throw new InvalidUserInputException(" readLong() failed. " 
                                          + "Input not an integer");
    } 
    return (long) tokenizer.nval;
  }

  // Method to read a double value
  public double readDouble() throws InvalidUserInputException {
    if (readToken() != tokenizer.TT_NUMBER) {
      throw new InvalidUserInputException(" readDouble() failed. " 
                                          + "Input data not numeric");
    }
    return tokenizer.nval;
  }

  // Method to read a double value
  public float readFloat() throws InvalidUserInputException {
    if (readToken() != tokenizer.TT_NUMBER) {
      throw new InvalidUserInputException(" readFloat() failed. " 
                                          + "Input data not numeric");
    }
    return (float)tokenizer.nval;
  }

  // Method to read a string
  public String readString() throws InvalidUserInputException {
    if (readToken() == tokenizer.TT_WORD || ttype == '\"' 
            || ttype == '\'') {
      return tokenizer.sval;
    } else {
      throw new InvalidUserInputException(" readString() failed. " 
                                          + "Input data is not a string");
    }
  }

  // Helper method to read the next token
  private int readToken() {
    try {
      ttype = tokenizer.nextToken();
      return ttype;

    } catch (IOException e) {  // Error reading in nextToken()
      e.printStackTrace(System.err);
      System.exit(1);         // End the program
    } 
    return 0;
  } 

  // Object to tokenize input from the standard input stream
  private StreamTokenizer tokenizer = new StreamTokenizer(
                                        new BufferedReader(
                                          new InputStreamReader(System.in)));
  private int ttype;   // Stores the token type code
}
