package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.MyDao;

public class MyService {
	// 마이페이지 서비스
	private MyService() {
	}

	private static MyService instance;

	public static MyService getInstance() {
		if (instance == null) {
			instance = new MyService();
		}
		return instance;
	}

	private MyDao myDao = MyDao.getInstance();

//  마이페이지 메인 홈 화면
	public int myPageHome() { 
//		*로그인 타입 : 고객, 가맹점주, 관리자 마이페이지 메뉴 출력
		// 고객 마이페이지
		if (Controller.loginUser.get("LOGIN_CODE").equals(1)) { // 고객 마이페이지
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■마이페이지■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1.내정보 수정     2.주문 내역 확인     3.1:1 문의 작성     4.이전으로(메인 메뉴 페이지 이동)");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞ 입력 > ");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: userModifyInfo();	break;  // 내정보수정
			case 2: userOrderList();	break;  // 주문내역조회
			case 3: return View.INQUIRY_MENU;  // 1대1문의
			case 4: return View.LOGIN_MAIN_MENU;
			default: System.out.println("잘못입력"); break;
			} //CLOSE FOR
		
		// 가맹점주 마이페이지	
		} else if (Controller.loginUser.get("LOGIN_CODE").equals(2)) { // 가맹점 마이페이지
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■마이페이지■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("1.내정보 수정     2. 주문 내역 확인     3. 1:1 문의 작성     4.이전으로");
			System.out.print("☞ 입력>");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: storeModifyInfo(); break; // 내정보수정
			case 2: storeOrderList();  break; // 주문내역조회
			case 3: return View.INQUIRY_MENU; // 1대1문의
			case 4: return View.LOGIN_MAIN_MENU;
			default: System.out.println("잘못입력"); break;
			}//CLOSE FOR
			
		// 관리자 마이페이지	
		} else if (Controller.loginUser.get("LOGIN_CODE").equals(3)) { // 관리자 고객센터 관리
			managerInquiry();
			return View.LOGIN_MAIN_MENU;
		}//CLOSE FOR
		
		return View.MYPAGE_MENU;
	}

