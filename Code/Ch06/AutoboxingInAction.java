public class AutoboxingInAction {
  public static void main(String[] args) {
    int[] values = { 3, 97, 55, 22, 12345 };
    Integer[] objs = new Integer[values.length];   // Array to store Integer objects
    
    // Call method to cause boxing conversions
    for(int i = 0 ; i<values.length ; i++) {
      objs[i] = boxInteger(values[i]);
    }

    // Use method to cause unboxing conversions
    for(Integer intObject : objs) {
      unboxInteger(intObject);
    }
  }

  // Method to cause boxing conversion
  public static Integer boxInteger(Integer obj) {
    return obj;
  }

  // Method to cause unboxing conversion
  public static void unboxInteger(int n) {
    System.out.println("value = " + n);
  }
}
