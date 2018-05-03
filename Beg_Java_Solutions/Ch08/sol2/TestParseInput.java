// Chapter 8 Exercise 2

// Tests that the ParseInput works for '/' separator

public class TestParseInput {
  public static void main(String[] args) {
    char separator = '/';
    ParseInput tokenizer = new ParseInput(separator);
  
  
  System.out.println("Enter items separated by "+separator+":"); 
  String[] inputs = tokenizer.readTokens();

  System.out.println("The items that you entered are:"); 
  if(inputs != null)
    for( String input : inputs) {
      System.out.print("  " + input);
    }
  }
}