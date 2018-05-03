public class TryParameterizedConstructor {
  public static void main(String[] args) {
    Manager[] managers = { new Manager("Jane",1), new Manager("Joe",3), new Manager("Freda",3),
                           new Manager("Bert", 2),new Manager("Ann", 2),new Manager("Dave", 2)};
    BinaryTree<Person> people = new BinaryTree<Person>(managers);
    listAll(people.sort());
  }

  // List the elements in any linked list
  public static <T> void listAll(LinkedList<T> list) {
    for(T obj : list) {
      System.out.println(obj);
    }
  }
}