import java.util.Locale;
import java.text.DateFormat;
import java.util.Date;
import static java.util.Locale.*;        // Import names of constants
import static java.text.DateFormat.*;    // Import names of constants

public class TryDateFormats {
  public static void main(String[] args) {
    Date today = new Date();
    Locale[] locales = {US, UK, GERMANY, FRANCE};  
    int[] styles = {FULL, LONG, MEDIUM, SHORT}; 
    String[] styleNames = {"FULL", "LONG", "MEDIUM", "SHORT"};    

    // Output the date for each locale in four styles
    DateFormat fmt = null;
    for(Locale locale : locales) {
      System.out.println("\nThe Date for " + 
                         locale.getDisplayCountry() + ":");
      for(int i = 0 ; i<styles.length ; i++) {
        fmt = DateFormat.getDateInstance(styles[i], locale);
        System.out.println( "\tIn " + styleNames[i] + 
                            " is " + fmt.format(today));
      }
    }
  }
}
