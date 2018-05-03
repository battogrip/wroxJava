import static java.lang.Integer.toBinaryString;

public class BitwiseOps {
  public static void main(String[] args) {
    int indicators = 0xFF07;
    int selectBit3 = 0x4;        // Mask to select the 3rd bit
    
    // Try the bitwise AND to select the third bit in indicators
    System.out.println("indicators               = " + toBinaryString(indicators));
    System.out.println("selectBit3               = " + toBinaryString(selectBit3));
    indicators &= selectBit3;
    System.out.println("indicators & selectBit3  = " + toBinaryString(indicators));

    // Try the bitwise OR to switch the third bit on
    indicators = 0xFF09;
    System.out.println("\nindicators               = " + toBinaryString(indicators));
    System.out.println("selectBit3               = " + toBinaryString(selectBit3));
    indicators |= selectBit3;
    System.out.println("indicators | selectBit3  = " + toBinaryString(indicators));

    // Now switch the third bit off again
    indicators &= ~selectBit3;
    System.out.println("\nThe third bit in the previous value of indicators has been switched off");
    System.out.println("indicators & ~selectBit3 = " + toBinaryString(indicators));
  }
}
