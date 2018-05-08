package wroxJava2;


//Identifiers: Identifiers, args, num, _Hola, $dollar

public class Identifiers {

	private static String hello;
	//int static = 4 //invalid; keyword used
	//private static String 4hello; //invalid; starting with number
	int String = 10; //valid, but not recommended
	int Object = 20; //valid, but not recommended
	//int baseball@home = 1; //invalid; @ not allowd
	
	public static void main(String[] args) {
		 hello = "helo";
		 int num = 10; //valid identifier
		 System.out.println(num);
		 
		 String _Hola = "hola identifier starting with underscore"; //valid identifier
		 System.out.println(_Hola);
		 
		 String $dollar = "$ dollar"; //valid identifier
		 System.out.println($dollar);
		 
		 String user_name = "user_name";
		 String User_name = "User_name";
		 System.out.println(user_name + ' ' + User_name);
	}

}
