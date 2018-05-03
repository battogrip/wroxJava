public class BinaryTree<T extends Comparable<? super T>> {
  // No-arg constructor
  public BinaryTree() {}
  

  // Parameterized constructor
  public <E extends T> BinaryTree(E[] items) {
    for(E item : items) {
      add(item);
    }
  }
  
  // Add a value to the tree
  public <E extends T> void add(E value) {
    if(root == null) {                 // If there's no root node
      root = new Node(value);          // store it in the root
    } else {                           // Otherwise...
      add(value, root);                // add it recursively
    }
  }

  // Recursive insertion of an object
  private <E extends T> void add(E value, Node node) {
    int comparison = node.obj.compareTo(value); 
    if(comparison == 0) {              // If it is equal to the current node
      ++node.count;                    // just increment the count
      return;
    }
    if(comparison > 0) {               // If it's less than the current node
      if(node.left == null) {          // and the left child node is not null
        node.left = new Node(value);   // Store it as the left child node
      } else {                         // Otherwise...
        add(value, node.left);         // ...add it to the left node
      }
    } else {                           // It must be greater than the current node
      if(node.right == null) {         // so it must go to the right...
        node.right = new Node(value);
      } else {
        add(value, node.right);
      }
    }
  }

  // Create a list containing the values from the tree in sequence  
  public LinkedList<T> sort() {
    treeSort(root);                    // Sort the objects into the list
    return values;
  }
  
  // Extract the tree nodes in sequence
  private void treeSort(Node node) {
    if(node != null) {                 // If the node isn't null
      treeSort(node.left);             // process its left child

      // List the duplicate objects for the current node
      for(int i = 0 ; i<node.count ; i++) {
        values.addItem(node.obj);
       }
      treeSort(node.right);            // Now process the right child
    }
  }

  LinkedList<T> values = new LinkedList<T>();    // Stores sorted values
  private Node root;                             // The root node

  // Private inner class defining nodes
  private class Node {
    Node(T value) {
      obj = value;
      count = 1;
    }
    
    T obj;                                       // Object stored in the node
    int count;                                   // Count of identical nodes 
    Node left;                                   // The left child node
    Node right;                                  // The right child node
  }
}
