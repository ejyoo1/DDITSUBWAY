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
				+ "        , B.BUYER_NAME"
				+ "        , D.MENU_NM"
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')"
				+ "   FROM ORDERS A INNER JOIN BUYER B      ON(A.BUYER_ID = B.BUYER_ID)"
				+ "                 INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO)"
				+ "                 INNER JOIN MENU D       ON(C.MENU_NO_SEQ = D.MENU_NO_SEQ)";
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> selectOrderList2(String orderNo){//[주문번호, 가맹점명, 메뉴이름, 재료(선택), 수량, 주문일(회원), 주문일(가맹점 확인)]
		String sql = "SELECT  A.ORDER_NO "
				+ "        ,  B.BUYER_NAME "
				+ "        ,  F.MENU_NM "
				+ "        ,  E.INGR_NAME "
				+ "        ,  C.INFO_CART_QTY "
				+ "        ,  TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD') "
				+ "        ,  TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD') "
				+ "   FROM ORDERS A   INNER JOIN BUYER B      ON(A.BUYER_ID = B.BUYER_ID) "
				+ "                   INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO) "
				+ "                   INNER JOIN ADD_INGR D   ON(C.INFO_ORDER_NO = D.INFO_ORDER_NO) "
				+ "                   INNER JOIN INGR E       ON(D.INGR_NO = E.INGR_NO) "
				+ "                   INNER JOIN MENU F       ON(C.MENU_NO_SEQ = F.MENU_NO_SEQ)"
				+ "   WHERE A.ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderNo);
		return jdbc.selectList(sql, param);
	}

	public List<Map<String, Object>> slectBuyerList() {
		String sql = "SELECT BUYER_NAME"
				+ "   FROM   BUYER";
		return jdbc.selectList(sql);
	}

}
