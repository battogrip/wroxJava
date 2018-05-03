import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FindingIntegers {
  public static void main(String args[])
 {
    String regEx = "[+|-]?(\\d+(\\.\\d*)?)|(\\.\\d+)";
    String str = "256 is the square of 16 and -2.5 squared is 6.25 " +
                                            "and -.243 is less than 0.1234.";
    Pattern pattern = Pattern.compile(regEx);
    Matcher m = pattern.matcher(str);
    int i = 0;
    String subStr = null;
    while(m.find()) {
      System.out.println(m.group());              // Output the substring matched
    }
  }
}
