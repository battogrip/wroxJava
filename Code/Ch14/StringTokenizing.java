public class StringTokenizing {
  public static void main(String[] args) {
    String text = "To be or not to be, that is the question."; // String to segment
    String delimiters = "\\s+|,\\s*|\\.\\s*";

    // Analyze the string 
    String[] tokens = text.split(delimiters);

    // Output the tokens
    System.out.println("Number of tokens: " + tokens.length);
    for(int j = 0; j<tokens.length; j++) {
      System.out.println(tokens[j]);
    }
  }
}
