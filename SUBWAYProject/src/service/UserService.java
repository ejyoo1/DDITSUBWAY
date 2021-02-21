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
		while(table.equals("오류")) {
			System.out.println("로그인 타입 입력 > 1. 고객\t2. 가맹점\t3. 관리자");
			int loginType = ScanUtil.nextInt();
			switch(loginType) {
				case 1: table = "MEMBER"; idColumn = "MEM_ID"; pwColumn="MEM_PW"; break;
				case 2: table = "BUYER"; idColumn = "BUYER_ID"; pwColumn="BUYER_PW"; break;
				case 3: table = "MANAGER"; idColumn = "MANAGER_ID"; pwColumn="MANAGER_PW"; break;
				default:
					System.out.println("다시입력"); table = "오류"; break;
			}
		}
		
		Map<String, Object> user = userDao.selectUser(userId, password, table, idColumn, pwColumn);
		
		if(user == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인 성공");
			Controller.loginUser = user;
			return View.BOARD_LIST;
		}
		
		return View.HOME;
	}
	
}