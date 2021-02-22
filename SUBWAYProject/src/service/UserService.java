package service;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
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
	
	private UserDao userDao = UserDao.getInstance();
	
	public int join(){
		System.out.println("=========== 회원가입 =============");
		System.out.print("아이디>");
		String userId = ScanUtil.nextLine();
		System.out.print("비밀번호>");
		String password = ScanUtil.nextLine();
		System.out.print("이름>");
		String userName = ScanUtil.nextLine();
		//아이디 중복 확인 생략
		//비밀번호 확인 생략
		//정규표현식(유효성 검사) 생략
		
		Map<String, Object> param = new HashMap<>();
		param.put("USER_ID", userId);
		param.put("PASSWORD", password);
		param.put("USER_NAME", userName);
		
		int result = userDao.insertUser(param);
		
		if(0 < result){
			System.out.println("회원가입 성공");
		}else{
			System.out.println("회원가입 실패");
		}
		
		return View.HOME;
	}

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
			System.out.println("로그인 타입 입력 > 1. 고객\t2. 가맹점\t3. 관리자");
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