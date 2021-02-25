package service;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.VerifiedUtil;
import util.View;
import dao.UserDao;

public class UserService {
	private UserService(){}
	private static UserService instance;
	public static UserService getInstance(){
		if(instance == null){
			instance = new UserService();
		}
		return instance;
	}
	
	private UserDao userDao = UserDao.getInstance();
	private VerifiedUtil verifiedUtil = VerifiedUtil.getInstance();
	
//	회원가입 메서드
	public int join(){
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■회원가입■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("☞ 회원가입 타입 입력 [1.고객     2.가맹점] ☜");
		String table = "오류";
//		userDao 로 넘겨주기 위한 회원가입 데이터 세팅
		Map<String, Object> param = new HashMap<>();
		while(table.equals("오류")) {
			System.out.print("☞ 입력 > ");
			int loginType = ScanUtil.nextInt();
			switch(loginType) {
//			회원가입 타입 : 고객
				case 1: 
					table = "MEMBER";
					String idColumn = "MEM_ID";
					String memID ="";
					boolean userFlag = true;
//					고객 아이디 유효성 검사
					while(userFlag) {
						System.out.print("☞ 고객 아이디 > ");
						memID = ScanUtil.nextLine();
//						DAO 호출 
						Map<String, Object> result = userDao.selectUser(memID, table, idColumn);
//						결과가 있으면 아이디 유효성 검사
						if(result == null){
							boolean userIdFlag = verifiedUtil.verifiedId(memID);
							if(userIdFlag) {
								System.out.println("☞ 아이디 검사 : 성공 ☜" );
								break;
							}else {
								System.out.println("☞ 아이디 검사 : 실패 ☜");
								System.out.println("형식[영문 : 대소문자 / 특수문자 : '-', '_'만 가능 / 길이 : 5~20자]");
							}
//						결과가 없으면 재입력
						}else {
							System.out.println("☞ 이미 사용중인 아이디입니다 ☜");
						}
					}
					
					System.out.print("☞ 고객 비밀번호 > ");
					String memPW = ScanUtil.nextLine();
					System.out.print("☞ 고객 이름 > ");
					String memNM = ScanUtil.nextLine();
					
//					생년월일 유효성 검사
					String memREGNO = "";
					while(userFlag) {
						System.out.print("☞ 고객 생년월일(ex.931206) > ");
						memREGNO = ScanUtil.nextLine();
						boolean userRegNoFlag = verifiedUtil.verifiedRegNo(memREGNO);
						if(userRegNoFlag) {
							System.out.println("☞ 생년월일 검사 : 성공 ☜");
							break;
						}else {
							System.out.println("☞ 생년월일 검사 : 실패 ☜");
							System.out.println("올바른 형식이 아닙니다.");
						}
					}
					
//					회원 전화번호 유효성 검사
					String memNumber = "";
					while(userFlag) {
						System.out.print("☞ 고객 회원 전화 번호(ex.000-0000-0000) > ");
						memNumber = ScanUtil.nextLine();
						boolean userNumberFlag = verifiedUtil.verifiedNumber(memNumber);
						if(userNumberFlag) {
							System.out.println("☞ 전화번호 검사 : 성공 ☜");
							break;
						}else {
							System.out.println("☞ 전화번호 검사 : 실패 ☜");
							System.out.println("올바른 형식이 아닙니다.");
						}
					}
					
					System.out.print("☞ 고객 회원 우편 번호(ex.0000-0000) > ");
					String memZIP = ScanUtil.nextLine();
					System.out.print("☞ 고객 회원 주소(ex. 대전시 중구 선화동 199-23 대덕아파트 2동 1023호) > ");
					String memADD = ScanUtil.nextLine();
					
//					입력값 세팅
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
					idColumn = "BUYER_ID";
					String buyerID ="";
					boolean userFlag1 = true;
//					고객 아이디 유효성 검사
					while(userFlag1) {
						System.out.print("☞ 가맹점 아이디 > ");
						buyerID = ScanUtil.nextLine();

//						DAO 호출 
						Map<String, Object> result = userDao.selectUser(buyerID, table, idColumn);
//						결과가 있으면 아이디 유효성 검사
						if(result == null){
							boolean userIdFlag = verifiedUtil.verifiedId(buyerID);
							if(userIdFlag) {
								System.out.println("☞ 아이디 검사 : 성공 ☜" );
								break;
							}else {
								System.out.println("☞ 아이디 검사 : 실패 ☜");
								System.out.println("형식[영문 : 대소문자 / 특수문자 : '-', '_'만 가능 / 길이 : 5~20자]");
							}
//							결과가 없으면 재입력
							}else {
								System.out.println("☞ 이미 사용중인 아이디입니다 ☜");
							}
					}
					
					System.out.print("☞ 가맹점 비밀번호 > ");
					String buyerPW = ScanUtil.nextLine();
					System.out.print("☞ 가맹점 이름 > ");
					String buyerNAME = ScanUtil.nextLine();
					
					String buyerComtel ="";
					while(userFlag1) {
						System.out.print("☞ 가맹점 전화번호(ex.000-0000-0000) > ");
						buyerComtel = ScanUtil.nextLine();
						boolean userNumberFlag = verifiedUtil.verifiedNumber(buyerComtel);
						if(userNumberFlag) {
							System.out.println("☞ 전화번호 검사 : 성공 ☜");
							break;
						}else {
							System.out.println("☞ 전화번호 검사 : 실패 ☜");
							System.out.println("올바른 형식이 아닙니다.");
						}
					}
					
					System.out.print("☞ 가맹점 우편 번호(ex.0000-0000) > ");
					String buyerZip = ScanUtil.nextLine();
					System.out.print("☞ 가맹점 주소(ex. 대전시 중구 선화동 199-23 1층) > ");
					String buyerAdd = ScanUtil.nextLine();
					
//					입력값 세팅
					param.put("BUYER_ID", buyerID);
					param.put("BUYER_PW", buyerPW);
					param.put("BUYER_NAME", buyerNAME);
					param.put("BUYER_COMTEL", buyerComtel);
					param.put("BUYER_ZIP", buyerZip);
					param.put("BUYER_ADD", buyerAdd);
					break;
					
//				잘못 입력한 경우
				default:
					System.out.println("☞ 잘못된 번호입니다 ☜");
					table = "오류"; 
					break;
			}
		}
		
//		DAO 호출하여 회원가입 결과 가져옴
		int result = userDao.insertUser(param,table);
		
//		회원 가입 결과 출력
		if(0 < result){
			System.out.println("☞ 회원가입 성공(로그인/회원가입/1:1문의 화면으로 이동합니다.) ☜");
		}else{
			System.out.println("☞ 회원가입 실패(로그인/회원가입/1:1문의 화면으로 이동합니다.) ☜");
		}
		
		return View.HOME;
	}

//	로그인 하는 메서드
	public int login() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■로그인■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print("☞ 아이디(대소문자 구분) > ");
		String userId = ScanUtil.nextLine();
		System.out.print("☞ 비밀번호(대소문자 구분) > ");
		String password = ScanUtil.nextLine();
		String table = "오류";
		String idColumn = "MEM_ID";
		String pwColumn = "MEM_PW";
		int loginCode = 1;
		
//		로그인 타입 별 작업
		while(table.equals("오류")) {
			System.out.println("로그인 타입 입력 [1.고객     2.가맹점     3.관리자]");
			System.out.print("☞ 입력 > ");
			int loginType = ScanUtil.nextInt();
			switch(loginType) {
//			로그인 타입이 고객일 때, 로그인 코드, 유저 테이블명, 유저 아이디, 유저 패스워드 저장
				case 1: table = "MEMBER"; idColumn = "MEM_ID"; pwColumn="MEM_PW"; loginCode=1; break;
				case 2: table = "BUYER"; idColumn = "BUYER_ID"; pwColumn="BUYER_PW"; loginCode=2; break;
				case 3: table = "MANAGER"; idColumn = "MANAGER_ID"; pwColumn="MANAGER_PW"; loginCode=3; break;
				default:
					System.out.println("☞ 잘못된 번호입니다 ☜"); 
					table = "오류"; 
					break;
			}
		}
		
