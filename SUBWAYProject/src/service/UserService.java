package service;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.VerifiedUtil;
import util.View;
import dao.UserDao;

public class UserService {
// 유저 서비스 
	private UserService(){}
	private static UserService instance;
	public static UserService getInstance(){
		if(instance == null){
			instance = new UserService();
		}
		return instance;
	}
//	userDao 객체 획득(SQL , PARAM)작성 위함
	private UserDao userDao = UserDao.getInstance();
	private VerifiedUtil verifiedUtil = VerifiedUtil.getInstance();
	
//	회원가입 하는 메서드
	public int join(){
		System.out.println("=========== 회원가입 =============");
		System.out.println("회원가입 타입 입력 [1. 고객\t2. 가맹점]");
		String table = "오류";
//		userDao 로 넘겨주기 위한 회원가입 데이터 세팅
		Map<String, Object> param = new HashMap<>();
		while(table.equals("오류")) {
			System.out.print("입력>");
			int loginType = ScanUtil.nextInt();
			switch(loginType) {
//			로그인 타입이 고객일 때, 로그인 코드, 유저 테이블명, 유저 아이디, 유저 패스워드 저장
				case 1: 
					table = "MEMBER";
					String memID ="";
					boolean userFlag = true;
					while(userFlag) {
						System.out.print("고객 아이디>");
						memID = ScanUtil.nextLine();
						boolean userIdFlag = verifiedUtil.verifiedId(memID);
						if(userIdFlag) {
							System.out.println("아이디 검사 : 정확하게 입력하였습니다.");
							break;
						}else {
							System.out.println("잘못 입력하였습니다. 아이디 형식[영문 : 대소문자 / 특수문자 : '-', '_'만 가능 / 길이 : 5~20자]");
						}
					}
					
					System.out.print("고객 비밀번호>");
					String memPW = ScanUtil.nextLine();
					System.out.print("고객 이름>");
					String memNM = ScanUtil.nextLine();
					System.out.print("고객 생년월일(ex.93/12/06)>");
					String memREGNO = ScanUtil.nextLine();
					
					String memNumber = "";
					while(userFlag) {
						System.out.print("고객 회원 전화 번호(ex.000-0000-0000)>");
						memNumber = ScanUtil.nextLine();
						boolean userNumberFlag = verifiedUtil.verifiedNumber(memNumber);
						if(userNumberFlag) {
							System.out.println("전화번호 검사 : 정확하게 입력하였습니다. ");
							break;
						}else {
							System.out.println("잘못 입력하였습니다. 형식에 맞추어 입력해주세요.");
						}
					}
					
					System.out.print("고객 회원 우편 번호(ex.0000-0000)>");
					String memZIP = ScanUtil.nextLine();
					System.out.print("고객 회원 주소(ex. 대전시 중구 선화동 199-23 대덕아파트 2동 1023호)");
					String memADD = ScanUtil.nextLine();
					
					param.put("MEM_ID", memID);
					param.put("MEM_PW", memPW);
					param.put("MEM_NM", memNM);
					param.put("MEM_REGNO", memREGNO);
					param.put("MEM_NUMBER", memNumber);
					param.put("MEM_ZIP", memZIP);
					param.put("MEM_ADD", memADD);
					break;
				case 2: 
					table = "BUYER"; 
					String buyerID ="";
					boolean userFlag1 = true;
					while(userFlag1) {
						System.out.print("가맹점 아이디>");
						buyerID = ScanUtil.nextLine();
						boolean userIdFlag = verifiedUtil.verifiedId(buyerID);
						if(userIdFlag) {
							System.out.println("아이디 검사 : 정확하게 입력하였습니다.");
							break;
						}else {
							System.out.println("잘못 입력하였습니다. 아이디 형식[영문 : 대소문자 / 특수문자 : '-', '_'만 가능 / 길이 : 5~20자]");
						}
					}
					
					System.out.print("가맹점 비밀번호>");
					String buyerPW = ScanUtil.nextLine();
					System.out.print("가맹점 이름>");
					String buyerNAME = ScanUtil.nextLine();
					
					String buyerComtel ="";
					while(userFlag1) {
						System.out.print("가맹점 전화번호(ex.000-0000-0000)>");
						buyerComtel = ScanUtil.nextLine();
						boolean userNumberFlag = verifiedUtil.verifiedNumber(buyerComtel);
						if(userNumberFlag) {
							System.out.println("전화번호 검사 : 정확하게 입력하였습니다. ");
							break;
						}else {
							System.out.println("잘못 입력하였습니다. 형식에 맞추어 입력해주세요.");
						}
					}
					
					System.out.print("가맹점 우편 번호(ex.0000-0000)>");
					String buyerZip = ScanUtil.nextLine();
					System.out.print("가맹점 주소(ex. 대전시 중구 선화동 199-23 1층)>");
					String buyerAdd = ScanUtil.nextLine();
					
					param.put("BUYER_ID", buyerID);
					param.put("BUYER_PW", buyerPW);
					param.put("BUYER_NAME", buyerNAME);
					param.put("BUYER_COMTEL", buyerComtel);
					param.put("BUYER_ZIP", buyerZip);
					param.put("BUYER_ADD", buyerAdd);
					
					break;
				default:
					System.out.println("다시입력"); 
					table = "오류"; 
					break;
			}
		}
		
		
		
		//아이디 중복 확인 생략
		//비밀번호 확인 생략
		//정규표현식(유효성 검사) 생략
		
		
		
		
		
		int result = userDao.insertUser(param,table);
		
		if(0 < result){
			System.out.println("회원가입 성공");
		}else{
			System.out.println("회원가입 실패");
		}
		
		return View.HOME;
	}

//	로그인 하는 메서드
	public int login() {
		System.out.println("========== 로그인 (대소문자 구분)=============");
		System.out.print("아이디>");
		String userId = ScanUtil.nextLine();
		System.out.print("비밀번호>");
		String password = ScanUtil.nextLine();
		String table = "오류";
		String idColumn = "MEM_ID";
		String pwColumn = "MEM_PW";
		int loginCode = 1;
//		로그인 타입 별 작업
		while(table.equals("오류")) {
			System.out.println("로그인 타입 입력 [1. 고객\t2. 가맹점\t3. 관리자]");
			System.out.print("입력>");
			int loginType = ScanUtil.nextInt();
			switch(loginType) {
//			로그인 타입이 고객일 때, 로그인 코드, 유저 테이블명, 유저 아이디, 유저 패스워드 저장
				case 1: table = "MEMBER"; idColumn = "MEM_ID"; pwColumn="MEM_PW"; loginCode=1; break;
				case 2: table = "BUYER"; idColumn = "BUYER_ID"; pwColumn="BUYER_PW"; loginCode=2; break;
				case 3: table = "MANAGER"; idColumn = "MANAGER_ID"; pwColumn="MANAGER_PW"; loginCode=3; break;
				default:
					System.out.println("다시입력"); table = "오류"; break;
			}
		}
		
		Map<String, Object> user = userDao.selectUser(userId, password, table, idColumn, pwColumn);
//		유저 로그인 타입에 따라 로그인 정보에 유저 로그인 코드와 해당하는 유저 테이블 명을 저장
//		로그인 시 로그인 된 유저가 존재하는 경우에만 로그인 테이블과 로그인 코드를 저장
		if(user == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인 성공");
			if(table.equals("MEMBER")) {
				user.put("LOGIN_TABLE", table);
				user.put("LOGIN_CODE", loginCode);
			}else if(table.equals("BUYER")) {
				user.put("LOGIN_TABLE", table);
				user.put("LOGIN_CODE", loginCode);
			}else if(table.equals("MANAGER")) {
				user.put("LOGIN_TABLE", table);
				user.put("LOGIN_CODE", loginCode);
			}else {
				System.out.println("정보없음");
			}
			Controller.loginUser = user;
			return View.LOGIN_MAIN_MENU;
		}
		
		return View.HOME;
	}
	
}