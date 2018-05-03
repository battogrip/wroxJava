import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TryStack {
  public static void main(String[] args) {
    // Values to be stored in stack and then retrieved
    String[] strings = {
           "The bigger they are, the harder they hit.", 
           "It's not the knowing that is difficult, but the doing." ,
           "It's a mistake to change horses in midstream.", 
           "An ounce of practice is worth a pound of instruction.", 
           "It's a poor dog that's not worth whistling for.",
           "If wishes were horses, beggars would ride.",
           "To leap high you must take a long run.",
           "Things are never so bad that they can't get worse.",
           "If in doubt, do nowt.",
           "A cat in gloves will catch no mice." }; 
    
    Stack<String> data = new Stack<String>();
    
    // Store the strings on the stack
    System.out.println("Strings pushed onto the stack in sequence are:");
    for(String string : strings) {
      System.out.println(string);
      data.push(string);
    }
    String directory = "C:/Beg Java Stuff";
    String filename = "StringStack.bin";             // Stores Stack object
    File dir = new File(directory);                  // Directory File object
    if(!dir.isDirectory()) {                         // If there's no directory
      dir.mkdir();                                   // create one
    }
    File file = new File(dir, filename);             // Stack object file

    // Write the Stack object to a file
    try {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
      out.writeObject(data);
      out.close();
    } catch(Exception e) {     
      e.printStackTrace();
      System.exit(1);
    } 

    // Now read the Stack object back from the file
    Stack<String> newStack = null;
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
      newStack = (Stack<String>)in.readObject();
      in.close();
    } catch(Exception e) {     
      e.printStackTrace();
      System.exit(1);
    }
    // List the contents of the new stack
    System.out.println("\nContents of stack read from file:"); 
    newStack.listAll();
  }
}