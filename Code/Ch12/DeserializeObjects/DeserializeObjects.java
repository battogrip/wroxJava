import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.EOFException;

class DeserializeObjects {  
  public static void main(String args[]) {
    ObjectInputStream objectIn = null;  // Stores the stream reference
    int objectCount = 0;                // Number of objects read
    Junk object = null;                 // Stores an object reference
    try {
      objectIn = new ObjectInputStream(
                   new BufferedInputStream(
                     new FileInputStream("C:/Beg Java Stuff/JunkObjects.bin")));

       // Read from the stream until we hit the end
       while(true) {
        object = (Junk)objectIn.readObject();// Read an object
        objectCount++;                       // Increment the count
        System.out.println(object);          // Output the object
      }

    } catch(ClassNotFoundException e) {
      e.printStackTrace(System.err);
      System.exit(1);

    } catch(EOFException e) {                // This will execute when we reach EOF
      System.out.println("EOF reached. "+ objectCount + " objects read.");

    } catch(IOException e) {                 // This is for other I/O errors 
      e.printStackTrace(System.err);
      System.exit(1);
    }

    // Close the stream
    try {
      objectIn.close();                      // Close the input stream

    } catch(IOException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }
}