//  고객 정보수정 페이지
	public int userModifyInfo() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■내정보 수정■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("비밀번호를 입력해 주세요 :");
		System.out.print("☞ 입력 > ");
		String password = ScanUtil.nextLine();                 
		
		// 비밀번호 확인절차
		Map<String, Object> user = myDao.selectUser(password);  
		if (user == null) {
			System.out.println("비밀번호를 잘못 입력하셨습니다.");
		} else {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■현재 정보■■■■■■■■■■■■■■■■■■■■■■■■■");
			List<Map<String, Object>> userList = myDao.selectUserList();
			System.out.println("회원명         생년월일                   전화번호                        우편번호             주소");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			for (Map<String, Object> User : userList) {
				System.out.print(User.get("MEM_NM") + "    ");
				System.out.print(User.get("MEM_REGNO") + "    ");
				System.out.print(User.get("MEM_NUMBER") +" ");
				System.out.print(User.get("MEM_ZIP") + " ");
				System.out.print(User.get("MEM_ADD") + "  ");
				System.out.println();
			}
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■변결할 정보■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.print("☞ 회원명>");
			String memName = ScanUtil.nextLine();
			System.out.print("☞ 생년월일>");
			String memregno = ScanUtil.nextLine();
			System.out.print("☞ 전화번호>");
			String memnumber = ScanUtil.nextLine();
			System.out.print("☞ 우편번호>");
			String memzip = ScanUtil.nextLine();
			System.out.print("☞ 주소>");
			String memadd = ScanUtil.nextLine();

			//param에 put
			Map<String, Object> param = new HashMap<>();
			param.put("MEM_NM", memName);
			param.put("MEM_REGNO", memregno);
			param.put("MEM_NUMBER", memnumber);
			param.put("MEM_ZIP", memzip);
			param.put("MEM_ADD", memadd);
			
			// 입력받은 데이터 update, 영향 받은 행이 있다면 수정성공 없다면 실패
			int result = myDao.updateUser(param);
			if (0 < result) {
				System.out.println("내정보 수정 성공");
			} else {
				System.out.println("내정보 수정 실패");
			}
		}
		// 마이페이지로 리턴
		return View.MYPAGE_MENU;
	}

	// 가맹점 정보수정 페이지
	public int storeModifyInfo() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■내정보 수정■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("비밀번호를 입력해 주세요 :");
		System.out.print("☞ 입력 > ");
		String password = ScanUtil.nextLine(); 
		
		// 비밀번호 확인절차
		Map<String, Object> user = myDao.selectStore(password); 
		if (user == null) {
			System.out.println("비밀번호를 잘못 입력하셨습니다.");
		} else {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■현재 정보■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			List<Map<String, Object>> storeList = myDao.selectStoreList();
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("가맹점 명            가맹점 전화번호                 가맹점 우편번호            가맹점 주소");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			for (Map<String, Object> Store : storeList) {
				System.out.print(Store.get("BUYER_NAME") + "\t");
				System.out.print(Store.get("BUYER_COMTEL") + "\t");
				System.out.print(Store.get("BUYER_ZIP") + "\t");
				System.out.print(Store.get("BUYER_ADD") + "\t");
				System.out.println();
			}
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■변결할 정보■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.print("☞ 가맹점  명>");
			String buyername = ScanUtil.nextLine();
			System.out.print("☞ 가맹점 전화번호>");
			String buyercomtel = ScanUtil.nextLine();
			System.out.print("☞ 가맹점 우편번호>");
			String buyerzip = ScanUtil.nextLine();
			System.out.print("☞ 가맹점 주소>");
			String buyeradd = ScanUtil.nextLine();

			//param에 put
			Map<String, Object> param = new HashMap<>();
			param.put("BUYER_NAME", buyername);
			param.put("BUYER_COMTEL", buyercomtel);
			param.put("BUYER_ZIP", buyerzip);
			param.put("BUYER_ADD", buyeradd);
			
			// 입력받은 데이터 update, 영향 받은 행이 있다면 수정성공 없다면 실패
			int result = myDao.insertStore(param);
			if (0 < result) {
				System.out.println("가맹점 정보 수정 성공");
			} else {
				System.out.println("가맹점 정보 수정 실패");
			}
		}
		// 마이페이지로 리턴
		return View.MYPAGE_MENU;
	}

	// 고객주문내역확인
	public int userOrderList() {
		List<Map<String, Object>> userOrderList = myDao.userOrderList();
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■주문내역 ■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("   주문정보전호     메뉴이름     가맹점명     고객주문날짜     총주문금액");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		for (Map<String, Object> User : userOrderList) {
			System.out.print(User.get("주문정보번호") + "\t");
			System.out.print(User.get("메뉴이름") + "\t");
			System.out.print(User.get("가맹점명") + "\t");
			System.out.print(User.get("고객주문날짜") + "\t");
			System.out.print(User.get("총주문금액") + "\t");
			System.out.println();
		}
		// 게시물을 가지고 사용자가 어떤 행위를 할지 결정한다.
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("1. 상세조회     2. 이전으로(마이페이지 메뉴)");
		System.out.print("☞ 입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: orderRead(); break;
		case 2: return View.MYPAGE_MENU;
		default: System.out.println("잘못 입력하셨습니다."); break;
		}
		return View.MYPAGE_MENU;

	}

	// 고객 주문내역 상세조회
	public int orderRead() {
		
		Map<String, Object> User;
		int info_order_no;
		
		while (true) {
			System.out.print("☞ 조회할 게시글 번호 입력>");
			info_order_no = ScanUtil.nextInt();

			Map<String, Object> param = new HashMap<>();
			param.put("INFO_ORDER_NO", info_order_no);

			User = myDao.orderReadDetail(param);
			if (User == null) {
				System.out.println("잘못 입력하셨습니다.");
			} else {
				break;
			}
		}
		// 게시글 내용 출력
		System.out.println("■■■■■■■■■■■■■■■■■■주문내역 상세조회■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■ 주문정보번호 : " + User.get("주문정보번호"));
		System.out.println("■ 메뉴이름 : " + User.get("메뉴이름"));
		System.out.println("■ 가맹점명 : " + User.get("가맹점명"));
		System.out.println("■ 주문수량 : " + User.get("주문수량"));
		// 재료 출력 호출
		List<Map<String, Object>> ingrList = myDao.ingrList(param);
		ArrayList<String> ingrlist = new ArrayList<>();
		for (Map<String, Object> ingr : ingrList) {
			ingrlist.add(ingr.get("재료이름").toString());
		}
		System.out.println("■ 추가재료이름 : " + ingrlist.toString());
		System.out.println("■ 총주문금액 : " + User.get("총주문금액"));
		System.out.println("■ 고객주문날짜 : " + User.get("고객주문날짜"));
		System.out.println("■ 가맹점 승인 : " + User.get("가맹점승인"));
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		// 이 게시글을 사용하여 사용자가 어떤 행위를 할지 결정한다.

		System.out.println("1.이전으로 (마이페이지 메뉴)");
		System.out.print("☞ 입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.MYPAGE_MENU;
		default: System.out.println("잘못 입력하셨습니다."); break;
		}
		return View.MYPAGE_MENU;
	}


	// 가맹점주문내역확인
	public int storeOrderList() {
		List<Map<String, Object>> storeOrderList = myDao.storeOrderList();
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■주문내역 ■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("     주문정보번호     메뉴이름     가맹점명     고객 주문 날짜     총주문금액");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		for (Map<String, Object> Store : storeOrderList) {
			System.out.print(Store.get("주문정보번호") + "\t");
			System.out.print(Store.get("메뉴이름") + "\t");
			System.out.print(Store.get("가맹점명") + "\t");
			System.out.print(Store.get("고객주문날짜") + "\t");
			System.out.print(Store.get("총주문금액") + "\t");
			System.out.println();
		}
		// 게시물을 가지고 사용자가 어떤 행위를 할지 결정한다.
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("1. 상세조회     2. 이전으로(마이페이지 메뉴)");
		System.out.print("☞ 입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: orderRead2(); break;
		case 2: return View.MYPAGE_MENU;
		default: System.out.println("잘못 입력하셨습니다."); break;
		}
		return View.MYPAGE_MENU;
	}

	// 가맹점 주문내역 상세조회
	public int orderRead2() {
		Map<String, Object> User;
		int in_order_no;
		Map<String, Object> param;
		
		while(true){
			System.out.print("☞ 조회할 게시글 번호 입력>");
			in_order_no = ScanUtil.nextInt();
	
			param = new HashMap<>();
			param.put("INFO_ORDER_NO", in_order_no);
	
			User = myDao.orderReadDetail2(param);
			if(User==null){
				System.out.println("잘못 입력하셨습니다.");
			}else{
				break;
			}
		}
		// 게시글 내용 출력
		System.out.println("■■■■■■■■■■■■■■■■■■주문내역 상세조회■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■ 주문정보번호 : " + User.get("주문정보번호"));
		System.out.println("■ 메뉴이름 : " + User.get("메뉴이름"));
		// 재료 출력 호출
		List<Map<String, Object>> ingrList = myDao.ingrList2(param);
		ArrayList<String> ingrlist = new ArrayList<>();
		for (Map<String, Object> ingr : ingrList) {
			ingrlist.add(ingr.get("재료이름").toString());
		}
		System.out.println("■ 추가재료이름 : " + ingrlist.toString());
		System.out.println("■ 주문수량 : " + User.get("주문수량"));
		System.out.println("■ 총주문금액 : " + User.get("총주문금액"));
		System.out.println("■ 고객주문날짜 : " + User.get("고객주문날짜"));
		System.out.println("■ 가맹점 승인 : " + User.get("가맹점승인"));
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		// 이 게시글을 사용하여 사용자가 어떤 행위를 할지 결정한다.
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("1.이전으로 (마이페이지 메뉴)");
		System.out.print("☞ 입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.MYPAGE_MENU;
		default: System.out.println("잘못 입력하셨습니다."); break;
		}
		return View.MYPAGE_MENU;
}

	
	// 관리자 1대1문의 조회
	public int managerInquiry() {
		List<Map<String, Object>> inquiryList = myDao.selectInquiryList();
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■고객 센터 관리 ■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("문의 번호     문의 제목      문의 작성일자");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		for (Map<String, Object> inquiry : inquiryList) {
			System.out.print(inquiry.get("INQUIRY_NO") + "\t");
			System.out.print(inquiry.get("INQUIRY_TITLE") + "\t");
			System.out.print(inquiry.get("INQUIRY_REG_DATE") + "\t");
			System.out.println();
		}
		// 게시물을 가지고 사용자가 어떤 행위를 할지 결정한다.
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("1. 문의 상세 조회     2. 이전으로(마이페이지 메뉴)");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print("☞ 입력 > ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: inquiryReadDelete(); break;
		case 2: break;// 호출한 메서드로 돌아감. myPageHome()
		default: System.out.println("잘못 입력하셨습니다."); break;
		}
		return View.MYPAGE_MENU;
	}

	// 1대1문의 상세조회 
	public int inquiryReadDelete() {
		Map<String, Object> inquiry;
		int inquiry_no;
		
		while(true){
			System.out.print("☞ 조회할 게시글 번호 입력>");
			inquiry_no = ScanUtil.nextInt();
			Map<String, Object> param = new HashMap<>();
			param.put("INQUIRY_NO", inquiry_no);
			
			// 게시글 내용 출력
			inquiry = myDao.selectInquiry(param); 
			if(inquiry==null){
				System.out.println("잘못 입력하셨습니다.");
			}else{
				break;
			}
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■ 문의 번호 : " + inquiry.get("INQUIRY_NO"));
		System.out.println("■ 문의 제목: " + inquiry.get("INQUIRY_TITLE"));
		System.out.println("■ 문의 이메일: " + inquiry.get("INQUIRY_EMAIL"));
		System.out.println("■ 문의 전화번호: " + inquiry.get("INQUIRY_COMETL"));
		System.out.println("■ 문의 내용: " + inquiry.get("INQUIRY_CONTENT"));
		System.out.println("■ 문의 작성일자: " + inquiry.get("INQUIRY_REG_DATE"));
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
		// 이 게시글을 사용하여 사용자가 어떤 행위를 할지 결정한다.
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("1. 1:1 문의삭제     2.이전으로(마이페이지 메뉴)");
		System.out.print("☞ 입력>");;
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: deleteBoard(inquiry_no); break;
		case 2: return managerInquiry();
		default: System.out.println("잘못 입력하셨습니다."); break;
		}
		return View.MYPAGE_MENU;
	}

	// 1대1 문의 삭제
	public int deleteBoard(int inquiry_no) {
		System.out.println("■■■■■■■■■■■■■■■■■■■ 1:1 문의 삭제 ■■■■■■■■■■■■■■■■■■■■■■");
		Map<String, Object> param = new HashMap<>();
		param.put("INQUIRY_NO", inquiry_no);
		int result = myDao.deleteBoard(param);
		if (0 < result) {
			System.out.println("1:1 문의를 삭제였습니다.");
		} else {
			System.out.println("1:1 문의 삭제를 실패하였습니다.");
		}
		return managerInquiry();

	}
}
