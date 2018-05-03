public class StringTokenizing {
  public static void main(String[] args) {
    String text = "To be or not to be, that is the question.";   // String to be segmented
    String delimiters = "[, .]";
    int[] limits = {0, -1};

    // Analyze the string 
    for(int limit : limits) {
      System.out.println("\nAnalysis with limit = " + limit);
      String[] tokens = text.split(delimiters, limit);
      System.out.println("Number of tokens: " + tokens.length);
      for(String token : tokens) {
        System.out.println(token);
      }
    }
  }
}
