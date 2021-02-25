package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifiedUtil {
	private VerifiedUtil () {
		
	}
	
	private static VerifiedUtil instance;
	
	public static VerifiedUtil getInstance() {
		if(instance == null) {
			instance = new VerifiedUtil();
		}
		return instance;
	}
	
	//메뉴 유효성 검사 (switch 문을 쓰지 않은경우)
	public boolean verifiedMenu(int length, int userInput) {//메뉴길이(이때 0은 쓰면 안됨)/유저입력번호
		boolean flag = false;
		if(0 < userInput && userInput <= length) {
			flag = true;
		}	
		return flag;
	}
	



//	아이디 정규식	
	public boolean verifiedId(String userId) {
		String regex1 = "[a-zA-Z0-9-_]{5,20}+";//아이디 정규식 : 특수문자 -_만
		Pattern p = Pattern.compile (regex1);
		Matcher m = p.matcher (userId);
		return m.matches();		
	}
	
//	비밀번호 정규식
	public boolean verifiedNumber(String userNumber) {
		String regex1 = "^\\d{3}-\\d{3,4}-\\d{4}$";//전화번호 정규식
		Pattern p = Pattern.compile (regex1);
		Matcher m = p.matcher (userNumber);
		return m.matches();
	}
	
//	이메일 정규식
	public boolean verifiedEmail(String userEmail) {
		String regex1 = "^[0-9a-zA-Z]([-_]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";//이메일 정규식
		Pattern p = Pattern.compile (regex1);
		Matcher m = p.matcher (userEmail);
		return m.matches();
	}
	
	public boolean verifiedDate(String userDate) {
		if(!userDate.equals("")) {
			String regex1 = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";//날짜 정규식 
			Pattern p = Pattern.compile (regex1);
			Matcher m = p.matcher (userDate);
			return m.matches();
		}else {
			return false;
		}
	}
	
	public boolean verifiedRegNo(String memREGNO) {
		if(!memREGNO.equals("")) {
			String regex1 = "([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))";//생년월일 정규식
			Pattern p = Pattern.compile (regex1);
			Matcher m = p.matcher (memREGNO);
			return m.matches();
		}else {
			return false;
		}
	}
	
}
