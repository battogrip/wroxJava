import java.util.Arrays;

public class TrySortingWithComparator {
  public static void main(String[] args) {
    Person[] authors = {new Person("Danielle", "Steel"),    new Person("John", "Grisham"), 
                        new Person("Tom", "Clancy"),        new Person("Christina", "Schwartz"),
                        new Person("Patricia", "Cornwell"), new Person("Bill", "Bryson") };

    System.out.println("Original order:");
    for(Person author : authors) {
      System.out.println(author);
    }

    Arrays.sort(authors, new ComparePersons());  // Sort using comparator

    System.out.println("\nOrder after sorting using comparator:");
    for(Person author : authors) {
      System.out.println(author);
    }

    Arrays.sort(authors);                        // Sort using Comparable method

    System.out.println("\nOrder after sorting using Comparable method:");
    for(Person author : authors) {
      System.out.println(author);
    }
  }
}