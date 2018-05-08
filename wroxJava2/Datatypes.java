package wroxJava2;

/*
 byte ; 1 byte (8 bits); MSB reserved for sign; range -(2^7) - (2^7) ; -128 to +127; max 255 chars (128 + 127) can be stored 
 boolean ; 1 byte
 
 short ; 2 byte (16 bits); MSB reserved for sign; range -(2^15) - (2^15) ; -32768 to +32767; max 65535 chars can be stored
 char ; 2 byte
 
 int ; 4 byte ; 4 byte (32 bits); MSB reserved for sign; range -(2^31) - (2^31)
 float ; 4 byte
 
 long ; 8 byte 4 byte (64 bits); MSB reserved for sign; range -(2^63) - (2^63)
 double ; 8 byte
 
 3 categories
 Character type
 Boolean type
 Numerical
 	Integral type (byte, short, int, long)
 	Floating type (float, double)
 	
java works with Unicode format (decimal representation)
http://www.ssec.wisc.edu/~tomw/java/unicode.html

 
 */



public class Datatypes {
	
	public static void main(String[] args) {
		//byte b = 200; //out of range
		byte b = 127;
		b++;
		double dbl = -3.4e38;
		
		System.out.println("b :" + b); // b= -128 (cycle)
		
		System.out.println(Byte.MIN_VALUE);
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);
		
		System.out.println(2e2); //200
		System.out.println(4e3); //4000
		System.out.println(dbl); //
		
		//octal rep
		int i2 = 010; //octal value > 8; 0*8^2 + 1*8^1 + 0*8^0
		int i3 = 01010; //octal value > ; 0*8^4 + 1*8^3  + 0*8^2 + 1*8^1 + 0*8^0 = 520
		System.out.println("i2 =" + i2);
		System.out.println("i3 =" + i3);
		
		//hex rep
		//0123456789A(=10) B(=11) C(=12) DEF(=15)
		int i4 = 0x10A;
		System.out.println("i4 = " + i4); // 1 0 A > 1*16^2 + 0*16^1 + 10*16^0 = 256 + 0 + 10 = 266
		
		//char notations
		char ch1 = 'A'; //value dec = 65
		char ch2 = 65;
		ch2++; // A + 1 pos > next value B 

		char ch3 = 433;
		
		System.out.println(ch1);
		System.out.println(ch2);
		System.out.println(ch3 ); // Ʊ > unicode value 433 (see table: 0x01B1	433	LATIN CAPITAL LETTER UPSILON	Ʊ )
	}

}
