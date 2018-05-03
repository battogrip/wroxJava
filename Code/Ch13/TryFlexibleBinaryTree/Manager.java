public class Manager extends Person {
  public Manager(String name, int level) {
    super(name);
    this.level = level;
  }

  public String toString() {
    return "Manager "+ super.toString() + " level: " + level;
  }

  protected int level;
}
