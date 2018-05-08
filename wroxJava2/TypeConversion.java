package wroxJava2;

public class TypeConversion {

	public static void main(String[] args) {
		
		//while performing arithmetic operation, when you have 2 integer data type, the result will be int
		byte b = 10;
		int x = b;
		
		int num1 = 10*100;
		int num3 = 10+119; //129
		//byte num2 = num1; //type auto conversion not allowed > assign int to byte
		
		//Explicit type casting > done by program
		byte num2 = (byte) num1; //explicit type casting allowed
		byte num4 = (byte) num3; //explicit type casting allowed
		
		System.out.println("b = " + b + " x = " + x);
		
		//negative cycle; byte range -128 / 127
		System.out.println(num2);
		System.out.println(num4);
		
		//float f1 = 10.5; //not allowed; will be assigned as a double
		float f1 = 10.5f; //correct notation for float
		double d1 = 11.6;
		
		//implicit type casting >  auto conv done by JVM
		byte num11 = 10;
		byte num99 = 110;
		//byte num13 = num11 + num99 // assinging via variable not allowed; '+' operator will auto conv num11 and num99 to int type
		byte num13 = (byte) (num11 + num99); // allowed
		byte num14 = 10 + 20; //direct assigning allowed
		short num22 = num11;
		int num33 = num22 * 30;
		long num44 = num33 + 100;
		float num55 = num44 * 12345;
		double num66 = num55;
		
		
		
		System.out.println("casted byte num13 = " + num13);
		System.out.println("casted byte num14 = " + num14);
		System.out.println(num66);
	}

}
