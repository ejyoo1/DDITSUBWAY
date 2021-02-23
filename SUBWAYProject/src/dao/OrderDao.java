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
    
	//고객 주문목록 조회
	public List<Map<String, Object>> memberOrderList(Object member) {
		//[주문번호, 가맹점명, 메뉴이름, 주문일(회원)]
		String sql = "SELECT A.ORDER_NO"
				+ "        , B.BUYER_NAME"
				+ "        , D.MENU_NM"
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')"
				+ "   FROM ORDERS A INNER JOIN BUYER B      ON(A.BUYER_ID = B.BUYER_ID)"
				+ "                 INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO)"
				+ "                 INNER JOIN MENU D       ON(C.MENU_NO_SEQ = D.MENU_NO_SEQ)"
				+ "   WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(member);
		return jdbc.selectList(sql,param);
	}
	
	//고객 주문목록 상세조회
	public List<Map<String, Object>> memberOrderDetail(Object member, String orderNo){
		//[주문번호, 가맹점명, 메뉴이름, 재료(선택), 수량, 주문일(회원), 주문일(가맹점 확인)]
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
				+ "   WHERE A.MEM_ID = ?"
				+ "   AND   A.ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(member);
		param.add(orderNo);
		return jdbc.selectList(sql, param);
	}
	
	//가맹점목록 리스트
	public List<Map<String, Object>> buyerList() {
		String sql = "SELECT BUYER_NAME"
				+ "   FROM   BUYER";
		return jdbc.selectList(sql);
	}
	
	//주문번호입력
	public int orderNoInsert(String buyer, Object member) {
		String sql = "INSERT INTO ORDERS(ORDER_NO, BUYER_ID, MEM_ID, ORDER_MEMBER_DATE ) "
				+ "               VALUES(TO_CHAR(SYSDATE,'YYYYMMDDHHMISS'),?, ?,SYSDATE)";
		List<Object> param = new ArrayList<>();
		param.add(buyer);
		param.add(member);
		return jdbc.update(sql, param);
	}
	
	//고객 주문번호 리스트
	public List<Map<String, Object>> memberOrderNoList(String buyer) {
		String sql = " SELECT ORDER_NO "
				+ "    FROM ORDERS"
				+ "    WHERE BUYER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(buyer);
		return jdbc.selectList(sql,param);
	}
	
	//메인메뉴입력
	public int mainMenuInsert(int menu, String orderNo, int qty) {
		String sql = "INSERT INTO INFO_ORDER(INFO_ORDER_NO,MENU_NO_SEQ,ORDER_NO,INFO_CART_QTY) "
				+ "               VALUES(INFO_ORDER_NO_SEQ.NEXTVAL,?,?,?)";
		List<Object> param = new ArrayList<>();
		param.add(menu);
		param.add(orderNo);
		param.add(qty);
		return jdbc.update(sql, param);
	}
	
    //고객 주문정보리스트
	public List<Map<String, Object>> memberInfoOrderNoList(String orderNo) {
		String sql = " SELECT INFO_ORDER_NO"
				+ "    FROM INFO_ORDER"
				+ "    WHERE ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderNo);
		return jdbc.selectList(sql, param);
	}
	
	//빵입력
	public int breadInsert(int infoOrderNo,String bread) {
		String sql = "INSERT INTO ADD_INGR(ADD_INGR_NO,INFO_ORDER_NO,INGR_NO)\r\n"
				+ "            VALUES(ADD_INGR_NO_SEQ.NEXTVAL,?,?)";
		List<Object> param = new ArrayList<>();
		param.add(infoOrderNo);
		param.add(bread);
		return jdbc.update(sql, param);
	}
	
	//치즈입력
	public int cheeseInsert(int infoOrderNo, String cheese) {
		String sql = "INSERT INTO ADD_INGR(ADD_INGR_NO,INFO_ORDER_NO,INGR_NO)\r\n"
				+ "            VALUES(ADD_INGR_NO_SEQ.NEXTVAL,?,?)";
		List<Object> param = new ArrayList<>();
		param.add(infoOrderNo);
		param.add(cheese);
		return jdbc.update(sql, param);
	}
	
	//야채입력
	public int vegetableInsert(int infoOrderNo, String vegetable) {
		String sql = "INSERT INTO ADD_INGR(ADD_INGR_NO,INFO_ORDER_NO,INGR_NO)\r\n"
				+ "            VALUES(ADD_INGR_NO_SEQ.NEXTVAL,?,?)";
		List<Object> param = new ArrayList<>();
		param.add(infoOrderNo);
		param.add(vegetable);
		return jdbc.update(sql, param);
	}
	
	//소스입력
	public int sourceInsert(int infoOrderNo, String source) {
		String sql = "INSERT INTO ADD_INGR(ADD_INGR_NO,INFO_ORDER_NO,INGR_NO)\r\n"
				+ "            VALUES(ADD_INGR_NO_SEQ.NEXTVAL,?,?)";
		List<Object> param = new ArrayList<>();
		param.add(infoOrderNo);
		param.add(source);
		return jdbc.update(sql, param);
	}
	
	//주문등록 리스트
	public List<Map<String, Object>> memberOrderRegList(int infoOrderNo) {
		String sql = "SELECT A.BUYER_NAME "
				+ "        , D.MENU_NM "
				+ "        , F.INGR_NAME "
				+ "        , C.INFO_CART_QTY"
				+ "        , TO_CHAR(B.ORDER_MEMBER_DATE,'YY-MM-DD')"
				+ "   FROM BUYER A INNER JOIN ORDERS B ON(B.BUYER_ID = A.BUYER_ID) "
				+ "                INNER JOIN INFO_ORDER C ON(B.ORDER_NO = C.ORDER_NO) "
				+ "                INNER JOIN MENU D ON(C.MENU_NO_SEQ = D.MENU_NO_SEQ) "
				+ "                INNER JOIN ADD_INGR E ON(C.INFO_ORDER_NO = E.INFO_ORDER_NO) "
				+ "                INNER JOIN INGR F ON(E.INGR_NO = F.INGR_NO) "
				+ "   WHERE C.INFO_ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(infoOrderNo);
		return jdbc.selectList(sql, param);
	}
	
    //주문등록 리스트
	public List<Map<String, Object>> memberOrderRegList2(int infoOrderNo) {
		String sql = "SELECT A.BUYER_NAME "
				+ "        , D.MENU_NM "
				+ "        , C.INFO_CART_QTY"
				+ "        , TO_CHAR(B.ORDER_MEMBER_DATE,'YY-MM-DD')"
				+ "   FROM BUYER A INNER JOIN ORDERS B ON(B.BUYER_ID = A.BUYER_ID) "
				+ "                INNER JOIN INFO_ORDER C ON(B.ORDER_NO = C.ORDER_NO) "
				+ "                INNER JOIN MENU D ON(C.MENU_NO_SEQ = D.MENU_NO_SEQ) "
				+ "   WHERE C.INFO_ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(infoOrderNo);
		return jdbc.selectList(sql, param);
	}
	
    //점주 주문조회
	public List<Map<String, Object>> buyerOrderList() {
		String sql = "SELECT A.ORDER_NO "
				+ "        , B.BUYER_NAME "
				+ "        , D.MENU_NM "
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD') "
				+ "   FROM ORDERS A INNER JOIN BUYER B ON(A.BUYER_ID = B.BUYER_ID) "
				+ "                 INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO) "
				+ "                 INNER JOIN MENU D       ON(C.MENU_NO_SEQ = D.MENU_NO_SEQ)";
		return jdbc.selectList(sql);
	}
	
	//점주 주문목록 상세
	public List<Map<String, Object>> buyerOrderDetail(String orderNo) {
		String sql = "SELECT A.ORDER_NO       "
				+ "        , B.BUYER_NAME     "
				+ "        , F.MENU_NM        "
				+ "        , E.INGR_NAME      "
				+ "        , C.INFO_CART_QTY  "
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD') "
				+ "        , TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD') "
				+ "   FROM ORDERS A   INNER JOIN BUYER B      ON(A.BUYER_ID = B.BUYER_ID) "
				+ "                   INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO) "
				+ "                   INNER JOIN ADD_INGR D   ON(C.INFO_ORDER_NO = D.INFO_ORDER_NO) "
				+ "                   INNER JOIN INGR E       ON(D.INGR_NO = E.INGR_NO) "
				+ "                   INNER JOIN MENU F       ON(C.MENU_NO_SEQ = F.MENU_NO_SEQ)"
				+ "   WHERE A.ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderNo);
		return jdbc.selectList(sql,param);
	}

	

}
