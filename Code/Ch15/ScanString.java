import java.util.Scanner;
import java.util.regex.Pattern;

public class ScanString {
  public static void main(String[] args) {
    String str = "Smith , where Jones had had 'had', had had 'had had'.";
    String regex = "had";
    System.out.println("String is:\n"+str+"\nToken sought is: "+regex);

    Pattern had = Pattern.compile(regex);
    Scanner strScan = new Scanner(str);
//    strScan.useDelimiter("\\s'|\\s*'?[.|,]\\s*|\\s");
    int hadCount = 0;
    while(strScan.hasNext()) {
      if(strScan.hasNext(had)) {
        ++hadCount;
      System.out.println("Token found!: " + strScan.next(had));
      } else {
        System.out.println("Token is    : " + strScan.next());
      }
    }
    System.out.println(hadCount + " instances of \"" + regex +  "\" were found.");
  }
}