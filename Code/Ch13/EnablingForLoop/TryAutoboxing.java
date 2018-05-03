public class TryAutoboxing {
  public static void main(String[] args) {
    LinkedList<Double> temperatures = new LinkedList<Double>();

    // Insert 6 temperature values 0 to 25 degress Centigrade
    for(int i = 0 ; i<6 ; i++) {
      temperatures.addItem(25.0*Math.random());
    }
      
    System.out.println(toFahrenheit(temperatures.getFirst()) + " degrees Fahrenheit");
    
    for(Double value : temperatures) {
      System.out.println(toFahrenheit(value) + " degrees Fahrenheit");
    }
  }

  // Convert Centigrade to Fahrenheit
  public static double toFahrenheit(double temperature) {
    return 1.8*temperature+32.0;
  }
}