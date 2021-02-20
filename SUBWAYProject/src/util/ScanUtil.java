package util;


import java.util.Scanner;

public class ScanUtil {
	public static final int ERROR = 9999 ;
	
	
	static Scanner s = new Scanner(System.in);
	
	public static String nextLine() {
		return s.nextLine();
		//return new ScanUtil.s.nextLine();
	}
	
	public static int nextInt() {
		String input = new ScanUtil().s.nextLine ();
		boolean output = true;
		char tmp;
		for(int i = 0 ; i < input.length() ; i++) {
			tmp = input.charAt (i);
			
			if(Character.isDigit (tmp) == false) { //입력한 문자가 숫자가 아닌경우
				output = false;
			}
		}
		if(output) {
			return Integer.parseInt (input);
		}
		//false 일때
		return ERROR;
	}
	
}
