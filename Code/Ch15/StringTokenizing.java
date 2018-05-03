public class StringTokenizing {
  public static void main(String[] args) {
    String text = "To be or not to be, that is the question."; // String to segment
    String delimiters = "\\s+|,\\s*|\\.\\s*";

    // Analyze the string 
    String[] tokens = text.split(delimiters);

    // Output the tokens
    System.out.println("Number of tokens: " + tokens.length);
    for(String token : tokens) {
      System.out.println(token);
    }
  }
}
