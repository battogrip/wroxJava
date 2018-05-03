import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TryCapturingGroups {
  public static void main(String args[]) {
    String regEx = "[+|-]?(\\d+(\\.\\d*)?)|(\\.\\d+)";
    String str = "256 is the square of 16 and -2.5 squared is 6.25 " +
                                            "and -.243 is less than 0.1234.";
    Pattern pattern = Pattern.compile(regEx);
    Matcher m = pattern.matcher(str);
    while(m.find()) {
      for(int i = 0; i<=m.groupCount() ; i++) {
        System.out.println("Group " + i + ": " + m.group(i)); // Group i substring
      }
    }
  }
}
