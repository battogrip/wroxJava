public class Stack<T> {
  public Stack() {
    items = new Object[initialCapacity];
  }

  // Push an item onto the stack
  public void push(T item) {
    if(itemCount==items.length) {
      Object[] newItems = new Object[items.length + increment];
      for(int i = 0 ; i<items.length ; i++) {
        newItems[i] = items[i];
      }
      items = newItems;
    }
    items[itemCount++] = item;
  }

  // Pop an item off the top of the stack
  public T pop() {
    if(itemCount == 0) {
      return null;
    }
    // Note: this cast to type T will result in a warning
    // from the compiler but is unavoidable
    T item = (T)items[--itemCount];
    items[itemCount] = null;             // Erase the object from the stack
    return item;
  }

  // Test for empty stack
  public boolean isEmpty() {
    return itemCount == 0;
  }

  // List the contents of the stack
  // This lists in FIFO sequence. For LIFO sequence go through the items array backwards.
  // The cast to type T will ensure the toString() for T will be called, if it exists.
  public void listAll() {
    for(int i = 0 ; i<itemCount ; i++) {
      System.out.println(((T)items[i]).toString());
    }
  }

  // Note: you cannot create an array of type T to store items in the stack
  // so you have to use type Object[]
  private Object[] items;                        // Array to store items in the stack
  private int itemCount;                         // Number of items in the stack

  private final static int initialCapacity = 10; // Initial stack capacity
  private final static int increment = 5;        // Capacity increment when full
}