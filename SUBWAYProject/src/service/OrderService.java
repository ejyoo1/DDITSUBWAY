package service;

import dao.OrderDao;
import util.ScanUtil;
import util.View;

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
	
	public void orderhome(){
		
		System.out.println("--------------------------------------");
		System.out.println("1.주문목록 조회\t2.주문등록\t3.이전으로");
		System.out.println("--------------------------------------");
		System.out.print("번호 입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			orderList();
			break;
		case 2:
			orderReg();
			break;
		case 3:
//			return View.ORDER; 

		
		}
		
}

	private void orderReg() {
		// TODO Auto-generated method stub
		
	}

	private void orderList() {
		// TODO Auto-generated method stub
		
	}
}