package wroxJava2;

public class TryLoops {

	enum Season { spring, summer, fall, winter };
	
	public static void main(String[] args) {

		int limit = 20;
		int sum = 0;
		int sum2 = 0;
		int sum3 = 0;
		int sum4 = 0;
		int sumP = 0;
		int x = 0;
		int y = 0;
		int p = 0;
		
		//for 
		for (int i=1, j=0 ; i <= limit; i++, j++) {
			sum += i; //sum = sum + i
			sum2 += j;
		}

		System.out.println(sum);
		System.out.println(sum2);

		//Enum - collection
		for (Season season : Season.values()) { //iterate over all values
			System.out.println("The season now is : " + season);
		}
		
		//while
		while(x <= limit) {
			sum3 += x++;
			System.out.println("sum3 = " + sum3);
		}
		
		//do .. while
		do {
			sum4 += y;
			y++;
		} while (y <= limit);
		
		System.out.println("sum4 = " + sum4);
		
		
		//For...continue
		for (p = 1; p <= limit ; p++  ) {
			//p deelbaar door 3
			if ( p % 3== 0) {
				continue;
			}
			sumP = p; 
			System.out.println("sumP = " + sumP);
		}
	}

}
