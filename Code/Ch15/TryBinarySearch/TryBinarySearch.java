import java.util.Arrays;

public class TryBinarySearch {
  public static void main(String[] args) {
    Person[] authors = {new Person("Danielle", "Steel"),    new Person("John", "Grisham"), 
                        new Person("Tom", "Clancy"),        new Person("Christina", "Schwartz"),
                        new Person("Patricia", "Cornwell"), new Person("Bill", "Bryson") };


    Arrays.sort(authors);                        // Sort using Comparable method

    System.out.println("\nOrder after sorting into ascending sequence:");
    for(Person author : authors) {
      System.out.println(author);
    }

    // Search for authors
    Person[] people = {new Person("Christina", "Schwartz"),    new Person("Ned", "Kelly"), 
                        new Person("Tom", "Clancy"),        new Person("Charles", "Dickens")};
     int index = 0;
    System.out.println("\nIn search of authors:");
    for(Person person : people) {
      index = Arrays.binarySearch(authors, person);
      if(index >= 0) {
        System.out.println(person + " was found at index position " + index);
      } else {
        System.out.println(person + " was not found. Return value is " + index);
      }
    }
  }
}