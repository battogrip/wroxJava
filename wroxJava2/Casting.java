package wroxJava2;

public class Casting {

	public static void main(String[] args) {
		
		char letter1 = 'A'; // letter1 is ‘A’
		char letter2 = (char)(letter1+1); // letter2 is ‘B’
		char letter3 = letter2; // letter3 is also ‘B’
		System.out.println("Here\'s a sequence of letters: "+ letter1 + letter2 + (++letter3)); // letter3 is now ‘C’
		System.out.println("Here are the decimal codes for the letters:\n" + letter1 + ": " + " " + (int)letter1 + " " + letter2 + ": " + (int)letter2 +" " + letter3 + ": " + (int)letter3);
		
		System.out.println("Here are the hexadecimal codes for the letters:\n"+
				letter1 + ": " + Integer.toHexString(letter1) +
				" " + letter2 + ": " + Integer.toHexString(letter2) +
				" " + letter3 + ": " + Integer.toHexString(letter3));


	}

}
