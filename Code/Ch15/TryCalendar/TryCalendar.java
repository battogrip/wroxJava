import java.util.GregorianCalendar;
import java.text.DateFormatSymbols;
import static java.util.Calendar.*;

class TryCalendar {
  public static void main(String[] args) {
    FormattedInput in = new FormattedInput();

    // Get the date of birth from the keyboard
    int day = 0, month = 0, year = 0;
    System.out.println("Enter your birth date as dd mm yyyy: ");
    try {
      day = in.readInt();
      month = in.readInt();
      year = in.readInt();
    } catch(InvalidUserInputException e) {
      System.out.println("Invalid input - terminating...");
      System.exit(1);
    }

    // Create birth date calendar - month is 0 to 11
    GregorianCalendar birthdate = new GregorianCalendar(year, month-1,day);
    GregorianCalendar today = new GregorianCalendar();        // Today's date

    // Create this year's birthday
    GregorianCalendar birthday = new GregorianCalendar(
                                        today.get(YEAR),
                                        birthdate.get(MONTH),
                                        birthdate.get(DATE));  

    int age = today.get(today.YEAR) - birthdate.get(YEAR);

    String[] weekdays = new DateFormatSymbols().getWeekdays(); // Get day names

    System.out.println("You were born on a " + 
                        weekdays[birthdate.get(DAY_OF_WEEK)]);
    System.out.println("This year you " + 
                        (birthday.after(today)   ?"will be " : "are ") +
                        age + " years old.");
    System.out.println("In " + today.get(YEAR) + " your birthday "+
                       (today.before(birthday)? "will be": "was")+
                       " on a "+ weekdays[birthday.get(DAY_OF_WEEK)] + ".");
  }
}
