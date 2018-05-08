package wroxJava2;

class Test {
	static void main1(String Gino[]) {
		System.out.println("run Test-main1");
	}
}

//arithmetic operation
class ArOps {
	 public int add(int x, int y) {
		int sum = x + y;
		System.out.println("sum x+y = " + sum);
		return sum;
	}
	 public void sub(int x, int y) {
		int sub = x - y;
		System.out.println("sub x - y = " + sub);
		//return sum;
	}

}


public class JVMTest {

	public static void main(String[] args) {
		System.out.println("args length = " + args.length);
		
		//if not static class, then you need to create a new object
		// Test t = new Test();
		Test.main1(args);
		
		//call directly
		//Add.add(1, 2);
		
		//create new object, so static not needed for the Add class
		ArOps a = new ArOps();
		int result = a.add(4, 5);
		
		ArOps b = new ArOps();
		b.sub(6, 5);
		
		System.out.println("result = " + result);

	}

}
