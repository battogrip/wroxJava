package wroxJava2;

import org.omg.CORBA.ARG_OUT;

class Test2 {
	static void main1(String Gino[]) {
		System.out.println("run Test-main1");
	}
}

//arithmetic operation
class ArOps2 {
	 public int add(int x, int y) {
		int sum = x + y;
		System.out.println("sum x+y = " + sum);
		return sum;
	}
	 
	 //example method overloading; same method but with diff parameter types
	 public String add(String str1, String str2) {
		String result = str1 + str2; 
		System.out.println("sum str1 and 2= " + result);
		return result;
		
	}
	 
	 public void sub(int x, int y) {
		int sub = x - y;
		System.out.println("sub x - y = " + sub);
		//return sum;
	}

	 public static void sub(String str1, String str2) {
		String resultStr = str1 + str2;
		System.out.println("resultStr " + resultStr);
		//return sum;
	}
	 
}


public class JVMTest2 {

	//java wroxJava2.JVMTest2 10 20
	public static void main(String args[]) {
		System.out.println("args length = " + args.length);
		String str1 = args[0];
		String str2 = args[1];
		
		int num1 = Integer.parseInt(str1);
		int num2 = Integer.parseInt(str2);
		
		//call directly
		//Add.add(1, 2);
		
		//create new object, so static not needed for the Add class
		ArOps2 a = new ArOps2();
		int result = a.add(num1, num2);
		
		ArOps2 z = new ArOps2();
		String resultStr = z.add("Hello ", "Gino");
		System.out.println(resultStr);
		
		ArOps2 b = new ArOps2();
		b.sub(num1, num2);
		
		
		
		System.out.println("result = " + result);

	}

}
