package service;

import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.OrderDao;
import util.ScanUtil;
import util.View;
// 주문 서비스 
public class OrderService {
//	생성자 만듬(private)
	private OrderService(){}
	
//	객체를 보관할 변수 생성
	private static OrderService instance;
	
//	메서드 호출 시 객체 주소 부여 
	public static OrderService getInstance(){
		if(instance == null){
			instance = new OrderService();
		}
		return instance;
	}
	
	private OrderDao orderDao = OrderDao.getInstance();
	

	
	//점주 주문메뉴 메인홈
	public int orderBuyerHome() {
		
		System.out.println("--------------------------------------");
		System.out.println("1.주문전체 조회   \t2.주문등록승인   \t3.주문등록삭제   \t4.이전으로");
		System.out.println("--------------------------------------");
		System.out.print("번호 입력> ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			buyerOrderList(Controller.loginUser.get("BUYER_ID")); // 점주 주문조회
			break;
		case 2:
			buyerOrderRegAccept();
			break;
		case 3:
			buyerOrderRegDelete();
			break;		
		case 4:
			return View.LOGIN_MAIN_MENU; //소분류 메뉴로
			
		default :
			System.out.println("잘못입력하였습니다.");
			break;
		}
		
		return View.ORDER_BUYER_MENU;
	}
	
    //점주 주문목록 조회
	public void buyerOrderList(Object buyer) {
		list : while(true) {
			List<Map<String, Object>> orderList = orderDao.buyerOrderList(buyer);
		    System.out.println("--------------------------------------");
		    System.out.println("주문번호   \t가맹점명   \t메뉴이름");
		    for(Map<String, Object> list : orderList) {
		    	System.out.println(list.get("ORDER_NO")
					+ "\t" + list.get("BUYER_NAME")
					+ "\t" + list.get("MENU_NM")
					+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
					+ "\t" + list.get("ORDER_PRICE") + "원");
		    }
		    System.out.println("--------------------------------------");
			    System.out.println("1.주문상세   \t2이전으로");
			    System.out.print("번호를 입력해주세요.> ");
			    int input = ScanUtil.nextInt();
			    switch(input) {
			    case 1:
			    	buyerOrderDetail(Controller.loginUser.get("BUYER_ID"));
				    break;
			    case 2:
				    break list;
				default :
					System.out.println("잘못입력하였습니다.");
					break;
				}
			}
		}

		
	
