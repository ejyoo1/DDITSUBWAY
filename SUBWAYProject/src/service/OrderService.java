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
	
	public void orderReg() {// 주문등록
		buyerSelect();
		
		System.out.println("가맹점을 선택해주세요");
		int input = ScanUtil.nextInt();
	}
	
	public void buyerSelect() {
		List<Map<String, Object>> buyerList = orderDao.slectBuyerList();{
			for(int i = 0; i < buyerList.size(); i++) {
				Map<String, Object> list = buyerList.get(i);
				System.out.print(i+1 + ". " + list.get("BUYER_NAME") + "\t");
				
				
			}
			System.out.println();

		}

	}
	
	public static void main(String[] args) {
		
	}
	
}