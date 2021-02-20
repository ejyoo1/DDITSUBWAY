package util;

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
	
	//아이디 정규화
	//이메일 정규화
	//비밀번호 정규화
	
}
