package controller;

import java.util.Map;

import service.BoardService;
import service.EventService;
import service.FrancService;
import service.LoginMainMenuService;
import service.MenuService;
import service.MyService;
import service.NotiService;
import service.OrderService;
import service.UserService;
import util.ScanUtil;
import util.View;

public class Controller {

	public static void main(String[] args) {
		/*
		 * 발표순서 : 조 소개 > 주제 소개 > 주제 선정 이유 > 메뉴 구조 > 시연
		 * 발표인원 : 발표자 1명, ppt 및 시연 도우미 1명
		 * 
		 * Controller : 화면 이동
		 * Service : 화면 기능
		 * Dao(Data Access Object) : 쿼리작성
		 */
		
		new Controller().start();
	}
	
	public static Map<String, Object> loginUser;
	
	private UserService userService = UserService.getInstance();
	private BoardService boardService = BoardService.getInstance();
	private LoginMainMenuService loginMainMenuService = LoginMainMenuService.getInstance();
	private MenuService menuService = MenuService.getInstance();
	private NotiService noticeService = NotiService.getInstance();
	private EventService eventService = EventService.getInstance();
	private OrderService orderService = OrderService.getInstance();
	private MyService myPageService = MyService.getInstance();
	private FrancService francService = FrancService.getInstance();

	private void start() {
		int view = View.HOME;
		
		while(true){
			switch (view) {
				case View.HOME: view = home(); break;
				case View.LOGIN: view = userService.login(); break;
				case View.JOIN: view = userService.join(); break;
				case View.LOGIN_MAIN_MENU: view = loginMainMenuService.loginMainHome(); break;
				case View.MENU: view = menuService.menuselect();break;
				case View.MENU_MANA: view = menuService.menumanage();break;
				case View.NOTICE_LIST: view = noticeService.noticeHome(); break;
				case View.EVENT_LIST: view = eventService.eventList(); break;
				case View.ORDER_MEMBER_MENU: view = orderService.orderMemberHome(); break;
				case View.ORDER_BUYER_MENU: view = orderService.orderBuyerHome(); break;
				case View.MYPAGE_MENU: view = myPageService.myPageHome(); break;
				case View.FRANC_LIST: view = francService.francList(); break;
				case View.NOTICE_LIST_INFO: view = noticeService.notiInfo(); break;
				case View.EVENT_LIST_INFO: view = eventService.eventInfo(); break;
				
				case View.BOARD_LIST: view = boardService.boardList(); break;
			}
		}
	}

	private int home() {
		System.out.println("------------------------------------------------");
		System.out.println("1.로그인 \t 2.회원가입 \t 3.1:1 문의 \t 4.종료");
		System.out.println("------------------------------------------------");
		System.out.print("번호 입력>");
		
		int input = ScanUtil.nextInt();
		
		switch (input) {
			case 1: return View.LOGIN;
			case 2: return View.JOIN;
			case 3: return View.MYPAGE_MENU;
			case 4:
				System.out.println("프로그램이 종료되었습니다.");
				System.exit(0);
			default:
				System.out.println("잘못입력하셨습니다.");
		}
		return View.HOME;
	}
	
	

}