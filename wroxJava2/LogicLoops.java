package wroxJava2;

public class LogicLoops {

	private static int ticketPrice;

	enum Season { spring, summer, fall, winter };
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int number = 0;
		number = 1+(int)(100*Math.random()); // Get a random integer between 1 & 100
		
		if(number%2 == 0) { // Test if it is even
		System.out.println("You have got an even number, " + number); // It is even
		} else {
		System.out.println("You have got an odd number, " + number); // It is odd
		}
		
		//Nested if
		letterCheck();
		
		//Enums
		//https://docs.oracle.com/javase/7/docs/api/java/lang/Enum.html
		enumCheck();
		
		//Logical operator
		letterCheck2();
		
		//Loops
				
		
	}
	
	public static void letterCheck() {
		char symbol = 'A';
		symbol = (char)(128.0*Math.random()); // Generate a random character
		
		//String echo = "letterCheck started";
		System.out.println("letterCheck started");
		
			if(symbol >= 'A') { // Is it A or greater?
				if(symbol <= 'Z') { // yes, and is it Z or less?
					// Then it is a capital letter
					System.out.println("You have the capital letter " + symbol);
				} else { // It is not Z or less
					if(symbol >= 'a') { // So is it a or greater?
						if(symbol <= 'z') { // Yes, so is it z or less?
							// Then it is a small letter
							System.out.println("You have the small letter " + symbol);
						} else { // It is not less than z
							System.out.println("The code is greater than a but it’s not a letter");
						}
					} else {
							System.out.println("The code is less than a and it’s not a letter");
					}
				}
			} else {
			System.out.println("The code is less than A so it’s not a letter");
			}
		
		//return echo;
		
	}
	
	public static void enumCheck() {
		Season season = Season.summer;
		
		if(season.equals(Season.spring)) {
			System.out.println("Spring has sprung, the grass is riz.");
			} else {
			System.out.println("It isn\'t Spring!");
			}
	}
	
	public static void letterCheck2() {
		char symbol = 'a';
		symbol = (char)(128.0*Math.random());
		System.out.println(symbol);
		
		//the conditional && will not bother to evaluate the right-hand operand (P128)
		if(symbol >= 'A' && symbol <= 'Z') { // (&&) Conditional AND - Is it a capital letter; 
			System.out.println("You have the capital letter " + symbol);
		} else {
				if(symbol >= 'a' && symbol <= 'z') { // or is it a small letter?
					System.out.println("You have the small letter " + symbol);
				} else { // It is not less than z
						System.out.println("The code is not a letter");
				}
			}
		
		int number = 50;
		if(number<40 && (3*number - 27)>100) {		//if number<40 (true), then evaluate (3*number - 27)>100); if number<40 (false), stop evaluation
		System.out.println("number = " + number);
		}
		
		int number2 = 30;
		if(number2<40 & (3*number2 - 27)>100) {		//evaluate both number<40 (true) and evaluate (3*number - 27)>100)
		System.out.println("number2 = " + number2);
		}

		//Logical OR
		int age = 15;
		ticketPrice = 10;
		
		if(age < 16 || age>= 65) {
			ticketPrice *= 0.9; // Reduce ticket price by 10%
			System.out.println("Ticket price: " + ticketPrice);
			}
		
		//Boolean NOT
		int age2 = 15;
		int ticketPrice2 = 100;
		
		if(!(age2 < 16 && age2>= 65)) {
			ticketPrice2 *= 0.9; // Reduce ticket price by 10%
			System.out.println("Ticket price age2 : " + ticketPrice2);
			}
		
		//Conditional operator
		int nHats = 1; // Number of hats
		System.out.println("I have " + nHats + " hat" + (nHats == 1 ? "." : "s."));
		
		nHats++; // Increment number of hats
		System.out.println("I have " + nHats + " hat" + (nHats == 1 ? "." : "s."));
		
		//scope variable
		int outer = 1; // Exists throughout the method
		{
		// You cannot refer to a variable before its declaration
		// System.out.println("inner = " + inner); // Uncomment this for an error
		int inner = 2;
		System.out.println("inner = " + inner); // Now it is OK
		System.out.println("outer = " + outer); // and outer is still here
		// All variables defined in the enclosing outer block still exist,
		// so you cannot redefine them here
		// int outer = 5; // Uncomment this for an error
		}

		// Any variables declared in the previous inner block no longer exist
		// so you cannot refer to them
		// System.out.println("inner = " + inner); // Uncomment this for an error > not defined
		// The previous variable, inner, does not exist so you can define a new one
		int inner = 3;
		System.out.println("inner = " + inner); // ... and output its value
		System.out.println("outer = " + outer); // outer is still around
		
	}
	
	public static void numForLoop() {
		int limit = 20; // Sum from 1 to this value
		int sum = 0; // Accumulate sum in this variable
		
		// Loop from 1 to the value of limit, adding 1 each cycle
		//for (initialization_expression ; loop_condition ; increment_expression)
		for(int i = 1; i <= limit; i++) {
		sum += i; // Add the current value of i to sum
		}

		System.out.println("sum = " + sum);
	}
	
	
		
//Final		
}
		

