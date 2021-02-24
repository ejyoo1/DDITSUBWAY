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
//		유저 입력 시 빈 값 입력하지 않은 경우(값을 입력한 경우)
		if(!input.equals("")) {
			for(int i = 0 ; i < input.length() ; i++) {
				tmp = input.charAt (i);
				
				if(Character.isDigit (tmp) == false) { //입력한 문자가 숫자가 아닌경우
					output = false;
				}
			}
			if(output) {
				return Integer.parseInt (input);
			}
//			유저가 아무것도 입력하지 않은경우
		}else {
//			유저 입력이 없는 경우 처리하지 않고 에러코드 부여
		}
		//false 일때
		return ERROR;
	}
	
}
