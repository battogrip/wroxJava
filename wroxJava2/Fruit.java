package wroxJava2;
import java.io.IOException; // For code that delays ending the program

public class Fruit {
	public static void main(String[] args) {
		// Declare and initialize three variables
		int numOranges = 5; // Count of oranges
		int numApples = 10; // Count of apples
		int numFruit = 10; // Count of fruit
		int numFruitEach = 0; // Number of fruit for each child
		numFruitEach = numFruit/4;
		int count = 10;
		
		char myCharacter = 'X';
		char myCharacter2 = 'A';
		myCharacter += 1; // Increment to next character >> 'Y'
		System.out.println("myCharacter: " + myCharacter);
		++myCharacter2;
		System.out.println("myCharacter2: " + myCharacter2); // Increment to next character >> 'B'
		
		char aChar = 0;
		char bChar = '\u0028'; //dec = 40
		aChar = (char)(2*bChar + 8); // 2*bChar > dec = 2*40 = 80; hex= 50 + 8 = 58 
		System.out.println("aChar = " + aChar); // aChar = 58
		
		numFruit = numOranges + numApples; // Calculate the total fruit count
		// Display the result
		System.out.println("A totally fruity program");
		System.out.println("Total fruit is " + numFruit);
		String classpath = System.getProperty("java.class.path");
		System.out.println(classpath);
		System.out.println(numFruitEach);
		System.out.println("count = " + count);
		System.out.println("add 1 to count: " + ++count);
		System.out.println("add 7 to count: " + (count+=7)); // count = count + 7
			
		// Increment oranges and calculate the total fruit
		numFruit = ++numOranges + numApples;
		System.out.println("A totally fruity program");
		// Display the result
		System.out.println("Value of oranges is " + numOranges);
		System.out.println("Total fruit is " + numFruit);
		
		// Code to delay ending the program
		System.out.println("(press Enter to exit)");
		try {
		System.in.read(); // Read some input from the keyboard
		} catch (IOException e) { // Catch the input exception
		return; // and just return
		}
	}
}
