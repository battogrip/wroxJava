public class TryStack {
  public static void main(String[] args) {
    // Values to be stored in stack and then retrieved
    double[] values = { 1.0, 1.4142 , 1.732 , 2.0, 2.236, 2.449, 2.646, 2.828, 3.0, 3.162 }; 
    
    Stack<Double> data = new Stack<Double>();
    
    // Store the values on the stack
    System.out.println("Data pushed onto the stack in sequence is:");
    for(double v : values) {
      System.out.println(v);
      data.push(v);
    }

    double back = 0;
    System.out.println("\nData retrieved from the stack in sequence is:");
    while(!data.isEmpty()) {
      System.out.println(data.pop());
    }

    System.out.println("The stack should now be empty. Popping an item produces: "+data.pop());
  }
}