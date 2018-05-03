import java.util.Comparator;

public class ComparePersons implements Comparator<Person> {
  // Method to compare Person objects - order is descending
  public int compare(Person person1, Person person2) {
    int result = -person1.getSurname().compareTo(person2.getSurname());
    return result == 0 ? -person1.getFirstName().compareTo(person2.getFirstName()):result;
  }

  // Method to compare with another comparator
  public boolean equals(Object collator) {
    if(this == collator) {             // If argument is the same object
      return true;                     // then it must be equal
    }
    if(collator == null) {             // If argument is null
      return false;                    // then it can't be equal
    }
    return getClass() == collator.getClass(); // Class must be the same for equal
  }
}
