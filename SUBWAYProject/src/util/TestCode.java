package util;

public class TestCode {
//	유효성 검사 서비스 객체 생성
	private VerifiedUtil verifiedUtil = VerifiedUtil.getInstance ();
	
	public static void main (String[] args) {
		new TestCode().start();
	}
	
	private void start() {
		System.out.println ("회원가입 메서드 호출");
		join();
		join2();
	}
	
	//회원 가입 시 메뉴 유효성 검사(switch 문을 안쓰는 경우)
	private void join() {
		System.out.println ("==회원가입1==");
		System.out.print ("회원명 > ");
		String userName = ScanUtil.nextLine ();
		int userCodeInput;
		
		while(true) {
			System.out.println ("회원 코드 선택 : 1. 고객\t2. 가맹점주");
			userCodeInput = ScanUtil.nextInt ();//입력한 내용이 숫자인지 문자인지 판별
			if(userCodeInput != ScanUtil.ERROR) {
				boolean flag = verifiedUtil.verifiedMenu (2, userCodeInput);//입력한 내용이 메뉴에 맞추어 입력하였는지 판별
				if(flag) {
					System.out.println ("메뉴에 맞게 정상적으로 입력하셨습니다.");
					break;
				}else {
					System.out.println ("정상적인 메뉴를 입력하지 않음.");
				}
			}else {
				System.out.println ("숫자가 아님 재입력");
			}
		}
	}
	
	
	
	//회원 가입 시 메뉴 유효성 검사(switch 문을 쓰는 경우)
		private void join2() {
			System.out.println ("==회원가입2==");
			System.out.print ("회원명 > ");
			String userName = ScanUtil.nextLine ();
			int userCodeInput;
			
			while(true) {
				System.out.println ("회원 코드 선택 : 1. 고객\t2. 가맹점주");
				userCodeInput = ScanUtil.nextInt ();//입력한 내용이 숫자인지 문자인지 판별
				if(userCodeInput != ScanUtil.ERROR) {
					switch(userCodeInput) {
						case 1:
							System.out.println ("고객입니다.");
							break;
						case 2:
							System.out.println ("가맹점주입니다.");
							break;
						default :
							System.out.println ("잘못입력하셨습니다.");
					}
				}else {
					System.out.println ("숫자가 아님 재입력");
				}
			}
		}
		
		
	//회원 가입 시 아이디 정규식 검사
		String regex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
		
}
