/*
 This version of the class has fields containing two maps.
 The addEntry() method stores each entry in both maps, in one using the
 person as the key and in the other using the number as the key. This allows
 an entry to be found using either type of key.
*/
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;
import java.io.Serializable;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class PhoneBook implements Serializable {
  public void addEntry(BookEntry entry) {
    personBook.put(entry.getPerson(), entry);
    numberBook.put(entry.getNumber(), entry);
  }

  public BookEntry getEntry(Person key) {
    return personBook.get(key);
  }

  public BookEntry getEntry(PhoneNumber key) {
    return numberBook.get(key);
  }

  public PhoneNumber getNumber(Person key) {
    return getEntry(key).getNumber();
  }

  public Person getPerson(PhoneNumber key) {
    return getEntry(key).getPerson();
  }

  public PhoneBook() {
    if(filename.exists())
    try {
      ObjectInputStream in = new ObjectInputStream(
                                 new FileInputStream(filename));
      personBook = (HashMap<Person, BookEntry>)in.readObject();
      numberBook = (HashMap<PhoneNumber, BookEntry>)in.readObject();
      in.close();
    } catch(ClassNotFoundException e) {
      System.out.println(e);
    } catch(IOException e) {
      System.out.println(e);
    }
  }

  public void save() {
    try {
      System.out.println("Saving phone book");
      ObjectOutputStream out = new ObjectOutputStream(
                                 new FileOutputStream(filename));
      out.writeObject(personBook);
      out.writeObject(numberBook);
      System.out.println(" Done");
      out.close();
    } catch(IOException e) {
      System.out.println(e);
    }
  }

  // List all entries in the book
  public void listEntries() {
    // Get the entries as a linked list
    LinkedList<BookEntry> entries = new LinkedList<BookEntry>(personBook.values());
    Collections.sort(entries);                     // Sort the entries

    for(BookEntry entry : entries) {
      System.out.println(entry);
    }
  }

  private File filename = new File("Phonebook.bin"); 
  private HashMap<Person,BookEntry> personBook = new HashMap<Person,BookEntry>();
  private HashMap<PhoneNumber,BookEntry> numberBook = new HashMap<PhoneNumber,BookEntry>();
}

