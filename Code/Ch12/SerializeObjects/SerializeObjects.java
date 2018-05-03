import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SerializeObjects {
  public static void main(String[] args) {
    Junk obj1 = new Junk("A green twig is easily bent.");
    Junk obj2 = new Junk("A little knowledge is a dangerous thing.");
    Junk obj3 = new Junk("Flies light on lean horses.");
    ObjectOutputStream objectOut = null;
    try {
       // Create the object output stream
       objectOut = new ObjectOutputStream(
                    new BufferedOutputStream(
                     new FileOutputStream("C:/Beg Java Stuff/JunkObjects.bin")));

      // Write three objects to the file
      objectOut.writeObject(obj1);            // Write object
      objectOut.writeObject(obj2);            // Write object
      objectOut.writeObject(obj3);            // Write object
      System.out.println("\n\nobj1:\n" + obj1
                        +"\n\nobj2:\n" + obj2
                        +"\n\nobj3:\n" + obj3);

    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }

    // Close the stream
    try {
       objectOut.close();                          // Close the output stream

    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }
}
