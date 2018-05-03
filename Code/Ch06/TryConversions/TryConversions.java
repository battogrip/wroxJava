// Note that the conversions directory is a subdirectory of the MyPackages directory
// in the code download and you must include the path to MyPackages in CLASSPATH
// if this example is to compile correctly.
import static conversions.ConversionFactors.*;     // Import static members

public class TryConversions {
  
  public static double poundsToGrams(double pounds) {
    return pounds*POUND_TO_GRAM;  
  }

  public static double inchesToMillimeters(double inches) {
    return inches*INCH_TO_MM;  
  }

  public static void main(String args[]) {
    int myWeightInPounds = 180;
    int myHeightInInches = 75;
    System.out.println("My weight in pounds: " +myWeightInPounds +
                       " \t-in grams: "+ (int)poundsToGrams(myWeightInPounds));
    System.out.println("My height in inches: " +myHeightInInches +
           " \t-in millimeters: "+ (int)inchesToMillimeters(myHeightInInches));
  }
}
