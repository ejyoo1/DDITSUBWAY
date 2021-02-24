package util;


import java.util.Scanner;

public class ScanUtil {
	public static final int ERROR = 9999 ;
	
	
	static Scanner s = new Scanner(System.in);
	
	public static String nextLine() {
		while(true) {
			String input = s.nextLine();
			if(!input.equals("")) {
				return input;
			}else {
				System.out.print(">");
			}
		}
//		return s.nextLine();
	}
	
	public static int nextInt() {
		while(true) {
			String input = new ScanUtil().s.nextLine ();
			if(!input.equals("")) {
				return Integer.parseInt(input);
			}else {
				System.out.print(">");
			}
		}
	}
}
