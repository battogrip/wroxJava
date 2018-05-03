import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import static java.lang.Math.random;

public class TrySerializableLinkedList {
  public static void main(String[] args) {
    LinkedList<Integer> numbers = new LinkedList<Integer>();
    for(int i = 0 ; i<10 ; i++) {
      numbers.addItem(1+(int)(100.0*random()));  // Add ten random integers 1 to 100
    }
    
    System.out.println("\nnumbers list contains:");
    listAll(numbers);                   // List contents of numbers
  
    // Now serialize the list to a file 
    String filename = "C:/Beg Java Stuff/Numbers.bin";
    try {
      ObjectOutputStream objOut = new ObjectOutputStream(
                                               new FileOutputStream(filename));
      objOut.writeObject(numbers);
      objOut.close();
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  
    LinkedList<Integer> values = null;    // Variable to store list from the file

    // Deserialize the list from the file
    try {
      ObjectInputStream objIn = new ObjectInputStream(
                                            new FileInputStream(filename));
      values = (LinkedList<Integer>)(objIn.readObject());
      objIn.close();
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    } catch(ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }

    System.out.println("\nvalues list contains:");
    listAll(values);                   // List contents of values
  }

  // List the contents of a linked list
  static void listAll(LinkedList<Integer> list) {
    Integer number = list.getFirst();
    int count = 0;
    do {
      System.out.printf("%5d",number);
      if(++count%5 == 0 ) {
        System.out.println();
      }
    } while((number = list.getNext()) != null);
  }

}