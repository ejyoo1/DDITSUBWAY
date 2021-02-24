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
		//[주문번호, 가맹점명, 메뉴이름, 주문일(회원), 가격(메뉴가격 * 주문수량)]
		String sql = "SELECT A.ORDER_NO"
				+ "        , B.BUYER_NAME"
				+ "        , D.MENU_NM"
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')"
				+ "        , A.ORDER_PRICE  "
				+ "   FROM ORDERS A INNER JOIN BUYER B      ON(A.BUYER_ID = B.BUYER_ID)"
				+ "                 INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO)"
				+ "                 INNER JOIN MENU D       ON(C.MENU_NO = D.MENU_NO)"
				+ "   WHERE A.MEM_ID = ?"
				+ "   ORDER BY A.ORDER_NO";
		List<Object> param = new ArrayList<>();
		param.add(member);
		return jdbc.selectList(sql,param);
	}
	
	//고객 주문목록 상세조회
	public List<Map<String, Object>> memberOrderDetail(Object member, String orderNo){
		//[주문번호, 가맹점명, 메뉴이름, 재료(선택), 수량, 주문일(회원), 주문일(가맹점 확인), 가격(메뉴가격 * 주문수량)]
		String sql = "SELECT  A.ORDER_NO "
				+ "        ,  B.BUYER_NAME "
				+ "        ,  F.MENU_NM "
				+ "        ,  E.INGR_NAME "
				+ "        ,  C.INFO_CART_QTY "
				+ "        ,  TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD') "
				+ "        ,  TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD') "
				+ "        ,  A.ORDER_PRICE "
				+ "   FROM ORDERS A   INNER JOIN BUYER B      ON(A.BUYER_ID = B.BUYER_ID) "
				+ "                   INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO) "
				+ "                   INNER JOIN ADD_INGR D   ON(C.INFO_ORDER_NO = D.INFO_ORDER_NO) "
				+ "                   INNER JOIN INGR E       ON(D.INGR_NO = E.INGR_NO) "
				+ "                   INNER JOIN MENU F       ON(C.MENU_NO = F.MENU_NO)"
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
	public int orderNoInsert(String buyer, Object member, int price) {
		String sql = "INSERT INTO ORDERS(ORDER_NO, BUYER_ID, MEM_ID, ORDER_PRICE, ORDER_MEMBER_DATE) "
				+ "               VALUES(TO_CHAR(SYSDATE,'YYYYMMDDHHMISS'),?, ?, ?,SYSDATE)";
		List<Object> param = new ArrayList<>();
		param.add(buyer);
		param.add(member);
		param.add(price);
		return jdbc.update(sql, param);
	}
	
	//고객 주문번호 리스트
	public List<Map<String, Object>> memberOrderNoList(String buyer) {
		String sql = " SELECT ORDER_NO "
				+ "    FROM ORDERS"
				+ "    WHERE BUYER_ID = ?"
				+ "    ORDER BY ORDER_NO";
		List<Object> param = new ArrayList<>();
		param.add(buyer);
		return jdbc.selectList(sql,param);
	}
	
	//메인메뉴입력
	public int mainMenuInsert(int menu, String orderNo, int qty) {
		String sql = "INSERT INTO INFO_ORDER(INFO_ORDER_NO,MENU_NO,ORDER_NO,INFO_CART_QTY) "
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
				+ "        , B.ORDER_PRICE"
				+ "   FROM BUYER A INNER JOIN ORDERS B ON(B.BUYER_ID = A.BUYER_ID) "
				+ "                INNER JOIN INFO_ORDER C ON(B.ORDER_NO = C.ORDER_NO) "
				+ "                INNER JOIN MENU D ON(C.MENU_NO = D.MENU_NO) "
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
				+ "        , B.ORDER_PRICE"
				+ "   FROM BUYER A INNER JOIN ORDERS B ON(B.BUYER_ID = A.BUYER_ID) "
				+ "                INNER JOIN INFO_ORDER C ON(B.ORDER_NO = C.ORDER_NO) "
				+ "                INNER JOIN MENU D ON(C.MENU_NO = D.MENU_NO) "
				+ "   WHERE C.INFO_ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(infoOrderNo);
		return jdbc.selectList(sql, param);
	}
	
    //점주 주문조회
	public List<Map<String, Object>> buyerOrderList(Object buyer) {
		String sql = "SELECT A.ORDER_NO "
				+ "        , B.BUYER_NAME "
				+ "        , D.MENU_NM "
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD') "
				+ "        , A.ORDER_PRICE"
				+ "   FROM ORDERS A INNER JOIN BUYER B ON(A.BUYER_ID = B.BUYER_ID) "
				+ "                 INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO) "
				+ "                 INNER JOIN MENU D       ON(C.MENU_NO = D.MENU_NO)"
				+ "   WHERE B.BUYER_ID = ?"
				+ "   ORDER BY A.ORDER_NO ";
		List<Object> param = new ArrayList<>();
		param.add(buyer);
		return jdbc.selectList(sql,param);
	}
	
	//점주 주문목록 상세
	public List<Map<String, Object>> buyerOrderDetail(String orderNo, Object buyer) {
		String sql = "SELECT A.ORDER_NO       "
				+ "        , B.BUYER_NAME     "
				+ "        , F.MENU_NM        "
				+ "        , E.INGR_NAME      "
				+ "        , C.INFO_CART_QTY  "
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD') "
				+ "        , TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD') "
				+ "        , A.ORDER_PRICE"
				+ "   FROM ORDERS A   INNER JOIN BUYER B      ON(A.BUYER_ID = B.BUYER_ID) "
				+ "                   INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO) "
				+ "                   INNER JOIN ADD_INGR D   ON(C.INFO_ORDER_NO = D.INFO_ORDER_NO) "
				+ "                   INNER JOIN INGR E       ON(D.INGR_NO = E.INGR_NO) "
				+ "                   INNER JOIN MENU F       ON(C.MENU_NO = F.MENU_NO)"
				+ "   WHERE A.ORDER_NO = ?"
				+ "   AND   B.BUYER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderNo);
		param.add(buyer);
		return jdbc.selectList(sql,param);
	}
    
	//점주 미등록 주문리스트
	public List<Map<String, Object>> notAcceptOrderList() {
		String sql = "SELECT A.ORDER_NO\r\n"
				+ "        , B.BUYER_NAME\r\n"
				+ "        , D.MENU_NM\r\n"
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')\r\n"
				+ "        , A.ORDER_PRICE"
				+ "   FROM ORDERS A INNER JOIN BUYER B ON(A.BUYER_ID = B.BUYER_ID)\r\n"
				+ "                 INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO)\r\n"
				+ "                 INNER JOIN MENU D       ON(C.MENU_NO = D.MENU_NO)\r\n"
				+ "   WHERE A.ORDER_BUYER_CHOICE IS NULL"
				+ "   ORDER BY A.ORDER_NO ";
		return jdbc.selectList(sql);
	}
	
	//점주 등록 승인
	public int regUpdate(String orderNo) {
		String sql = "UPDATE ORDERS\r\n"
				+ "   SET    ORDER_BUYER_CHOICE = SYSDATE\r\n"
				+ "   WHERE  ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderNo);
		return jdbc.update(sql, param);
	}
	
	//점주 미등록 주문리스트 상세 조회
	public List<Map<String, Object>> notAcceptOrderDetail() {
		String sql = "SELECT A.ORDER_NO      \r\n"
				+ "        , B.BUYER_NAME    \r\n"
				+ "        , F.MENU_NM       \r\n"
				+ "        , E.INGR_NAME     \r\n"
				+ "        , C.INFO_CART_QTY \r\n"
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')\r\n"
				+ "        , TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')\r\n"
				+ "        , A.ORDER_PRICE "
				+ "  FROM ORDERS A   INNER JOIN BUYER B      ON(A.BUYER_ID = B.BUYER_ID)\r\n"
				+ "                  INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO)\r\n"
				+ "                  INNER JOIN ADD_INGR D   ON(C.INFO_ORDER_NO = D.INFO_ORDER_NO)\r\n"
				+ "                  INNER JOIN INGR E       ON(D.INGR_NO = E.INGR_NO)\r\n"
				+ "                  INNER JOIN MENU F       ON(C.MENU_NO = F.MENU_NO)\r\n"
				+ "  WHERE A.ORDER_BUYER_CHOICE IS NULL"
				+ "  ORDER BY A.ORDER_NO ";
		return jdbc.selectList(sql);
	}
    
	//점주 삭제할 주문목록 리스트
	public List<Map<String, Object>> deleteOrderList(Object buyer) {
		String sql = "SELECT A.ORDER_NO \r\n "
				+ "        , C.INFO_ORDER_NO "
				+ "        , B.BUYER_NAME\r\n"
				+ "        , D.MENU_NM \r\n"
				+ "        , TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD')\r\n"
				+ "        , TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD')\r\n"
				+ "        , A.ORDER_PRICE\r\n"
				+ "   FROM ORDERS A INNER JOIN BUYER B ON(A.BUYER_ID = B.BUYER_ID)\r\n"
				+ "              INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO)\r\n"
				+ "              INNER JOIN MENU D       ON(C.MENU_NO = D.MENU_NO)\r\n"
				+ "   WHERE B.BUYER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(buyer);
		return jdbc.selectList(sql, param);
	}
	
	//ORDER TABLE 삭제
	public int deleteInfoOrder(String orderNo) {
		String sql = "DELETE FROM ORDERS  "
				+ "   WHERE  ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderNo);
		return jdbc.update(sql, param);
	}
	
    //INFO_ORDER TABLE 삭제
	public int deleteInfoOrder(int orderInfoNo) {
		String sql = "DELETE FROM INFO_ORDER "
				+ "   WHERE INFO_ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderInfoNo);
		return jdbc.update(sql, param);
	}
	
    //ADD_INGR TABLE 삭제
	public int deleteAddIngr(int orderInfoNo) {
		String sql = "DELETE FROM ADD_INGR "
				+ "   WHERE  INFO_ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderInfoNo);
		return jdbc.update(sql, param);
	}
    //카트테이블 입력
	public int cartInsert(Object member, int menu, int qty) {
		String sql = "INSERT INTO CART(CART_NO, MEM_ID, MENU_NO, CART_QTY)\r\n"
				+ "               VALUES(CART_NO_SEQ.NEXTVAL, ?,?,?)";
		List<Object> param = new ArrayList<>();
		param.add(member);
		param.add(menu);
		param.add(qty);
		return jdbc.update(sql, param);
	}
    
	//장바구니 번호출력
	public List<Map<String, Object>> cartNoSelect(Object member) {
		String sql = "SELECT CART_NO "
				+ "   FROM   CART "
				+ "   WHERE  MEM_ID = ? "
				+ "   ORDER BY CART_NO";
		List<Object> param = new ArrayList<>();
		param.add(member);
		return jdbc.selectList(sql, param);
	}
    
	//카트빵입력
	public int cartBreadInsert(String bread, int cartNo) {
		String sql = "INSERT INTO CART_ADD_INGR(CART_ADD_NO, INGR_NO, CART_NO)\r\n"
				+ "               VALUES(CART_ADD_NO_SEQ.NEXTVAL, ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(bread);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	
	//카트치즈입력
	public int cartCheeseInsert(String cheese, int cartNo) {
		String sql = "INSERT INTO CART_ADD_INGR(CART_ADD_NO, INGR_NO, CART_NO)\r\n"
				+ "               VALUES(CART_ADD_NO_SEQ.NEXTVAL, ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(cheese);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	
    //카트야채입력
	public int cartVegetabkeInsert(String vegetable, int cartNo) {
		String sql = "INSERT INTO CART_ADD_INGR(CART_ADD_NO, INGR_NO, CART_NO)\r\n"
				+ "               VALUES(CART_ADD_NO_SEQ.NEXTVAL, ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(vegetable);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	
    //카트소스입력
	public int cartSourceInsert(String source, int cartNo) {
		String sql = "INSERT INTO CART_ADD_INGR(CART_ADD_NO, INGR_NO, CART_NO)\r\n"
				+ "               VALUES(CART_ADD_NO_SEQ.NEXTVAL, ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(source);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	
	//주문번호 생성
	public int orderTableInsert(String buyer, Object member) {
		String sql = "INSERT INTO ORDERS(ORDER_NO, BUYER_ID, MEM_ID, ORDER_MEMBER_DATE )\r\n"
				+ "               VALUES(TO_CHAR(SYSDATE,'YYYYMMDDHHMISS'), ?, ?,SYSDATE)";
		List<Object> param = new ArrayList<>();
		param.add(buyer);
		param.add(member);
		return jdbc.update(sql, param);
	}

	public int orderTableUpdate(int price, String orderNo) {
		String sql = "UPDATE ORDERS\r\n"
				+ "   SET    ORDER_PRICE = ?"
				+ "   WHERE  ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(price);
		param.add(orderNo);
		return jdbc.update(sql, param);
	}
	
	// 카트테이블에서 INFO_ORDER로 입력
	public int infoOrderInsert(Object member) {
		String sql = "INSERT INTO INFO_ORDER(INFO_ORDER_NO,MENU_NO,ORDER_NO,INFO_CART_QTY)\r\n"
				+ "   SELECT INFO_ORDER_NO_SEQ.NEXTVAL,MENU_NO,(SELECT MAX(ORDER_NO) FROM ORDERS), CART_QTY\r\n"
				+ "   FROM   CART WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(member);
		return jdbc.update(sql, param);
	}
	
	// 카트테이블에서 ADD_INGR로 입력
	public int addIngrInsert() {
		String sql = "INSERT INTO ADD_INGR(ADD_INGR_NO, INFO_ORDER_NO, INGR_NO)\r\n"
				+ "   SELECT ADD_INGR_NO_SEQ.NEXTVAL, (SELECT MAX(INFO_ORDER_NO) FROM INFO_ORDER), INGR_NO\r\n"
				+ "   FROM   CART_ADD_INGR A WHERE A.CART_NO = (SELECT B.CART_NO FROM CART B WHERE B.CART_NO = A.CART_NO)";
		return jdbc.update(sql);
	}
    
	// 최종 고객 주문등록 출력리스트
	public List<Map<String, Object>> finalOrderList(Object member, String orderNo) {
		String sql = "SELECT  A.ORDER_NO "
				+ "        ,  B.BUYER_NAME "
				+ "        ,  F.MENU_NM "
				+ "        ,  E.INGR_NAME "
				+ "        ,  C.INFO_CART_QTY "
				+ "        ,  TO_CHAR(A.ORDER_MEMBER_DATE,'YY-MM-DD') "
				+ "        ,  TO_CHAR(A.ORDER_BUYER_CHOICE,'YY-MM-DD') "
				+ "        ,  A.ORDER_PRICE "
				+ "   FROM ORDERS A   INNER JOIN BUYER B      ON(A.BUYER_ID = B.BUYER_ID) "
				+ "                   INNER JOIN INFO_ORDER C ON(A.ORDER_NO = C.ORDER_NO) "
				+ "                   INNER JOIN ADD_INGR D   ON(C.INFO_ORDER_NO = D.INFO_ORDER_NO) "
				+ "                   INNER JOIN INGR E       ON(D.INGR_NO = E.INGR_NO) "
				+ "                   INNER JOIN MENU F       ON(C.MENU_NO = F.MENU_NO)"
				+ "   WHERE A.MEM_ID = ?"
				+ "   AND   A.ORDER_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(member);
		param.add(orderNo);
		return jdbc.selectList(sql, param);
	}

	public int cartAddDelete() {
		String sql = "DELETE FROM CART_ADD_INGR";
		return jdbc.update(sql);
	}

	public int cartDelete() {
		String sql = "DELETE FROM CART";
		return jdbc.update(sql);
	}

	

	

}
