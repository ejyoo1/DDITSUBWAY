package service;

import controller.Controller;
import util.ScanUtil;
import util.View;

public class LoginMainMenuService {
	private LoginMainMenuService(){}
	private static LoginMainMenuService instance;
	public static LoginMainMenuService getInstance(){
		if(instance == null){
			instance = new LoginMainMenuService();
		}
		return instance;
	}
//	로그인 성공 시 메뉴 출력되는 메서드
	public int loginMainHome() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■메인 메뉴■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
//		로그인 코드 별로 메뉴 출력
		if(Controller.loginUser.get("LOGIN_CODE").equals(1)) {
			System.out.println("1.메뉴소개     2.공지사항     3.이벤트     4.주문     5.마이페이지     6.로그아웃");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞ 입력 > ");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: return View.MENU;
				case 2: return View.NOTICE_LIST;
				case 3: return View.EVENT_LIST;
				case 4: return View.ORDER_MEMBER_MENU;
				case 5: return View.MYPAGE_MENU;
				case 6: 
					System.out.println("☞ " + Controller.loginUser.get("MEM_ID") + " 님. 로그아웃 되었습니다. ☜");
					Controller.loginUser = null; 
					EventService.eventListCode = 1; 
					return View.HOME;
				default : System.out.println("☞ 잘못된 번호입니다 ☜");
			}
		}else if(Controller.loginUser.get("LOGIN_CODE").equals(2)) {
			System.out.println("1.메뉴소개     2.공지사항     3.이벤트     4.주문     5.마이페이지     6.로그아웃");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞ 입력 > ");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: return View.MENU;
				case 2: return View.NOTICE_LIST;
				case 3: return View.EVENT_LIST;
				case 4: return View.ORDER_BUYER_MENU;
				case 5: return View.MYPAGE_MENU;
				case 6: 
					System.out.println("☞ " + Controller.loginUser.get("BUYER_ID") + " 님. 로그아웃 되었습니다. ☜");
					Controller.loginUser = null; 
					EventService.eventListCode = 1; 
					return View.HOME;
				default : System.out.println("☞ 잘못된 번호입니다 ☜");
			}
		}else if(Controller.loginUser.get("LOGIN_CODE").equals(3)) {
			System.out.println("1.메뉴 관리     2.공지 관리     3.이벤트 관리     4.가맹점 관리     5.고객 센터 관리     6.로그아웃");
			System.out.print("☞ 입력 > ");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: return View.MENU_MANA;
				case 2: return View.NOTICE_LIST;
				case 3: return View.EVENT_LIST;
				case 4: return View.FRANC_LIST;
				case 5: return View.MYPAGE_MENU;
				case 6: 
					System.out.println("☞ " + Controller.loginUser.get("MANAGER_ID") + " 님. 로그아웃 되었습니다. ☜");
					Controller.loginUser = null; 
					EventService.eventListCode = 1; 
					return View.HOME;
				default : System.out.println("☞ 잘못된 번호입니다 ☜");
			}
		}else {
			System.out.println("☞ 로그인 정보가 없습니다. ☜");
		}
		
		
		return View.LOGIN_MAIN_MENU;
	}
}