		Map<String, Object> user = userDao.selectUser(userId, password, table, idColumn, pwColumn);
//		유저 로그인 타입에 따라 로그인 정보에 유저 로그인 코드와 해당하는 유저 테이블 명을 저장
//		로그인 시 로그인 된 유저가 존재하는 경우에만 로그인 테이블과 로그인 코드를 저장
		if(user == null){
			System.out.println("☞ 로그인 정보를 찾을 수 없습니다. 다시 시도 하십시오. ☜");
		}else{
			if(table.equals("MEMBER")) {
				System.out.println("☞ " + user.get("MEM_NM") + " 님 환영합니다! ☜");
				user.put("LOGIN_TABLE", table);
				user.put("LOGIN_CODE", loginCode);
			}else if(table.equals("BUYER")) {
				System.out.println("☞ " + user.get("BUYER_NAME") + " 님 환영합니다! ☜");
				user.put("LOGIN_TABLE", table);
				user.put("LOGIN_CODE", loginCode);
			}else if(table.equals("MANAGER")) {
				System.out.println("☞ 관리자로 로그인되었습니다. ☜");
				user.put("LOGIN_TABLE", table);
				user.put("LOGIN_CODE", loginCode);
			}
//			로그인 성공 시 로그인 정보를 저장하고 로그인 메인 메뉴로 이동
			Controller.loginUser = user;
			return View.LOGIN_MAIN_MENU;
		}
//		로그인 실패 시 로그인/회원가입/1:1문의 화면으로 이동
		return View.HOME;
	}
	
}