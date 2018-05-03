import java.util.Vector;

public class TrySimpleVector {
  public static void main(String[] args) {
    Vector<String> names = new Vector<String>();
    String[] firstnames = { "Jack", "Jill", "John", 
                            "Joan", "Jeremiah", "Josephine"};

    // Add the names to the vector
    for(String firstname : firstnames) {
      names.add(firstname);
    }

    // List the contents of the vector
    for(String name : names) {
      System.out.println(name);
    }    
/*
    // Code to use an iterator
    java.util.Iterator<String> iter = names.iterator();
    while(iter.hasNext()) {
      System.out.println(iter.next());
    }

    // Accessing elements using the get() method
    for(int i = 0 ; i<names.size() ; i++) {
      System.out.println(names.get(i));
    }
*/
  }
}
