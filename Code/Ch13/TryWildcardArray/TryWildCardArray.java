public class TryWildCardArray {
  public static void main(String[] args) {
    BinaryTree<?>[] trees = {new BinaryTree<Integer>(), new BinaryTree<String>()};
    LinkedList<?>[] lists = new LinkedList<?>[trees.length];
    
    int[] numbers = new int[30];
    for(int i = 0 ; i<numbers.length ; i++) {
      numbers[i] = (int)(1000.0*Math.random());   // Random integers 0 to 999
    }
    // List starting integer values
    int count = 0;
    System.out.println("Original values are:");
    for(int number : numbers) {
      System.out.printf("%6d", number);
      if(++count%6 == 0) {
        System.out.println();
      }
    }

    // Add the integers to first tree
    for(int number:numbers) {
      ((BinaryTree<Integer>)trees[0]).add(number);
    }

    // Create an array of words to be sorted
    String[] words = {"vacillate", "procrastinate", "arboreal", "syzygy", "xenocracy",
                         "zygote", "mephitic", "soporific", "grisly", "gristly" };

    // List the words
    System.out.println("\nOriginal word sequence:");
    for(String word : words) {
      System.out.printf("%-15s", word);
        if(++count%5 == 0) {
          System.out.println();
        }
    }

    // Insert the words into second tree
    for(String word : words) {
      ((BinaryTree<String>)trees[1]).add(word);
    }

    // Sort the values in both trees
    for(int i = 0 ; i<lists.length ; i++){
    lists[i] = trees[i].sort();
  }

    // List the sorted values from both trees
    for(LinkedList<?> list : lists){
      System.out.println("\nSorted results:");
      listAll(list);
    }
  }

  // List the elements in any linked list
  public static void listAll(LinkedList<?> list) {
    for(Object obj : list) {
      System.out.println(obj);
    }
  }
}