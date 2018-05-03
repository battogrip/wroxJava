// Chapter 12 Exercise 2

/*
  This is a easy extension to the previous exercise solution. The only tricky part is 
  how to avoid running over the end-of-file marker when displaying the contents of the 
  Persons.bin file.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class PersonSaver {
  public PersonSaver(){
    File dir = new File(directory);                            // Directory File object
    if(!dir.isDirectory()) {                                   // If there's no directory
      dir.mkdir();                                             // create one
  }
    file = new File(dir, filename);                            // Person file
  }
  
  public static void main(String[] args) {
    PersonSaver saver = new PersonSaver();          // Create a saver object
    saver.operate();                                // and execute it
  }

  // Runs the Person saver
  private void operate(){
    // Get operation selection from the keyboard
    while(true) {  
      switch(selectOperation()) {

        case 'N': case 'n':  // Read and add a new person
          addPerson();
          break;

        case 'D': case 'd':  // Display the contents of the file
          displayPersons();
          break;

        case 'Q': case 'q':  // Quit the program, but first close all streams
          try {
            if(objectsOut != null)
              objectsOut.close();
          }catch(IOException e){
            e.printStackTrace();
          }
          System.exit(0);
          break;

        default:
          System.out.println("Invalid Input. Try again.");
          break;
      }
    }
  }
    
  // Method to read a character entered on the keyboard
  private char selectOperation() {
    System.out.println("\nEnter a letter to select an operation:"
                      +"\n Q or q to quit the program."
                      +"\n N or n to enter a new name and address."
                      +"\n D or d to display the contents of the file.");
                            
    String s = null;
    try {
      s = kb.readLine();
    } catch (IOException e) {
        e.printStackTrace(System.err);
        System.exit(1);
    }
    
    return s.trim().charAt(0);  // Return the first non-blank letter
  }

  // Method to add a new Person object to the file  
  public void addPerson() {
    Person person = null;
    String secondname = null;
    try {
      System.out.println("Enter first name:");
      String firstname = kb.readLine().trim();
      System.out.println("Enter second name:");
      secondname = kb.readLine().trim();
      Name name = new Name(firstname, secondname);

      StringBuffer sb = new StringBuffer();  // To store address
      
      // We will add each address line to sb terminated by '\n'
      System.out.println("Enter address one line at a time. Enter ! to end:");
      String s = null; 
      while((s = kb.readLine().trim()).charAt(0) != '!') {
        sb.append(s).append('\n');
      }
      person = new Person(new Name(firstname, secondname), new Address(sb.toString()));
    } catch(IOException e) {
      System.err.println("Error reading from keyboard\n");
      e.printStackTrace();
      System.exit(1); 
    }   

    // Write the new Person object to the file
    try {
      if(objectsOut == null) {
      
        boolean personFileCheck = file.exists() && file.length()>0;
        FileOutputStream ostream = new FileOutputStream(file, true);
        if(personFileCheck) {
          // The file exists so use the class type that inhibits writing the header
          objectsOut = new ReuseObjectOutputStream(ostream);
        } else {
          // Since the file does not exist, creating the output stream 
          // this will create a file to which a header will be written.
          objectsOut = new ObjectOutputStream(ostream);  // To write Person objects
        }
      }
      objectsOut.writeObject(person);
      objectsOut.flush();
    } catch(IOException e){
      e.printStackTrace();
      System.exit(1); 
    }   
  }
  
  // Method to output persons in the file  
  public void displayPersons() {
    if(!file.exists()){
      System.out.println("File does not exist because no records have been entered.");
      return;
    }
    Person person = null;
    try {
      FileInputStream istream = new FileInputStream(file);
      ObjectInputStream objectsIn = new ObjectInputStream(istream);
      while(istream.available()>0) {                           // While there are bytes in the underlying stream
        System.out.println((Person)(objectsIn.readObject()));  // Read and display an object
      }
      istream.close();                                         // Close the stream
    } catch(IOException e){
      e.printStackTrace();
      System.exit(1); 
    } catch(ClassNotFoundException e){
      e.printStackTrace();
      System.exit(1);
    } 
  }

  private String directory = "C:/Beg Java Stuff";
  private String filename = "Persons.bin";                    // Stores Person objects

  // Buffered keyboard input stream
  private BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

  // File paths
  private File file = null;                                   // Person file
  
  private ObjectOutputStream objectsOut = null;
}
