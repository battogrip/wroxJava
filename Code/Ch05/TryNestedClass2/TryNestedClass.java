public class TryNestedClass {
  static public void main(String[] args) {
    // Create three magic hats and output them
    System.out.println(new MagicHat("Gray Topper"));
    System.out.println(new MagicHat("Black Topper"));
    System.out.println(new MagicHat("Baseball Cap"));

/*
    // Uncomment this block of code to execute the additional statements
    // Code for modified version
    MagicHat oldHat = new MagicHat("Old hat");        // New hat object
    MagicHat.Rabbit rabbit = oldHat.new Rabbit();     // Create rabbit object
    System.out.println(oldHat);                       // Show the hat
    System.out.println("\nNew rabbit is: " + rabbit); // Display the rabbit
*/
  }
}
