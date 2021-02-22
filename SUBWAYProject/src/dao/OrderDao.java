package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import util.ScanUtil;

public class OrderDao {
	
//	생성자 만듬(private)
	private OrderDao(){}
	
//	객체를 보관할 변수 생성
	private static OrderDao instance;
	
//	메서드 호출 시 객체 주소 부여 
	public static OrderDao getInstance(){
		if(instance == null){
			instance = new OrderDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();

	public List<Map<String, Object>> selectOrderList() {
		String sql = "SELECT A.ORDER_NO"
				+ "        , (SELECT B.BUYER_NAME FORM BUYER B B.BUYER_ID = A.BUYER_ID)"
				+ "        , D.MENU_NM"
				+ "        , A.ORDER,MEMBER_DATE "
				+ " FROM ORDER A INNER JOIN INFO_ORDER C ON (C.ORDER_NO = A.ORDER_NO)"
				+ "              INNER JOIN MENU D ON (D.MENU_NO = C.MENU_NO)";
		return jdbc.selectList(sql);
	}
	
	public Map<String, Object> selectOrderOne(){//[주문번호, 가맹점명, 메뉴이름, 재료(선택), 수량, 주문일(회원), 주문일(가맹점 확인)]
		int input = ScanUtil.nextInt();
		int orderNo = input;
		String sql = "SELECT A.ORDER_NO"
				+ "        , (SELECT B.BUYER_NAME FORM BUYER B B.BUYER_ID = A.BUYER_ID)"
				+ "        , D.MENU_NM"
				+ "        , A.ORDER,MEMBER_DATE "
				+ " FROM ORDER A INNER JOIN INFO_ORDER C ON (C.ORDER_NO = A.ORDER_NO)"
				+ "              INNER JOIN MENU D ON (D.MENU_NO = C.MENU_NO)"
				+ " WHERE ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderNo);
		return jdbc.selectOne(sql, param);
	}

}
