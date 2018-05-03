import java.util.regex.Pattern;
import java.util.regex.Matcher;

class SearchAndReplace  
{
  public static void main(String args[]) 
  {
    String joke = "My dog hasn't got any nose.\n"
                 +"How does your dog smell then?\n"
                 +"My dog smells horrible.\n";
    String regEx = "dog";

    Pattern doggone = Pattern.compile(regEx);
    Matcher m = doggone.matcher(joke);

    StringBuffer newJoke = new StringBuffer();
    while(m.find()) {
      m.appendReplacement(newJoke, "goat");
    }
    m.appendTail(newJoke);
    System.out.println(newJoke.toString());
  }
}
