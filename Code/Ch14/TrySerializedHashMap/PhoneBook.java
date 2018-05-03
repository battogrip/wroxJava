import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;

class PhoneBook implements Serializable {
  public PhoneBook() {
    if(filename.exists())                   // If there's a phone book in a file...
    try {
      ObjectInputStream in = new ObjectInputStream(
      new FileInputStream(filename));
      phonebook = (HashMap<Person,BookEntry>)in.readObject(); //...read it in.
      in.close();

    } catch(ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  // List all entries in the book
  public void listEntries() {
    // Get the entries as a linked list
    LinkedList<BookEntry> entries = new LinkedList<BookEntry>(phonebook.values());
    Collections.sort(entries);                     // Sort the entries

    for(BookEntry entry : entries) {
      System.out.println(entry);
    }
  }

  public void save() {
    try {
      System.out.println("Saving phone book");
      ObjectOutputStream out = new ObjectOutputStream(
      new FileOutputStream(filename));
      out.writeObject(phonebook);
      System.out.println("Done");
      out.close();

    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public void addEntry(BookEntry entry) {
    phonebook.put(entry.getPerson(), entry);
  }

  public BookEntry getEntry(Person key) {
    return phonebook.get(key);  
  }

  public PhoneNumber getNumber(Person key) {
    return getEntry(key).getNumber();  
  }

  private File filename = new File("Phonebook.bin"); 
  private HashMap<Person,BookEntry> phonebook = new HashMap<Person,BookEntry>();
}
