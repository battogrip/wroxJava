import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.HashMap;

class PhoneBook implements Serializable {
  public void addEntry(BookEntry entry) {
    phonebook.put(entry.getPerson(), entry);
  }

  public BookEntry getEntry(Person key) {
    return phonebook.get(key);  
  }

  public PhoneNumber getNumber(Person key) {
    return getEntry(key).getNumber();  
  }

  private HashMap<Person,BookEntry> phonebook = new HashMap<Person,BookEntry>();
}
