public class TryProperties {
  public static void main(String[] args) {
    java.util.Properties properties = System.getProperties();
    properties.list(System.out);
  }
}