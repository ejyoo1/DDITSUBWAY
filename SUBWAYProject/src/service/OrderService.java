package service;

import java.math.BigDecimal;
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
	

	/////////////////////////////////////////////////////////////////////////////////
	
	//점주 주문메뉴 메인홈
	public int orderBuyerHome() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■주문 메인 메뉴 ■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("1.주문전체 조회     2.주문등록승인     3.주문등록삭제     4.이전으로");
		System.out.print("☞ 입력>");
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
	
    //점주 주문목록 리스트
	public void buyerOrderList(Object buyer) {
		list : while(true) {
			List<Map<String, Object>> orderList = orderDao.buyerOrderList(buyer);
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■주문목록 리스트■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		    System.out.println("주문번호     가맹점명     메뉴이름     고객주문일자     가격");
		    System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		    for(Map<String, Object> list : orderList) {
		    	System.out.println(list.get("ORDER_NO")
					+ "\t" + list.get("BUYER_NAME")
					+ "\t" + list.get("MENU_NM")
					+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
					+ "\t" + list.get("ORDER_PRICE") + "원");
		    }
		    System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			    System.out.println("1.주문상세     2.이전으로");
				System.out.print("☞ 입력 > ");
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
	
    //점주 주문목록 상세조회
	public void buyerOrderDetail(Object buyer) {
		System.out.print("☞ 조회할 게시글 번호 입력>");
		String orderNo = ScanUtil.nextLine();
		
		List<Map<String, Object>> menuGu2 = orderDao.menuGu2(orderNo);
		String dao = String.valueOf(menuGu2.get(0).get("MENU_GU"));
		if(dao.equals("SD") || dao.equals("SL")) {
			List<Map<String, Object>> orderList = orderDao.buyerOrderDetail(orderNo,buyer);
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("주문번호     가맹점 명      메뉴이름     재료     수량     고객주문일자     가맹점승인일자     가격");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
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
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		}else {
			List<Map<String, Object>> orderList = orderDao.buyerOrderDetail2(orderNo,buyer);
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("주문번호     가맹점 명      메뉴이름     수량     고객주문일자     가맹점승인일자     가격");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
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
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		}
		
			
			System.out.println("1.이전으로(주문목록 리스트)");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				break;

			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}
		}
	
	// 점주 주문등록 승인
	public void buyerOrderRegAccept() {
		System.out.println("■■■■■■■■■■■■■■■■■■■미등록 주문목록■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		list: while (true) {
			// 점주 미등록 주문리스트 출력
			notAcceptOrderList();

			System.out.println("1.상세조회     2.이전으로(주문 메뉴)");
			int input = ScanUtil.nextInt();

			if (input == 1) {
				// 점주 미등록 주문리스트 상세 조회
				notAcceptOrderDetail();
			} else {
				break list;
			}

			// 점주 등록 승인
			regUpdate();

			System.out.println("1.미등록 주문리스트 2.주문 메인 메뉴");
			input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				break;

			case 2:
				break list;

			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}
		}

	}
	
	//점주 미등록 주문리스트
	public void notAcceptOrderList() {
		List<Map<String, Object>> orderList = orderDao.notAcceptOrderList();
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("주문번호     가맹점명     메뉴이름     고개주문일자     가격");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		for(Map<String, Object> list : orderList) {
			System.out.println(list.get("ORDER_NO")
					+ "\t" + list.get("BUYER_NAME")
					+ "\t" + list.get("MENU_NM")
					+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
					+ "\t" + list.get("ORDER_PRICE") + "원");
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

		
	}
	
	//점주 미등록 주문리스트 상세 조회
	public void notAcceptOrderDetail() {
		List<Map<String, Object>> orderList = orderDao.notAcceptOrderDetail();
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
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
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
	}
	
	//점주 등록 승인
	public void regUpdate() {
		System.out.println("등록승인할 주문번호를 입력해주세요.");
		String orderNo = ScanUtil.nextLine();
		int orderReg = orderDao.regUpdate(orderNo);
		System.out.println(orderReg + "개의 주문리스트가 등록되었습니다.");
		
		
	}
	
	//점주 주문등록 삭제 
	public void buyerOrderRegDelete() {
		list: while (true) {

			// 점주 삭제할 주문리스트 출력
			deleteOrderList(Controller.loginUser.get("BUYER_ID"));
            
			System.out.println("1.삭제하기      2.이전으로(주문 메인 메뉴)");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: // 점주 주문등록 삭제
				System.out.println("☞ 삭제할 리스트의 주문번호를 입력해주세요.  > ");
				String orderNo = ScanUtil.nextLine();
				System.out.println("☞ 삭제할 리스트의 주문정보번호를 입력해주세요.  > ");
				int orderInfoNo = ScanUtil.nextInt();
				deleteOrder(orderNo);
				deleteAddIngr(orderInfoNo);
				deleteInfoOrder(orderInfoNo);
				break;
			case 2:
				break list;
			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}
		}
	}
	
	//점주 삭제할 주문목록 리스트
    public void deleteOrderList(Object buyer) {
		List<Map<String, Object>> deleteOrederList = orderDao.deleteOrderList(buyer);
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");	  
		System.out.println("주문번호     주문정보번호     가맹점명     메뉴이름");
	    for(Map<String, Object> list : deleteOrederList) {
	    	System.out.println(list.get("ORDER_NO")
	    			+ "\t" + list.get("INFO_ORDER_NO")
				    + "\t" + list.get("BUYER_NAME")
				    + "\t" + list.get("MENU_NM") 
				    + "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
				    + "\t" + list.get("TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')") + "(점주)"
				    + "\t" + list.get("ORDER_PRICE") + "원") ;
	    }
	    System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
	    System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
    }
		
    //ORDER TABLE 삭제
	public void deleteOrder(String orderNo) {
		int result = orderDao.deleteInfoOrder(orderNo);
	}
	
	//ADD_INGR TABLE 삭제
	public void deleteAddIngr(int orderInfoNo) {
		int result = orderDao.deleteAddIngr(orderInfoNo);
	}
	
	//INFO_ORDER TABLE 삭제
	public void deleteInfoOrder(int orderInfoNo) {
		int result = orderDao.deleteInfoOrder(orderInfoNo);
	}

	/////////////////////////////////////////////////////////////////////////////////
	
	//고객 주문메뉴 메인홈
	public int orderMemberHome(){// 고객용
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■주문 메인 메뉴■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("1.주문목록 조회     2.주문등록     3.이전으로 (메인 메뉴)");
		System.out.print("☞ 입력 > ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return memberOrderList(Controller.loginUser.get("MEM_ID")); // 고객 주문목록 조회
						
		case 2:
			return memberOrderReg(); // 주문등록
						
		case 3:
			return View.LOGIN_MAIN_MENU; //소분류 메뉴로
			
		default :
			System.out.println("잘못입력하였습니다.");
			break;
		
		}
		return View.ORDER_MEMBER_MENU;
	} 
	
	//고객 주문목록 리스트
	public int memberOrderList(Object member) {
		
			List<Map<String, Object>> orderList = orderDao.memberOrderList(member); // 주문목록 조회
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("주문번호     가맹점명     메뉴이름");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			for(Map<String, Object> list : orderList) {
				System.out.println(list.get("ORDER_NO")
						+ "     " + list.get("BUYER_NAME")
						+ "     " + list.get("MENU_NM")
						+ "     " + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)"
						+ "     " + list.get("ORDER_PRICE") + "원");
			}
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("1.주문상세     2.이전으로");
			System.out.print("☞ 입력 > ");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				return memberOrderDetail(Controller.loginUser.get("MEM_ID"));
				
			case 2:
				System.out.println("주문메뉴를 출력합니다.");
				return View.ORDER_MEMBER_MENU;

			default:
				System.out.println("잘못입력하였습니다.");
				break;
			}
			return View.ORDER_MEMBER_MENU;

		}

	
    //고객 주문목록 상세조회
	public int memberOrderDetail(Object member) {
		System.out.print("☞ 조회할 주문번호 입력>");	 			
		String orderNo = ScanUtil.nextLine();
		
		List<Map<String, Object>> orderList2 = orderDao.menuGu(member,orderNo);
		String dao = String.valueOf(orderList2.get(0).get("MENU_GU"));
		
        if(dao.equals("SL") || dao.equals("SD")) {
        	//샌드위치, 샐러드 조회
    		List<Map<String, Object>> orderList = orderDao.elsedetail(member, orderNo);
    		System.out.println("■■■■■■■■■■■■■■■■■■주문내역 상세조회■■■■■■■■■■■■■■■■■■■■■■");
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
    		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
    		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
    		System.out.println("1.이전으로(고객 주문 메뉴)");
        }else {
        	//랩조회
    		List<Map<String, Object>> orderList3 = orderDao.wrdetail(member, orderNo);
    		System.out.println("■■■■■■■■■■■■■■■■■■주문내역 상세조회■■■■■■■■■■■■■■■■■■■■■■");
    		for (Map<String, Object> list : orderList3) {
    			System.out.println(list.get("ORDER_NO") 
    					+ "\t" + list.get("BUYER_NAME") 
    					+ "\t" + list.get("MENU_NM")
    					+ "\t" + list.get("INFO_CART_QTY") + "개" 
    					+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)" 
    					+ "\t" + list.get("TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')") + "(점주)"
    					+ "\t" + list.get("ORDER_PRICE") + "원");
    		}
    		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
    		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
    		System.out.println("1.이전으로(고객 주문 메뉴)");
        	
        	
        }
        System.out.print("☞ 입력>");
        int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.ORDER_MEMBER_LIST;

		default:
			System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MEMBER_MENU;

	}

	//고객 주문등록
	public int memberOrderReg() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■고객 주문등록 ■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("주문메뉴를 선택해주세요");
		System.out.println("1.샌드위치     2.샐러드     3.랩     4.이전으로");
		int input = ScanUtil.nextInt();
		switch(input) {
		
		case 1:
			return sandwich();
			
		case 2:
			return salad();
			
		case 3:
			return rap();
			
		case 4:
			return View.ORDER_MEMBER_MENU;
			
	    default :
	    	System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MEMBER_REG;
		
	}
	
	// 샌드위치-재료선택
	public int sandwich() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■샌드위치 ■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓가맹점명〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		// 가맹점 출력
		buyerList();
		System.out.print("☞  가맹점을 입력해주세요(글자입력) >");

		String buyer = ScanUtil.nextLine();
		
		List<Map<String, Object>> buyerId = orderDao.buyerId(buyer);
		String buyerid = (String) buyerId.get(0).get("BUYER_ID");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		// 메인메뉴선택
		String menuGu = "SD";
		menuNmList(menuGu);
		System.out.print("☞  메뉴를 입력해주세요(글자입력) >");
				
        String menu = ScanUtil.nextLine();
        System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		// 재료션택
		System.out.println("빵을 선택해주세요(숫자입력)");
		System.out.println("1.화이트\t 2.파마산");
		System.out.print("☞ 입력 > ");
		int input1 = ScanUtil.nextInt();
		String bread = null;
		if (input1 == 1) {
			bread = "B001";
		} else {
			bread = "B002";
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("치즈를 선택해주세요(숫자입력)");
		System.out.println("1.아메리칸\t 2.슈레드 ");
		System.out.print("☞ 입력 > ");
		int input2 = ScanUtil.nextInt();
		String cheese = null;
		if (input2 == 1) {
			cheese = "C001";
		} else {
			cheese = "C002";
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("야채를 선택해주세요(숫자입력)");
		System.out.println("1.양상추\t 2.토마토");
		System.out.print("☞ 입력 > ");
		int input3 = ScanUtil.nextInt();
		String vegetable = null;
		if (input3 == 1) {
			vegetable = "V001";
		} else {
			vegetable = "V002";
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("소스를 선택해주세요(숫자입력)");
		System.out.println("1.머스타드\t 2.칠리");
		System.out.print("☞ 입력 > ");
		int input4 = ScanUtil.nextInt();
		String source = null;
		if (input4 == 1) {
			source = "S001";
		} else {
			source = "S002";
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		// 수량 입력, 가격 생성
		System.out.print("☞ 수량을 입력해주세요(숫자입력) >");
		int qty = ScanUtil.nextInt();
		
		List<Map<String, Object>> menuPrice = orderDao.menuPrice(menu);
		int menuprice = ((BigDecimal) menuPrice.get(0).get("MENU_PRICE")).intValue();

		int price = menuprice * qty;

		// 장바구니 테이블에 넣기
		List<Map<String, Object>> menuNo = orderDao.menuNo(menu);
		int menuno = ((BigDecimal) menuNo.get(0).get("MENU_NO")).intValue();

		// 카트테이블 입력
		cartInsert(Controller.loginUser.get("MEM_ID"), menuno, qty);

		// 장바구니번호 출력
		cartNoSelect(Controller.loginUser.get("MEM_ID"));
		System.out.print("☞ 장바구니번호를 입력해주세요(숫자입력) >");
		int cartNo = ScanUtil.nextInt();

		// 카트테이블에 재료정보 입력
		cartBreadInsert(bread, cartNo);
		cartCheeseInsert(cheese, cartNo);
		cartVegetableInsert(vegetable, cartNo);
		cartSourceInsert(source, cartNo);

		System.out.println("1.계속주문     2.주문결정");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.ORDER_MEMBER_REG;
		case 2:
			// 주문번호 생성
			orderTableInsert(buyerid, Controller.loginUser.get("MEM_ID"));
        
			// 주문번호 출력
			memberOrderNoList(buyerid);
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞  주문번호를 입력해주세요(숫자입력)> ");
			String orderNo = ScanUtil.nextLine();
			// ORDER테이블에 ORDER_PRICE 업데이트
			orderTableUpdate(price, orderNo);
			// 카트테이블에서 ADD_INGR/ INFO_ORDER로 입력
			infoOrderInsert(Controller.loginUser.get("MEM_ID"));
			
			//주문정보번호 추가
			addInfoOrder1();
			addInfoOrder2();
			
			addIngrInsert();
			// 최종 고객 주문목록 출력
			finalOrderList(Controller.loginUser.get("MEM_ID"), orderNo);
								
			// 카트 테이블 데이터 삭제
			cartAddDelete();
			cartDelete();
			return View.ORDER_MEMBER_MENU;
		default:
			System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MEMBER_REG;
	}

	
    

	private void addInfoOrder2() {
		int result = orderDao.addInfoOrder2();
		
	}

	private void addInfoOrder1() {
		int result = orderDao.addInfoOrder1();
		
	}

	private void finalOrderList(Object member, String orderNo) {
		List<Map<String, Object>> orderList2 = orderDao.menuGu(member,orderNo);
		String dao = String.valueOf(orderList2.get(0).get("MENU_GU"));
		if(dao.equals("SL") || dao.equals("SD")) {
        	//샌드위치, 샐러드 조회
    		List<Map<String, Object>> orderList = orderDao.elsedetail(member, orderNo);
    		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓주문 목록〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
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
    		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
    		System.out.println("주문이 완료되었습니다.");
        }else {
        	//랩조회
    		List<Map<String, Object>> orderList3 = orderDao.wrdetail(member, orderNo);
    		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓주문 목록〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
    		for (Map<String, Object> list : orderList3) {
    			System.out.println(list.get("ORDER_NO") 
    					+ "\t" + list.get("BUYER_NAME") 
    					+ "\t" + list.get("MENU_NM")
    					+ "\t" + list.get("INFO_CART_QTY") + "개" 
    					+ "\t" + list.get("TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')") + "(고객)" 
    					+ "\t" + list.get("TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')") + "(점주)"
    					+ "\t" + list.get("ORDER_PRICE") + "원");
    		}
    		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
    		System.out.println("주문이 완료되었습니다.");
		
	}
	}

	//메인 메뉴이름 리스트
	public void menuNmList(String menuGu) {
		List<Map<String, Object>> menuList = orderDao.menuNmList(menuGu);
		for(int i = 0; i < menuList.size(); i++) {
			Map<String, Object> list = menuList.get(i);
			System.out.print(i+1 + "." + list.get("MENU_NM") + "     ");
		}
		System.out.println();
		
	}
		
		
	

	// 샐러드-재료선택
	public int salad() {

		// 가맹점 출력
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■샐러드 ■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓가맹점명〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		buyerList();
		System.out.print("☞  가맹점을 입력해주세요(글자입력) > ");

		String buyer = ScanUtil.nextLine();

		List<Map<String, Object>> buyerId = orderDao.buyerId(buyer);
		String buyerid = (String) buyerId.get(0).get("BUYER_ID");

		// 메인메뉴선택
		String menuGu = "SL";
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		menuNmList(menuGu);
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print("☞메뉴를 입력해주세요(글자입력) > ");

		String menu = ScanUtil.nextLine();

		// 재료션택
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("치즈를 선택해주세요(숫자입력)");
		System.out.println("1.아메리칸\t 2.슈레드 ");
		System.out.print("☞ 입력 > ");
		System.out.println();
		int input2 = ScanUtil.nextInt();
		String cheese = null;
		if (input2 == 1) {
			cheese = "C001";
		} else {
			cheese = "C002";
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("야채를 선택해주세요(숫자입력)");
		System.out.println("1.양상추\t 2.토마토");
		System.out.print("☞ 입력 > ");
		System.out.println();
		int input3 = ScanUtil.nextInt();
		String vegetable = null;
		if (input3 == 1) {
			vegetable = "V001";
		} else {
			vegetable = "V002";
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("소스를 선택해주세요(숫자입력)");
		System.out.println("1.머스타드\t 2.칠리");
		System.out.print("☞ 입력 > ");
		int input4 = ScanUtil.nextInt();
		String source = null;
		if (input4 == 1) {
			source = "S001";
		} else {
			source = "S002";
		}

		// 수량 입력, 가격 생성
		System.out.print("☞ 수량을 입력해주세요(숫자입력) > ");
		int qty = ScanUtil.nextInt();

		List<Map<String, Object>> menuPrice = orderDao.menuPrice(menu);
		int menuprice = ((BigDecimal) menuPrice.get(0).get("MENU_PRICE")).intValue();

		int price = menuprice * qty;
		
		

		// 장바구니 테이블에 넣기
		List<Map<String, Object>> menuNo = orderDao.menuNo(menu);
		int menuno = ((BigDecimal) menuNo.get(0).get("MENU_NO")).intValue();

		// 카트테이블 입력
		cartInsert(Controller.loginUser.get("MEM_ID"), menuno, qty);

		// 장바구니번호 출력
		cartNoSelect(Controller.loginUser.get("MEM_ID"));
		System.out.print("☞ 장바구니번호를 입력해주세요 >");
		int cartNo = ScanUtil.nextInt();

		// 카트테이블에 재료정보 입력
		cartCheeseInsert(cheese, cartNo);
		cartVegetableInsert(vegetable, cartNo);
		cartSourceInsert(source, cartNo);

		System.out.println("1.계속주문     2.주문결정");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.ORDER_MEMBER_REG;
		case 2:
			// 주문번호 생성
			orderTableInsert(buyerid, Controller.loginUser.get("MEM_ID"));

			// 주문번호 출력
			memberOrderNoList(buyerid);
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞ 주문번호를 입력해주세요(숫자입력) > ");
			String orderNo = ScanUtil.nextLine();
			// ORDER테이블에 ORDER_PRICE 업데이트
			orderTableUpdate(price, orderNo);
			// 카트테이블에서 ADD_INGR/ INFO_ORDER로 입력
			infoOrderInsert(Controller.loginUser.get("MEM_ID"));
			
			//주문정보번호 추가
			addInfoOrder1();
			addInfoOrder2();
			
			addIngrInsert();
			// 최종 고객 주문목록 출력
			finalOrderList(Controller.loginUser.get("MEM_ID"), orderNo);
			// 카트 테이블 데이터 삭제
			cartAddDelete();
			cartDelete();
			return View.ORDER_MEMBER_MENU;
		default:
			System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MEMBER_REG;
	}

	// 랩-재료x

	public int rap() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■랩 ■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓가맹점명〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		// 가맹점 출력
		buyerList();
		System.out.print("☞  가맹점을 입력해주세요(글자입력) > ");

		String buyer = ScanUtil.nextLine();

		List<Map<String, Object>> buyerId = orderDao.buyerId(buyer);
		String buyerid = (String) buyerId.get(0).get("BUYER_ID");

		// 메인메뉴선택
		String menuGu = "WR";
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		menuNmList(menuGu);
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print("☞ 메뉴를 입력해주세요(글자입력) > ");

		String menu = ScanUtil.nextLine();
		
		// 수량 입력, 가격 생성
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print("☞ 수량을 입력해주세요(숫자입력) > ");
		int qty = ScanUtil.nextInt();

		List<Map<String, Object>> menuPrice = orderDao.menuPrice(menu);
		int menuprice = ((BigDecimal) menuPrice.get(0).get("MENU_PRICE")).intValue();

		int price = menuprice * qty;

		// 장바구니 테이블에 넣기
		List<Map<String, Object>> menuNo = orderDao.menuNo(menu);
		int menuno = ((BigDecimal) menuNo.get(0).get("MENU_NO")).intValue();

		// 카트테이블 입력
		cartInsert(Controller.loginUser.get("MEM_ID"), menuno, qty);
		
		// 카트테이블 입력
		cartInsert(Controller.loginUser.get("MEM_ID"), menuno, qty);

		System.out.println("1.계속주문     2.주문결정");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.ORDER_MEMBER_REG;
		case 2:
			// 주문번호 생성
			orderTableInsert(buyerid, Controller.loginUser.get("MEM_ID"));

			// 주문번호 출력
			memberOrderNoList(buyerid);
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞ 주문번호를 입력해주세요(숫자입력) > ");
			String orderNo = ScanUtil.nextLine();
			// ORDER테이블에 ORDER_PRICE 업데이트
			orderTableUpdate(price, orderNo);
			// 카트테이블에서 ADD_INGR/ INFO_ORDER로 입력
			infoOrderInsert(Controller.loginUser.get("MEM_ID"));
			
			//주문정보번호 추가
			addInfoOrder1();
			addInfoOrder2();
			
			addIngrInsert();
			// 최종 고객 주문목록 출력
			finalOrderList(Controller.loginUser.get("MEM_ID"), orderNo);
			// 카트 테이블 데이터 삭제
			cartAddDelete();
			cartDelete();
			return View.ORDER_MEMBER_MENU;
		default:
			System.out.println("잘못입력하였습니다.");
			break;
		}
		return View.ORDER_MEMBER_REG;
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
		
		
	}
		
	//메인메뉴입력
	public void mainMenuInsert(int menu, String orderNo, int qty) {
		int mainMenu = orderDao.mainMenuInsert(menu,orderNo,qty);
		
		
	}
		
	//빵입력
	public void breadInsert(int infoOrderNo,String bread) {
		int insertBread = orderDao.breadInsert(infoOrderNo,bread);
		
			
	}
		
	//치즈입력
	public void cheeseInsert(int infoOrderNo, String cheese) {
		int insertCheese = orderDao.cheeseInsert(infoOrderNo,cheese);
		
			
	}
		
	//야채입력
	public void vegetableInsert(int infoOrderNo, String vegetable) {
		int insertVegetable = orderDao.vegetableInsert(infoOrderNo,vegetable);
		
			
	}
		
	//소스입력
	public void sourceInsert(int infoOrderNo, String source) {
		int insertSource = orderDao.sourceInsert(infoOrderNo,source);
		
			
	}
	
	//고객 주문번호 리스트
	public void memberOrderNoList(String buyerid) {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓주문번호〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		List<Map<String, Object>> orderNoList = orderDao.memberOrderNoList(buyerid);
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
		List<Map<String, Object>> buyerList = orderDao.buyerList();
			for(int i = 0; i < buyerList.size(); i++) {
				Map<String, Object> list = buyerList.get(i);
				System.out.print(i+1 + "." + list.get("BUYER_NAME") + "  \t");
			}
			System.out.println();
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		}

	
	
	/////////////////////////////////////////////////////////////////////////////////
		
	

	
	
	private void cartAddDelete() {
		int result = orderDao.cartAddDelete();
	}

	private void cartDelete() {
		int result = orderDao.cartDelete();
		
	}

	
	// 카트테이블에서 ADD_INGR로 입력
	private void addIngrInsert() {
		int result = orderDao.addIngrInsert();
	}
	
	// 카트테이블에서 INFO_ORDER로 입력
	public void infoOrderInsert(Object member) {
		int result = orderDao.infoOrderInsert(member);
		
	}

	// ORDER테이블에 ORDER_PRICE 업데이트
	private void orderTableUpdate(int price, String orderNo) {
		int result = orderDao.orderTableUpdate(price,orderNo);
	}

	//주문번호 생성
	public void orderTableInsert(String buyerId, Object member) {
		int result = orderDao.orderTableInsert(buyerId, member);

	}

//		//주문번호 출력
//		memberOrderNoList(buyer);
//		System.out.println("주문번호를 입력해주세요");
//		String orderNo = ScanUtil.nextLine();
//		//주문정보번호 입력 및 출력
//		mainMenuInsert(menu,orderNo,qty);
//		memberInfoOrderNoList(orderNo);
//		System.out.println("주문정보번호를 입력해주세요");
//		int infoOrderNo = ScanUtil.nextInt();
//		//재료 입력
//		breadInsert(infoOrderNo, bread);
//		cheeseInsert(infoOrderNo,cheese);
//		vegetableInsert(infoOrderNo,vegetable);
//		sourceInsert(infoOrderNo,source);
//		//memberOrderRegList(infoOrderNo);
//		System.out.println("주문등록이 완료되었습니다.");
//		break list;
				
//		System.out.println("1.주문등록하기 2. 취소");
//		input = ScanUtil.nextInt();
//		switch(input) {
//		case 1: 
//			orderNoInsert(buyer,Controller.loginUser.get("MEM_ID"),price);
//			memberOrderNoList(buyer);
//			System.out.println("주문번호를 입력해주세요");
//			String orderNo = ScanUtil.nextLine();
//			mainMenuInsert(menu,orderNo,qty);
//			memberInfoOrderNoList(orderNo);
//			System.out.println("주문정보번호를 입력해주세요");
//			int infoOrderNo = ScanUtil.nextInt();
//			breadInsert(infoOrderNo, bread);
//			cheeseInsert(infoOrderNo,cheese);
//			vegetableInsert(infoOrderNo,vegetable);
//			sourceInsert(infoOrderNo,source);
//			memberOrderRegList(infoOrderNo);
//			System.out.println("주문등록이 완료되었습니다.");
//			System.out.println("주문메뉴로 돌아가시겠습니까?");
//			String yes = ScanUtil.nextLine();
//			break;
//		case 2:
//			break;//주문메뉴로 
//		default :
//			System.out.println("잘못입력하였습니다.");
//			break;
//		}
		
	
	
	
    //카트 빵입력
	private void cartBreadInsert(String bread, int cartNo) {
		int result = orderDao.cartBreadInsert(bread, cartNo);
	}
	//카트 치즈입력
	private void cartCheeseInsert(String cheese, int cartNo) {
		int result = orderDao.cartCheeseInsert(cheese, cartNo);
		
	}
	//카트 야채입력
	private void cartVegetableInsert(String vegetable, int cartNo) {
		int result = orderDao.cartVegetabkeInsert(vegetable, cartNo);
		
	}
	//카트 소스입력
	private void cartSourceInsert(String source, int cartNo) {
		int result = orderDao.cartSourceInsert(source, cartNo);
		
	}
    //카트 추가재료 테이블 입력
	public void cartNoSelect(Object member) {
		List<Map<String, Object>> cartNoSelect = orderDao.cartNoSelect(member);
		
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓장바구니 번호〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		for(Map<String, Object> list : cartNoSelect) {
			System.out.println(list.get("CART_NO"));
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
	}
    //카트테이블 입력
	public void cartInsert(Object member, int menuno, int qty) {
		int result = orderDao.cartInsert(member, menuno, qty);
		
			
		
		
	}

	

}