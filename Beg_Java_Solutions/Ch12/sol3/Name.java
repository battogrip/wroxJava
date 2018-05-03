// Class encapsulating a name
import java.io.Serializable;

public class Name implements Serializable {
  // Constructor  
  public Name(String firstname, String secondname) {
    firstName = firstname;
    secondName = secondname;
  }
  
  public String toString() {
    return firstName + " " + secondName;
  }

  private String firstName;
  private String secondName;
}