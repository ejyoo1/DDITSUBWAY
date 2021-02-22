package service;

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
		System.out.println("1.주문목록 조회\t2.주문등록\t3.이전으로");
		System.out.println("--------------------------------------");
		System.out.print("번호 입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			orderList(); // 주문목록 조회
			break;
			
		case 2:
			orderReg(); // 주문등록
			break;
			
		case 3:
//			return View.ORDER_MENU; //소분류 메뉴로
			
		default :
			System.out.println("잘못입력하였습니다.");
			break;

		
		}
		return View.ORDER_MENU;
}
	public void orderList() {
		List<Map<String, Object>> orderList = orderDao.selectOrderList(); {// 주문목록 조회
			System.out.println("--------------------------------------");
			System.out.println("주문번호\t가맹점명\t메뉴이름\t주문일");
			System.out.println("--------------------------------------");
			for(Map<String, Object> list : orderList) {
				System.out.println(list.get("ORDER_NO")
						+ "\t" + list.get("BUYER_NAME")
						+ "\t" + list.get("MENU_NM")
						+ "\t" + list.get("ORDER_MEMBER_DATE"));
			}
			System.out.println("--------------------------------------");
			System.out.println("1.주문상세\t2.이전으로");
			System.out.print("번호입력>");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				orderDetail();
				break;
			case 2:
				break; //주문메뉴로

			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}

		}
		

	}

	private void orderDetail() {
		Map<String, Object> orderOne = orderDao.selectOrderOne();{
			System.out.println("주문번호를 입력해주세요");
			int input = ScanUtil.nextInt();
			int orderNo = input;
			
		}
		System.out.println("주문번호\t가맹점명\t메뉴이름\t재료(선택)\t주문일(회원)\t주문일(가맹점확인)");
		
		System.out.println("1.이전으로");
		
	}
	private void orderReg() {// 주문등록
		// TODO Auto-generated method stub
		
	}

	
}