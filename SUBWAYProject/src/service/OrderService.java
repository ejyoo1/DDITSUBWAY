package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//주문메뉴 메인홈
	public int orderHome(){// 고객용
		
		System.out.println("--------------------------------------");
		System.out.println("1.주문목록 조회\t2.주문등록 \t3.이전으로");
		System.out.println("--------------------------------------");
		System.out.print("번호 입력> ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			orderList(); // 주문목록 조회
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
		return View.ORDER_MENU;
}   
	
	//주문목록 리스트
	public void orderList() {
		List<Map<String, Object>> orderList = orderDao.selectOrderList(); // 주문목록 조회
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
 	private void orderDetail() {//[주문번호, 가맹점명, 메뉴이름, 재료(선택), 수량, 주문일(회원), 주문일(가맹점 확인)]

				System.out.println("--------------------------------------");
				
				
				System.out.print("주문번호를 입력해주세요> ");
				
				
				//1. 사용자 입력
				String orderNo = ScanUtil.nextLine();
				System.out.println("--------------------------------------");
				//2.DB 데이터 접근 -> 출력할 것 가져옴.
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
					orderList();
					break;

				default:
					System.out.println("잘못입력하였습니다.");
					break;
				}

			}
	
 	//주문등록 메인메뉴
	public void orderReg() {
		buyerSelect();
		System.out.println("가맹점을 입력해주세요");
		String buyer = ScanUtil.nextLine();
		Map<String, Object> param = new HashMap<>();
		param.put("BUYER_ID", buyer);
		int result = OrderDao.insertOrder(param);
		System.out.println("1.주문하기\t2.이전으로");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1:
			order();
			break;
		case 2:
			break;
		
		default:
			System.out.println("잘못입력하였습니다.");
			break;
		}
		
		
	}
	
	//가맹점목록 리스트
	public void buyerSelect() {
		List<Map<String, Object>> buyerList = orderDao.slectBuyerList();{
			for(int i = 0; i < buyerList.size(); i++) {
				Map<String, Object> list = buyerList.get(i);
				System.out.print(i+1 + ". " + list.get("BUYER_NAME") + "\t");
			}
			System.out.println();

		}

	}
	
	//메뉴선택
	public void order() {
		System.out.println("주문메뉴를 선택해주세요");
		System.out.println("1.샌드위치\t2.샐러드\t3.랩");
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
		System.out.println("1. 에그마요 베이컨\t 2. 에그마요 페퍼로니\t 3. 이전으로");
		System.out.println("빵을 입력해주세요");
		System.out.println("1.화이트\t 2.파마산");
		String bread = ScanUtil.nextLine();
		
		System.out.println("치즈를 입력해주세요");
		System.out.println("1.아메리칸\t 2.슈레드 ");
		String cheese = ScanUtil.nextLine();
		
		System.out.println("야채를 입력해주세요");
		System.out.println("1.양상추\t 2.토마토");
		String vegetable = ScanUtil.nextLine();
		
		System.out.println("소스를 입력해주세요");
		System.out.println("1.머스타드\t 2.칠리");
		String source = ScanUtil.nextLine();
		
		System.out.println("최종결과 출력");
		// [가맹점명, 메뉴이름, 빵선택, 치즈선택, 야채선택, 소스선택]
		System.out.println("1.주문등록하기 2. 취소");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1: 
			
			break;
		case 2:
			
			break;
		default :
			System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MENU;
	}
	
	//샐러드-재료선택
	public int salad() {
		System.out.println("1. K-바베큐\t 2. 쉬림프\t 3. 이전으로");
		
		System.out.println("치즈를 선택해주세요");
		System.out.println("1.아메리칸\t 2.슈레드 ");
		
		System.out.println("야채를 선택해주세요");
		System.out.println("1.양상추\t 2.토마토");
		
		System.out.println("소스를 선택해주세요");
		System.out.println("1.머스타드\t 2.칠리");
		
		System.out.println("최종결과 출력");
		// [가맹점명, 메뉴이름, 빵선택, 치즈선택, 야채선택, 소스선택]
		System.out.println("1.주문등록하기 2. 취소");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1: 
			
			break;
		case 2:
			break;
		default :
			System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MENU;
	}
	
	//랩-재료x
	public int rap() {
		System.out.println("1. 쉬림프 에그 그릴드 랩\t 2. 스테이크 & 치즈 아보카도 그릴드 랩\t 3. 이전으로");
		System.out.println("최종결과 출력");
		// [가맹점명, 메뉴이름, 빵선택, 치즈선택, 야채선택, 소스선택]
		System.out.println("1.주문등록하기 2. 취소");
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1: 
			
			break;
		case 2:
			
			break;
		default :
			
			break;
		}
		return View.ORDER_MENU;
		
	}

	

	

	
	
	
}