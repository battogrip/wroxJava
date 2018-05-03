/*
    The code assumes that each person has only one phone number but there can be
    several people corresponding to the same phone number.
    
    Adding an entry will involve adding entries to two hash maps: a hash map keyed on PhoneNumber objects 
    and to a hash map keyed on second names. The value for each entry in both maps will be a 
    linked list of BookEntry objects.
    
    Before adding each entry it will be necessary to check whether entries for the second name
    and number keys already exist in the maps. If an entry exists, the existing entry must be
    updated. If there is no existing entry, one must be created.
    
*/
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Collection;
import java.io.Serializable;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class PhoneBook implements Serializable {
  public void addEntry(BookEntry entry) {
   String name = entry.getPerson().getSecondName();
    PhoneNumber number = entry.getNumber();

    if(personBook.containsKey(name)) {       // The name exists
      updateEntry(name, entry);              // so update existing entry for the name
    } else {
      addNewEntry(name, entry);
    }
      
    if(numberBook.containsKey(number)) {     // If the number exists
      updateEntry(number,entry);             // update the entry for the number
    } else { 
      addNewEntry(number,entry);             // otherwise create a new list entry
    }
  }

  // Update map keyed on second names
  private void updateEntry(String key, BookEntry entry) {
    LinkedList<BookEntry> list = personBook.get(key);
    assert(list != null);

    list.add(entry);
  }

  // Update map keyed on numbers
  private void updateEntry(PhoneNumber key, BookEntry entry) {
    LinkedList<BookEntry> list = numberBook.get(key);
    assert(list != null);

    list.add(entry);
  }

  // Create a new entry in the map keyed on second names
  private void addNewEntry(String key, BookEntry entry) {
    LinkedList<BookEntry> list = new LinkedList<BookEntry>();
    list.add(entry);
    personBook.put(key, list);
  }

  // Create a new entry in the map keyed on numbers
  private void addNewEntry(PhoneNumber key, BookEntry entry) {
    LinkedList<BookEntry> list = new LinkedList<BookEntry>();
    list.add(entry);
    numberBook.put(key, list);
  }

  // Get the entry for a given name
  public BookEntry[] getEntries(Person person) {
    LinkedList<BookEntry> list = personBook.get(person.getSecondName());
    if(list == null) {
      return new BookEntry[0];
    }
    LinkedList<BookEntry> entries = new LinkedList<BookEntry>();  // Stores entries matching person
    for(BookEntry be : list) {
      if(be.getPerson().equals(person)) {
        entries.add(be);
      }
    }
    // Return the contents of the list of entries found as an array
    return entries.toArray(new BookEntry[0]);
  }

  public BookEntry[] getEntries(PhoneNumber number) {
    LinkedList<BookEntry> list = numberBook.get(number);
    if(list == null){
      return new BookEntry[0];               // If no entry, return array with no elements
    } else {
      return list.toArray(new BookEntry[0]); // Return the contents of the list as an array
    }
  }

  // Return entries corresponding to a second name
  public BookEntry[] getEntries(String name) {
    LinkedList<BookEntry> list = personBook.get(name);
    if(list == null){
      return new BookEntry[0];               // If no entry, return array with no elements
    } else {
      return list.toArray(new BookEntry[0]); // Return the contents of the list as an array
    }
  }

  public PhoneBook() {
    if(filename.exists())
    try {
      ObjectInputStream in = new ObjectInputStream(
                                 new FileInputStream(filename));
      personBook = (HashMap<String, LinkedList<BookEntry> >)in.readObject();
      numberBook = (HashMap<PhoneNumber, LinkedList<BookEntry> >)in.readObject();
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
  // This is a little complicated because the entries in the person map are linked lists,
  // and these are returned as a Collection object by the values() method for HashMap<>
  public void listEntries() {
    // Get the entries as a collection of linked lists
    Collection<LinkedList<BookEntry>> entriesCollection = personBook.values();
    for(LinkedList<BookEntry> entries : entriesCollection) {
      Collections.sort(entries);                     // Sort the entries in the current list
      // Output the entries in the list
      for(BookEntry entry : entries) {
        System.out.println(entry);
      }
    }
  }

  private File filename = new File("Phonebook2.bin"); 

  // The personBook map is now keyed on String objects - second names, instead of Person objects.
  // The values for both maps are references to LinkedList<BookEntry> objects
  private HashMap<String,LinkedList<BookEntry> > personBook = new HashMap<String,LinkedList<BookEntry>>();
  private HashMap<PhoneNumber,LinkedList<BookEntry> > numberBook = new HashMap<PhoneNumber,LinkedList<BookEntry>>();
}

