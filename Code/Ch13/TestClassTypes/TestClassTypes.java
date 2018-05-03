public class TestClassTypes {
  public static void main(String[] args) {

  LinkedList<String> proverbs = new LinkedList<String>();
  LinkedList<Double> numbers = new LinkedList<Double>();
   System.out.println("numbers class name " + numbers.getClass().getName());
   System.out.println("proverbs class name " + proverbs.getClass().getName());
   System.out.println("Compare Class objects: "
                            + numbers.getClass().equals(proverbs.getClass()));

  }
}