    //점주 주문목록 상세
	public void buyerOrderDetail(Object buyer) {
		System.out.println("--------------------------------------");
		System.out.print("주문번호를 입력해주세요> ");
		String orderNo = ScanUtil.nextLine();
		System.out.println("--------------------------------------");
		
		List<Map<String, Object>> orderList = orderDao.buyerOrderDetail(orderNo,buyer);
		for(Map<String, Object> list : orderList) {
			System.out.println(list.get("ORDER_NO")
					+ "\t" + list.get("BUYER_NAME")
					+ "\t" + list.get("MENU_NM")
					+ "\t" + list.get("INGR_NAME")
					+ "\t" + list.get("INFO_CART_QTY") + "개"
					+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
					+ "\t" + list.get("TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')") + "(점주)"
					+ "\t" + list.get("ORDER_PRICE") + "원");
		}
		System.out.println("--------------------------------------");
			
			System.out.println("1.이전으로");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				System.out.println("고객 주문목록을 출력합니다.");
				break;

			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}
		}
	
	//점주 주문등록 삭제 홈
	public void buyerOrderRegDelete() {
		list: while (true) {
			System.out.println("점주주문목록에서 삭제할 주문목록을 출력합니다.");

			// 점주 삭제할 주문리스트 출력
			deleteOrderList(Controller.loginUser.get("BUYER_ID"));
            
			System.out.println("1.삭제할 주문리스트 입력 2.이전으로");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: // 점주 주문등록 삭제
				System.out.println("삭제할 리스트의 주문번호를 입력해주세요.");
				String orderNo = ScanUtil.nextLine();
				System.out.println("삭제할 리스트의 주문정보번호를 입력해주세요");
				int orderInfoNo = ScanUtil.nextInt();
				deleteOrder(orderNo);
				deleteAddIngr(orderInfoNo);
				deleteInfoOrder(orderInfoNo);
				break;
			case 2:
				System.out.println("점주 주문메뉴를 출력합니다.");
				break list;
			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}
		}
	}
	
	
		
    //ORDER TABLE 삭제
	public void deleteOrder(String orderNo) {
		int result = orderDao.deleteInfoOrder(orderNo);
		System.out.println(result + "개의 행이 삭제되었습니다.");
	}
	
	//ADD_INGR TABLE 삭제
	public void deleteAddIngr(int orderInfoNo) {
		int result = orderDao.deleteAddIngr(orderInfoNo);
		System.out.println(result + "개의 행이 삭제되었습니다.");
	}
	
	//INFO_ORDER TABLE 삭제
	public void deleteInfoOrder(int orderInfoNo) {
		int result = orderDao.deleteInfoOrder(orderInfoNo);
		System.out.println(result + "개의 행이 삭제되었습니다.");
	}

	//점주 삭제할 주문목록 리스트
    public void deleteOrderList(Object buyer) {
		List<Map<String, Object>> deleteOrederList = orderDao.deleteOrderList(buyer);
		System.out.println("--------------------------------------");
	    System.out.println("주문번호   \t주문정보번호   \t가맹점명   \t메뉴이름");
	    for(Map<String, Object> list : deleteOrederList) {
	    	System.out.println(list.get("ORDER_NO")
	    			+ "\t" + list.get("INFO_ORDER_NO")
				    + "\t" + list.get("BUYER_NAME")
				    + "\t" + list.get("MENU_NM") 
				    + "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
				    + "\t" + list.get("TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')") + "(점주)"
				    + "\t" + list.get("ORDER_PRICE") + "원") ;
	    }
	    System.out.println("--------------------------------------");
	}

	//점주 주문등록 승인
	public void buyerOrderRegAccept() {
		System.out.println("점주주문목록에서 미등록된 주문목록을 출력합니다.");
		list : while(true) {
		//점주 미등록 주문리스트 출력
		notAcceptOrderList();
		
		System.out.println("1.상세조회\t2.이전으로");
		int input = ScanUtil.nextInt();
		
		if(input == 1) {
			//점주 미등록 주문리스트 상세 조회
			notAcceptOrderDetail();
		}else {
			break list;
		}
				
		//점주 등록 승인
		regUpdate();
		
		System.out.println("1.미등록 주문리스트 2.점주 주문메뉴");
		input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			break;
		
		case 2:
			System.out.println("점주 주문메뉴를 출력합니다.");
			break list;

		default:
			System.out.println("잘못입력하였습니다.");
			break;
		}
		}
		
	}
	
	//점주 미등록 주문리스트 상세 조회
	public void notAcceptOrderDetail() {
		List<Map<String, Object>> orderList = orderDao.notAcceptOrderDetail();
		for (Map<String, Object> list : orderList) {
			System.out.println(list.get("ORDER_NO") 
					+ "\t" + list.get("BUYER_NAME") 
					+ "\t" + list.get("MENU_NM") 
					+ "\t" + list.get("INGR_NAME") 
					+ "\t" + list.get("INFO_CART_QTY") + "개" 
					+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')")  + "(고객)"
					+ "\t" + list.get("TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')") + "(점주)"
					+ "\t" + list.get("ORDER_PRICE") + "원");
		}
		System.out.println("--------------------------------------");
		
	}
	
	//점주 등록 승인
	public void regUpdate() {
		System.out.println("등록승인할 주문번호를 입력해주세요.");
		String orderNo = ScanUtil.nextLine();
		int orderReg = orderDao.regUpdate(orderNo);
		System.out.println(orderReg + "개의 주문리스트가 등록되었습니다.");
		
		
	}

	//점주 미등록 주문리스트
	public void notAcceptOrderList() {
		List<Map<String, Object>> orderList = orderDao.notAcceptOrderList();
		System.out.println("주문번호   \t가맹점명   \t메뉴이름");
		for(Map<String, Object> list : orderList) {
			System.out.println(list.get("ORDER_NO")
					+ "\t" + list.get("BUYER_NAME")
					+ "\t" + list.get("MENU_NM")
					+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
					+ "\t" + list.get("ORDER_PRICE") + "원");
		}
		System.out.println("--------------------------------------");
		
	}

	//고객 주문메뉴 메인홈
	public int orderMemberHome(){// 고객용
		
		System.out.println("--------------------------------------");
		System.out.println("1.주문목록 조회\t2.주문등록 \t3.이전으로");
		System.out.println("--------------------------------------");
		System.out.print("번호 입력> ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			memberOrderList(Controller.loginUser.get("MEM_ID")); // 고객 주문목록 조회
			break;
			
		case 2:
			memberOrderReg(); // 주문등록
			break;
			
		case 3:
			return View.LOGIN_MAIN_MENU; //소분류 메뉴로
			
		default :
			System.out.println("잘못입력하였습니다.");
			break;

		
		}
		return View.ORDER_MEMBER_MENU;
	} 
	
	//고객 주문목록 리스트
	public void memberOrderList(Object member) {
		list : while(true) {
			List<Map<String, Object>> orderList = orderDao.memberOrderList(member); // 주문목록 조회
			System.out.println("--------------------------------------");
			System.out.println("주문번호   \t가맹점명   \t메뉴이름");
			for(Map<String, Object> list : orderList) {
				System.out.println(list.get("ORDER_NO")
						+ "\t" + list.get("BUYER_NAME")
						+ "\t" + list.get("MENU_NM")
						+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
						+ "\t" + list.get("ORDER_PRICE") + "원");
			}
			System.out.println("--------------------------------------");
			System.out.println("1.주문상세   \t2.이전으로");
			System.out.print("번호를 입력해주세요.> ");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				memberOrderDetail(Controller.loginUser.get("MEM_ID"));
				break;
			case 2:
				System.out.println("주문메뉴를 출력합니다.");
				break list; //주문메뉴로

			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}

		}

	}
	
    //고객 주문목록 상세조회
 	public void memberOrderDetail(Object member) {
			 			
		System.out.println("--------------------------------------");
		System.out.print("주문번호를 입력해주세요> ");
		String orderNo = ScanUtil.nextLine();
		System.out.println("--------------------------------------");

		List<Map<String, Object>> orderList = orderDao.memberOrderDetail(member, orderNo);
		for (Map<String, Object> list : orderList) {
			System.out.println(list.get("ORDER_NO") 
					+ "\t" + list.get("BUYER_NAME") 
					+ "\t" + list.get("MENU_NM") 
					+ "\t" + list.get("INGR_NAME") 
					+ "\t" + list.get("INFO_CART_QTY") + "개" 
					+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)" 
					+ "\t" + list.get("TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')") + "(점주)"
					+ "\t" + list.get("ORDER_PRICE") + "원");
		}
		System.out.println("--------------------------------------");
		System.out.println("1.이전으로");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			System.out.println("고객 주문목록을 출력합니다.");
			break;

		default:
			System.out.println("잘못입력하였습니다.");
			break;
		}

	}

	//고객 주문등록
	public void memberOrderReg() {
		System.out.println("주문메뉴를 선택해주세요");
		System.out.println("1.샌드위치 \t2.샐러드   \t3.랩");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1:
			sandwich();
			break;
		case 2:
			salad();
			break;
		case 3:
			rap();
			break;
	    default :
	    	System.out.println("잘못입력하였습니다.");
			break;
		}
		
	}
	
	//샌드위치-재료선택
	public int sandwich() {
		buyerList();
		System.out.println("가맹점을 선택해주세요");
		int input5 = ScanUtil.nextInt();
		String buyer = null;
		if(input5 == 1) {
			buyer = "DEMOBUYER1";
		}else if(input5 == 2) {
			buyer = "DEMOBUYER2";
		}else if(input5 == 3) {
			buyer = "DEMOBUYER3";
		}else {
			buyer = "subway1";
		}
				
		System.out.println("메뉴를 선택해주세요");
		System.out.println("1.에그마요 베이컨\t 2.에그마요 페퍼로니");
		int menu = ScanUtil.nextInt();
		
		
		System.out.println("빵을 선택해주세요");
		System.out.println("1.화이트\t 2.파마산");
		int input1 = ScanUtil.nextInt();
		String bread = null;
		if(input1 == 1) {
			bread = "B001";
		}else {
			bread = "B002";
		}
		
		System.out.println("치즈를 선택해주세요");
		System.out.println("1.아메리칸\t 2.슈레드 ");
		int input2 = ScanUtil.nextInt();
		String cheese = null;
		if(input2 == 1) {
			cheese = "C001";
		}else {
			cheese = "C002";
		}
		
		System.out.println("야채를 선택해주세요");
		System.out.println("1.양상추\t 2.토마토");
		int input3 = ScanUtil.nextInt();
		String vegetable = null;
		if(input3 == 1) {
			vegetable = "V001";
		}else {
			vegetable = "V002";
		}
		
		System.out.println("소스를 선택해주세요");
		System.out.println("1.머스타드\t 2.칠리");
		int input4 = ScanUtil.nextInt();
		String source = null;
		if(input4 == 1) {
			source = "S001";
		}else {
			source = "S002";
		}
		
		System.out.println("수량을 입력해주세요");
	    int qty = ScanUtil.nextInt();
		
	    int price = 0;
		if(menu == 1) {
			price = 4800 * qty;
		}else if(menu == 2) {
			price = 4800 * qty;
		}
	    
		System.out.println("1.주문등록하기 2. 취소");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1: 
			orderNoInsert(buyer,Controller.loginUser.get("MEM_ID"),price);
			memberOrderNoList(buyer);
			System.out.println("주문번호를 입력해주세요");
			String orderNo = ScanUtil.nextLine();
			mainMenuInsert(menu,orderNo,qty);
			memberInfoOrderNoList(orderNo);
			System.out.println("주문정보번호를 입력해주세요");
			int infoOrderNo = ScanUtil.nextInt();
			breadInsert(infoOrderNo, bread);
			cheeseInsert(infoOrderNo,cheese);
			vegetableInsert(infoOrderNo,vegetable);
			sourceInsert(infoOrderNo,source);
			memberOrderRegList(infoOrderNo);
			System.out.println("주문등록이 완료되었습니다.");
			System.out.println("주문메뉴로 돌아가시겠습니까?");
			String yes = ScanUtil.nextLine();
			break;
		case 2:
			break;//주문메뉴로 
		default :
			System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MEMBER_MENU;
	}
	
	//샐러드-재료선택
	public int salad() {
		
		buyerList();
		System.out.println("가맹점을 선택해주세요");
		int input5 = ScanUtil.nextInt();
		String buyer = null;
		if(input5 == 1) {
			buyer = "DEMOBUYER1";
		}else if(input5 == 2) {
			buyer = "DEMOBUYER2";
		}else if(input5 == 3) {
			buyer = "DEMOBUYER3";
		}else {
			buyer = "subway1";
		}		
				
		System.out.println("메뉴를 선택해주세요");
		System.out.println("5.써브웨이 클럽\t6.이탈리안 비엠티");
		int menu = ScanUtil.nextInt();
		
				
		System.out.println("치즈를 선택해주세요");
		System.out.println("1.아메리칸\t 2.슈레드 ");
		int input2 = ScanUtil.nextInt();
		String cheese = null;
		if(input2 == 1) {
			cheese = "C001";
		}else {
			cheese = "C002";
		}
		
		System.out.println("야채를 선택해주세요");
		System.out.println("1.양상추\t 2.토마토");
		int input3 = ScanUtil.nextInt();
		String vegetable = null;
		if(input3 == 1) {
			vegetable = "V001";
		}else {
			vegetable = "V002";
		}
		
		System.out.println("소스를 선택해주세요");
		System.out.println("1.머스타드\t 2.칠리");
		int input4 = ScanUtil.nextInt();
		String source = null;
		if(input4 == 1) {
			source = "S001";
		}else {
			source = "S002";
		}
		
		System.out.println("수량을 입력해주세요");
	    int qty = ScanUtil.nextInt();
	    
	    int price = 0;
		if(menu == 5) {
			price = 5800 * qty;
		}else if(menu == 6) {
			price = 6200 * qty;
		}
		
		System.out.println("1.주문등록하기 2. 취소");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1: 
			orderNoInsert(buyer,Controller.loginUser.get("MEM_ID"),price);
			memberOrderNoList(buyer);
			System.out.println("주문번호를 입력해주세요");
			String orderNo = ScanUtil.nextLine();
			mainMenuInsert(menu,orderNo,qty);
			memberInfoOrderNoList(orderNo);
			System.out.println("주문정보번호를 입력해주세요");
			int infoOrderNo = ScanUtil.nextInt();
			cheeseInsert(infoOrderNo,cheese);
			vegetableInsert(infoOrderNo,vegetable);
			sourceInsert(infoOrderNo,source);
			memberOrderRegList(infoOrderNo);
			System.out.println("주문등록이 완료되었습니다.");
			System.out.println("주문메뉴로 돌아가시겠습니까?");
			String yes = ScanUtil.nextLine();
			break;
		case 2:
			break;//주문메뉴로 
		default :
			System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MEMBER_MENU;
	}
	
	//랩-재료x
	public int rap() {
		
		buyerList();
		System.out.println("가맹점을 선택해주세요");
		int input5 = ScanUtil.nextInt();
		String buyer = null;
		if(input5 == 1) {
			buyer = "DEMOBUYER1";
		}else if(input5 == 2) {
			buyer = "DEMOBUYER2";
		}else if(input5 == 3) {
			buyer = "DEMOBUYER3";
		}else {
			buyer = "subway1";
		}
				
		System.out.println("메뉴를 선택해주세요");
		System.out.println("3.쉬림프 에그 그릴드 랩\t 4.스테이크 & 치즈 아보카도 그릴드 랩");
		int menu = ScanUtil.nextInt();
		
		
		System.out.println("수량을 입력해주세요");
	    int qty = ScanUtil.nextInt();
	    
	    int price = 0;
		if(menu == 3) {
			price = 5000 * qty;
		}else if(menu == 4){
			price = 5700 * qty;
		}
		
		System.out.println("1.주문등록하기 2. 취소");
		int input = ScanUtil.nextInt();
				
		switch(input) {
		case 1: 
			orderNoInsert(buyer,Controller.loginUser.get("MEM_ID"),price);
			memberOrderNoList(buyer);
			System.out.println("주문번호를 입력해주세요");
			String orderNo = ScanUtil.nextLine();
			mainMenuInsert(menu,orderNo,qty);
			memberInfoOrderNoList(orderNo);
			System.out.println("주문정보번호를 입력해주세요");
			int infoOrderNo = ScanUtil.nextInt();
			memberOrderRegList2(infoOrderNo);
			System.out.println("주문등록이 완료되었습니다.");
			System.out.println("주문메뉴로 돌아가시겠습니까?");
			String yes = ScanUtil.nextLine();
			break;
		case 2:
			break;//주문메뉴로 
		default :
			System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MEMBER_MENU;
		
	}
	
	
    //고객 주문등록 리스트 -
	public void memberOrderRegList(int infoOderNo) {
		List<Map<String, Object>> regOrderList = orderDao.memberOrderRegList(infoOderNo);
		for(Map<String, Object> list : regOrderList) {
			System.out.println(list.get("BUYER_NAME")
					+ "\t" + list.get("INGR_NAME")
					+ "\t" + list.get("MENU_NM")
					+ "\t" + list.get("INFO_CART_QTY")
					+ "\t" + list.get("TO_CHAR(B.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
					+ "\t" + list.get("ORDER_PRICE") + "원");
		}
		System.out.println("--------------------------------------");
	}
	
	//고객 주문등록 리스트
	public void memberOrderRegList2(int infoOrderNo) {
		List<Map<String, Object>> regOrderList2 = orderDao.memberOrderRegList2(infoOrderNo);
		for(Map<String, Object> list : regOrderList2) {
			System.out.println(list.get("BUYER_NAME")
					+ "\t" + list.get("MENU_NM")
					+ "\t" + list.get("INFO_CART_QTY")
					+ "\t" + list.get("TO_CHAR(B.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
					+ "\t" + list.get("ORDER_PRICE") + "원");
		}
		System.out.println("--------------------------------------");
		
	}
	
	//주문번호입력
    public void orderNoInsert(String buyer,Object member, int price) {
		int buy = orderDao.orderNoInsert(buyer,member,price);
		System.out.println(buy + "개의 주문번호가 입력되었습니다.");	
		System.out.println("--------------------------------------");
		
	}
		
	//메인메뉴입력
	public void mainMenuInsert(int menu, String orderNo, int qty) {
		int mainMenu = orderDao.mainMenuInsert(menu,orderNo,qty);
		System.out.println(mainMenu + "개의 메인메뉴가 입력되었습니다.");
		System.out.println("--------------------------------------");
		
	}
		
	//빵입력
	public void breadInsert(int infoOrderNo,String bread) {
		int insertBread = orderDao.breadInsert(infoOrderNo,bread);
		System.out.println(insertBread + "개의 빵이 입력되었습니다.");
		System.out.println("--------------------------------------");
			
	}
		
	//치즈입력
	public void cheeseInsert(int infoOrderNo, String cheese) {
		int insertCheese = orderDao.cheeseInsert(infoOrderNo,cheese);
		System.out.println(insertCheese + "개의 치즈가 입력되었습니다.");
		System.out.println("--------------------------------------");
			
	}
		
	//야채입력
	public void vegetableInsert(int infoOrderNo, String vegetable) {
		int insertVegetable = orderDao.vegetableInsert(infoOrderNo,vegetable);
		System.out.println(insertVegetable + "개의 야채가 입력되었습니다.");
		System.out.println("--------------------------------------");
			
	}
		
	//소스입력
	public void sourceInsert(int infoOrderNo, String source) {
		int insertSource = orderDao.sourceInsert(infoOrderNo,source);
		System.out.println(insertSource + "개의 소스가 입력되었습니다.");
		System.out.println("--------------------------------------");
			
	}
	
	//고객 주문번호 리스트
	public void memberOrderNoList(String buyer) {
		System.out.println("주문번호");
		List<Map<String, Object>> orderNoList = orderDao.memberOrderNoList(buyer);
		for(int i = 0; i < orderNoList.size(); i++) {
			Map<String, Object> list = orderNoList.get(i);
			System.out.println(i+1 + "." + list.get("ORDER_NO"));
		}
		
	}
		
	//고객 주문정보리스트
	public void memberInfoOrderNoList(String orderNo) {
		System.out.println("주문정보번호");
		List<Map<String, Object>> infoOrderNoSelectList = orderDao.memberInfoOrderNoList(orderNo);
		for(int i = 0; i < infoOrderNoSelectList.size(); i++) {
			Map<String, Object> list = infoOrderNoSelectList.get(i);
			System.out.println(i+1 + "." + list.get("INFO_ORDER_NO"));
		}
					
	}
	
	//가맹점목록 리스트
	public void buyerList() {
		System.out.println("가맹점명");
		List<Map<String, Object>> buyerList = orderDao.buyerList();{
			for(int i = 0; i < buyerList.size(); i++) {
				Map<String, Object> list = buyerList.get(i);
				System.out.print(i+1 + "." + list.get("BUYER_NAME") + "  \t");
			}
			System.out.println();
		}

	}
	
	
		
	

}