public class Jacket {
  public Jacket(JacketSize size, JacketColor color) {
    this.size = size;
    this.color = color;
  }

  public String toString() {
    StringBuffer str = new StringBuffer("Jacket ");
    return str.append(size).append(" in ").append(color).toString();
  }

  private JacketSize size;
  private JacketColor color; 
}