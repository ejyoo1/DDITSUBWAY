package service;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	//고객 주문메뉴 메인홈
	public int orderMemberHome(){// 고객용
		
		System.out.println("--------------------------------------");
		System.out.println("1.주문목록 조회\t2.주문등록 \t3.이전으로");
		System.out.println("--------------------------------------");
		System.out.print("번호 입력> ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			orderList(Controller.loginUser.get("MEM_ID")); // 고객 주문목록 조회
			break;
			
		case 2:
			orderReg(); // 주문등록
			break;
			
		case 3:
			return View.LOGIN_MAIN_MENU; //소분류 메뉴로
			
		default :
			System.out.println("잘못입력하였습니다.");
			break;

		
		}
		return View.ORDER_MEMBER_MENU;
	} 
	
	private void orderAllList() {
		// TODO Auto-generated method stub
		
	}

	//점주 주문메뉴 메인홈
	public int orderBuyerHome() {
		
		System.out.println("--------------------------------------");
		System.out.println("1.주문전체 조회\t2.이전으로");
		System.out.println("--------------------------------------");
		System.out.print("번호 입력> ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
//			orderList(); // 주문목록 조회
			break;
			
		case 2:
			return View.LOGIN_MAIN_MENU; //소분류 메뉴로
			
		default :
			System.out.println("잘못입력하였습니다.");
			break;
		}
		
		return View.ORDER_BUYER_MENU;
	}

	//주문목록 리스트
	public void orderList(Object member) {
		List<Map<String, Object>> orderList = orderDao.selectOrderList(member); // 주문목록 조회
			System.out.println("--------------------------------------");
			System.out.println("주문번호   \t가맹점명   \t메뉴이름   \t주문일");
			for(Map<String, Object> list : orderList) {
				System.out.println(list.get("ORDER_NO")
						+ "\t" + list.get("BUYER_NAME")
						+ "\t" + list.get("MENU_NM")
						+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')"));
			}
			System.out.println("--------------------------------------");
			System.out.println("1.주문상세   \t2.이전으로");
			System.out.print("번호를 입력해주세요.> ");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				orderDetail();
				break;
			case 2:
				System.out.println("주문메뉴를 출력합니다.");
				break; //주문메뉴로

			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}

		
		

	}
	
    //주문목록 상세
 	private void orderDetail() {

				System.out.println("--------------------------------------");
				
				
				System.out.print("주문번호를 입력해주세요> ");
				
				
				
				String orderNo = ScanUtil.nextLine();
				System.out.println("--------------------------------------");
				
				List<Map<String, Object>> orderList2 = orderDao.selectOrderList2(orderNo); 
				System.out.println("주문번호   \t가맹점명   \t메뉴이름   \t재료(선택)\t수량 \t주문일(회원) \t주문일(가맹점확인)");
				for(Map<String, Object> list : orderList2) {
					System.out.println(list.get("ORDER_NO")
							+ "\t" + list.get("BUYER_NAME")
							+ "\t" + list.get("MENU_NM")
							+ "\t" + list.get("INGR_NAME")
							+ "\t" + list.get("INFO_CART_QTY") + "개"
							+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')")
							+ "\t" + list.get("TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')"));
				}
				System.out.println("--------------------------------------");
				System.out.println("1.이전으로");
				int input = ScanUtil.nextInt();
				switch (input) {
				case 1:
//					orderList();
					break;

				default:
					System.out.println("잘못입력하였습니다.");
					break;
				}

			}

	//주문등록
	public void orderReg() {
		System.out.println("주문메뉴를 선택해주세요");
		System.out.println("1.샌드위치 \t2.샐러드   \t3.랩");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1:
			sandewitch();
			break;
		case 2:
			salad();
			break;
		case 3:
			rap();
			break;
		}
		
	}
	
	//샌드위치-재료선택
	public int sandewitch() {
		buyerSelect();
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
		
		System.out.println("1.주문등록하기 2. 취소");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1: 
			buyerInsert(buyer,Controller.loginUser.get("MEM_ID"));
			orderNoSelectList(buyer);
			System.out.println("주문번호를 입력해주세요");
			String orderNo = ScanUtil.nextLine();
			menuInsert(menu,orderNo,qty);
			InfoOrderNoSelectList(orderNo);
			System.out.println("주문정보번호를 입력해주세요");
			int infoOrderNo = ScanUtil.nextInt();
			breadInsert(infoOrderNo, bread);
			cheeseInsert(infoOrderNo,cheese);
			vegetableInsert(infoOrderNo,vegetable);
			sourceInsert(infoOrderNo,source);
			regOrderList(infoOrderNo);
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
		
		buyerSelect();
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
		
		System.out.println("1.주문등록하기 2. 취소");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1: 
			buyerInsert(buyer,Controller.loginUser.get("MEM_ID"));
			orderNoSelectList(buyer);
			System.out.println("주문번호를 입력해주세요");
			String orderNo = ScanUtil.nextLine();
			menuInsert(menu,orderNo,qty);
			InfoOrderNoSelectList(orderNo);
			System.out.println("주문정보번호를 입력해주세요");
			int infoOrderNo = ScanUtil.nextInt();
			cheeseInsert(infoOrderNo,cheese);
			vegetableInsert(infoOrderNo,vegetable);
			sourceInsert(infoOrderNo,source);
			regOrderList(infoOrderNo);
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
		
		buyerSelect();
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
		
		System.out.println("1.주문등록하기 2. 취소");
		int input = ScanUtil.nextInt();
				
		switch(input) {
		case 1: 
			buyerInsert(buyer,Controller.loginUser.get("MEM_ID"));
			orderNoSelectList(buyer);
			System.out.println("주문번호를 입력해주세요");
			String orderNo = ScanUtil.nextLine();
			menuInsert(menu,orderNo,qty);
			InfoOrderNoSelectList(orderNo);
			System.out.println("주문정보번호를 입력해주세요");
			int infoOrderNo = ScanUtil.nextInt();
			regOrderList2(infoOrderNo);
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
	
	

	public void regOrderList(int infoOderNo) {
		List<Map<String, Object>> regOrderList = orderDao.regOrderList(infoOderNo);
		for(Map<String, Object> list : regOrderList) {
			System.out.println(list.get("BUYER_NAME")
					+ "\t" + list.get("INGR_NAME")
					+ "\t" + list.get("MENU_NM")
					+ "\t" + list.get("INFO_CART_QTY")
					+ "\t" + list.get("TO_CHAR(B.ORDER_MEMBER_DATE,'YY-MM-DD')"));
		}
		System.out.println("--------------------------------------");
	}
	
	public void regOrderList2(int infoOrderNo) {
		List<Map<String, Object>> regOrderList2 = orderDao.regOrderList2(infoOrderNo);
		for(Map<String, Object> list : regOrderList2) {
			System.out.println(list.get("BUYER_NAME")
					+ "\t" + list.get("MENU_NM")
					+ "\t" + list.get("INFO_CART_QTY")
					+ "\t" + list.get("TO_CHAR(B.ORDER_MEMBER_DATE,'YY-MM-DD')"));
		}
		System.out.println("--------------------------------------");
		
	}
	
	 //주문번호입력
    public void buyerInsert(String buyer,Object member) {
		int buy = orderDao.buyerInsert(buyer,member);
		System.out.println(buy + "개의 행이 입력되었습니다.");	
		System.out.println("--------------------------------------");
		
	}
		
	//메뉴입력
	public void menuInsert(int menu, String orderNo, int qty) {
		int mainMenu = orderDao.menuInsert(menu,orderNo,qty);
		System.out.println(mainMenu + "개의 행이 입력되었습니다.");
		System.out.println("--------------------------------------");
		
	}
		
	//빵입력
	public void breadInsert(int infoOrderNo,String bread) {
		int insertBread = orderDao.insertBread(infoOrderNo,bread);
		System.out.println(insertBread + "개의 행이 입력되었습니다.");
		System.out.println("--------------------------------------");
			
	}
		
	//치즈입력
	public void cheeseInsert(int infoOrderNo, String cheese) {
		int insertCheese = orderDao.insertCheese(infoOrderNo,cheese);
		System.out.println(insertCheese + "개의 행이 입력되었습니다.");
		System.out.println("--------------------------------------");
			
	}
		
	//야채입력
	public void vegetableInsert(int infoOrderNo, String vegetable) {
		int insertVegetable = orderDao.insertVegetable(infoOrderNo,vegetable);
		System.out.println(insertVegetable + "개의 행이 입력되었습니다.");
		System.out.println("--------------------------------------");
			
	}
		
	//소스입력
	public void sourceInsert(int infoOrderNo, String source) {
		int insertSource = orderDao.insertSource(infoOrderNo,source);
		System.out.println(insertSource + "개의 행이 입력되었습니다.");
		System.out.println("--------------------------------------");
			
	}
	
	//주문번호 리스트
	public void orderNoSelectList(String buyer) {
		List<Map<String, Object>> orderNoList = orderDao.orderNoSelectList(buyer);
		for(Map<String, Object> list : orderNoList) {
			System.out.println(list.get("ORDER_NO"));
		}
		
	}
		
	//주문정보리스트
	public void InfoOrderNoSelectList(String orderNo) {
		List<Map<String, Object>> infoOrderNoSelectList = orderDao.infoOrderNoSelectList(orderNo);
		for(Map<String, Object> list : infoOrderNoSelectList) {
			System.out.println(list.get("INFO_ORDER_NO"));
		}
			
	}
	
	//가맹점목록 리스트
	public void buyerSelect() {
		List<Map<String, Object>> buyerList = orderDao.slectBuyerList();{
			for(int i = 0; i < buyerList.size(); i++) {
				Map<String, Object> list = buyerList.get(i);
				System.out.print(i+1 + "." + list.get("BUYER_NAME") + "  \t");
			}
			System.out.println();
		}

	}
	
	
		
	

}