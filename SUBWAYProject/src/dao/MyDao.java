package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import controller.Controller;

public class MyDao {

	private MyDao() {
	}

	private static MyDao instance;

	public static MyDao getInstance() {
		if (instance == null) {
			instance = new MyDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

	// 고객 비밀번호 조회하는 쿼리
	public Map<String, Object> selectUser(String password) {
		String sql = " SELECT * " 
					+ "FROM MEMBER " + "WHERE MEM_ID = ? "
					+ "AND MEM_PW = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginUser.get("MEM_ID"));
		param.add(password);

		return jdbc.selectOne(sql, param);
	}

	// 가맹점 비밀번호 조회하는 쿼리
	public Map<String, Object> selectStore(String password) {
		String sql = " SELECT * " 
					+ "FROM BUYER " 
					+ "WHERE BUYER_ID = ? "
					+ "AND BUYER_PW = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginUser.get("BUYER_ID"));
		param.add(password);

		return jdbc.selectOne(sql, param);
	}

	// 고객 내정보출력 코드
	public List<Map<String, Object>> selectUserList() {
		String sql = " SELECT MEM_NM " 
					+ " ,MEM_REGNO " + " ,MEM_NUMBER "
					+ " ,MEM_ZIP " + " ,MEM_ADD" 
					+ " FROM MEMBER"
					+ " WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginUser.get("MEM_ID"));
		
		return jdbc.selectList(sql, param);
	}

	// 가맹점 내정보출력 코드
	public List<Map<String, Object>> selectStoreList() {
		String sql = " SELECT BUYER_NAME " 
					+ " ,BUYER_COMTEL " 
					+ " ,BUYER_ZIP "
					+ " ,BUYER_ADD " 
					+ " FROM BUYER" 
					+ "	WHERE BUYER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginUser.get("BUYER_ID"));
		return jdbc.selectList(sql, param);
	}

	// 회원정보 수정
	public int updateUser(Map<String, Object> param) {
		String sql = " UPDATE MEMBER " 
					+ "SET MEM_NM = ?, " 
					+ "MEM_REGNO = ?, "
					+ "MEM_NUMBER = ?, " 
					+ "MEM_ZIP = ?, " 
					+ "MEM_ADD = ? "
					+ "WHERE MEM_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add(param.get("MEM_NM"));
		p.add(param.get("MEM_REGNO"));
		p.add(param.get("MEM_NUMBER"));
		p.add(param.get("MEM_ZIP"));
		p.add(param.get("MEM_ADD"));
		p.add(Controller.loginUser.get("MEM_ID"));

		return jdbc.update(sql, p);
	}

	// 가맹점정보 수정
	public int insertStore(Map<String, Object> param) {
		String sql = " UPDATE BUYER " 
					+ "SET BUYER_NAME = ?,"
					+ "BUYER_COMTEL = ?," 
					+ "BUYER_ZIP = ?," 
					+ "BUYER_ADD = ?"
					+ "WHERE BUYER_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add(param.get("BUYER_NAME"));
		p.add(param.get("BUYER_COMTEL"));
		p.add(param.get("BUYER_ZIP"));
		p.add(param.get("BUYER_ADD"));
		p.add(Controller.loginUser.get("BUYER_ID"));

		return jdbc.update(sql, p);
	}

	// 고객 주문내역조회
	public List<Map<String, Object>> userOrderList() {
		String sql = " SELECT DISTINCT A.INFO_ORDER_NO AS 주문정보번호 "
					+ ",B.MENU_NM AS 메뉴이름 "
					+ ",D.BUYER_NAME AS 가맹점명 "
					+ ",(B.MENU_PRICE * A.INFO_CART_QTY) AS 총주문금액 "
					+ ",TO_CHAR(C.ORDER_MEMBER_DATE,'YYYY-MM-DD')  AS 고객주문날짜"
					+ ",A.INFO_CART_QTY AS 주문수량 "
					+ "FROM INFO_ORDER A INNER JOIN MENU B ON(A.MENU_NO = B.MENU_NO) "
					+ "INNER JOIN ORDERS C ON(A.ORDER_NO = C.ORDER_NO) "
					+ "INNER JOIN BUYER D ON(C.BUYER_ID = D.BUYER_ID) "
					+ "INNER JOIN ADD_INGR E ON (A.INFO_ORDER_NO = E.INFO_ORDER_NO) "
					+ "INNER JOIN INGR F ON(E.INGR_NO = F.INGR_NO) "
					+ "WHERE C.MEM_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add(Controller.loginUser.get("MEM_ID"));

		return jdbc.selectList(sql, p);
	}

	// 고객 주문 내역 상세조회
	public Map<String, Object> orderReadDetail(Map<String, Object> param) {
		String sql = " SELECT A.INFO_ORDER_NO AS 주문정보번호 "
					+ ",B.MENU_NM AS 메뉴이름 "
					+ ",D.BUYER_NAME AS 가맹점명 "
					+ ",(B.MENU_PRICE * A.INFO_CART_QTY) AS 총주문금액 "
					+ ",C.ORDER_MEMBER_DATE AS 고객주문날짜"
					+ ",A.INFO_CART_QTY AS 주문수량 "
					+ ",F.INGR_NAME AS 재료이름"
					+ ",C.ORDER_BUYER_CHOICE AS 가맹점승인 "
					+ "FROM INFO_ORDER A INNER JOIN MENU B ON(A.MENU_NO = B.MENU_NO) "
					+ "INNER JOIN ORDERS C ON(A.ORDER_NO = C.ORDER_NO) "
					+ "INNER JOIN BUYER D ON(C.BUYER_ID = D.BUYER_ID) "
					+ "INNER JOIN ADD_INGR E ON (A.INFO_ORDER_NO = E.INFO_ORDER_NO) "
					+ "INNER JOIN INGR F ON(E.INGR_NO = F.INGR_NO) "
					+ "WHERE C.MEM_ID = ?" + "AND A.INFO_ORDER_NO = ?";
		List<Object> p = new ArrayList<>();
		p.add(Controller.loginUser.get("MEM_ID"));
		p.add(param.get("INFO_ORDER_NO"));

		return jdbc.selectOne(sql, p);
	}

	// 가맹점 주문내역조회
	public List<Map<String, Object>> storeOrderList() {
		String sql = " SELECT DISTINCT A.INFO_ORDER_NO AS 주문정보번호 "
					+ ",B.MENU_NM AS 메뉴이름 "
					+ ",D.BUYER_NAME AS 가맹점명 "
					+ ",TO_CHAR(C.ORDER_MEMBER_DATE,'YYYY-MM-DD')  AS 고객주문날짜"
					+ ",C.ORDER_PRICE AS 총주문금액 "
					+ ",A.INFO_CART_QTY AS 주문수량 "
					+ "FROM INFO_ORDER A INNER JOIN MENU B ON(A.MENU_NO = B.MENU_NO) "
					+ "INNER JOIN ORDERS C ON(A.ORDER_NO = C.ORDER_NO) "
					+ "INNER JOIN BUYER D ON(C.BUYER_ID = D.BUYER_ID) "
					+ "INNER JOIN ADD_INGR E ON (A.INFO_ORDER_NO = E.INFO_ORDER_NO) "
					+ "INNER JOIN INGR F ON(E.INGR_NO = F.INGR_NO) "
					+ "WHERE D.BUYER_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add(Controller.loginUser.get("BUYER_ID"));
		
		return jdbc.selectList(sql, p);
	}

	// 가맹점 주문 내역 상세조회
	public Map<String, Object> orderReadDetail2(Map<String, Object> param) {
		String sql = " SELECT A.INFO_ORDER_NO AS 주문정보번호 "
					+ ",B.MENU_NM AS 메뉴이름 "
					+ ",D.BUYER_NAME AS 가맹점명 "
					+ ",(B.MENU_PRICE * A.INFO_CART_QTY) AS 총주문금액 "
					+ ",TO_CHAR(C.ORDER_MEMBER_DATE,'YYYY-MM-DD')  AS 고객주문날짜"
					+ ",A.INFO_CART_QTY AS 주문수량 "
					+ ",F.INGR_NAME AS 재료이름 "
					+ ",C.ORDER_BUYER_CHOICE AS 가맹점승인 "
					+ "FROM INFO_ORDER A INNER JOIN MENU B ON(A.MENU_NO= B.MENU_NO) "
					+ "INNER JOIN ORDERS C ON(A.ORDER_NO = C.ORDER_NO) "
					+ "INNER JOIN BUYER D ON(C.BUYER_ID = D.BUYER_ID) "
					+ "INNER JOIN ADD_INGR E ON (A.INFO_ORDER_NO = E.INFO_ORDER_NO) "
					+ "INNER JOIN INGR F ON(E.INGR_NO = F.INGR_NO) "
					+ "WHERE C.BUYER_ID = ?" + "AND A.INFO_ORDER_NO = ?";
		List<Object> p = new ArrayList<>();
		p.add(Controller.loginUser.get("BUYER_ID"));
		p.add(param.get("INFO_ORDER_NO"));

		return jdbc.selectOne(sql, p);
	}

	// 고객 재료 출력 코드
	public List<Map<String, Object>> ingrList(Map<String, Object> param) {
		String sql = " SELECT F.INGR_NAME AS 재료이름 "
					+ "FROM INFO_ORDER A INNER JOIN MENU B ON(A.MENU_NO = B.MENU_NO) "
					+ "INNER JOIN ORDERS C ON(A.ORDER_NO = C.ORDER_NO) "
					+ "INNER JOIN BUYER D ON(C.BUYER_ID = D.BUYER_ID) "
					+ "INNER JOIN ADD_INGR E ON (A.INFO_ORDER_NO = E.INFO_ORDER_NO) "
					+ "INNER JOIN INGR F ON(E.INGR_NO = F.INGR_NO) "
					+ "WHERE C.MEM_ID = ?" + "AND A.INFO_ORDER_NO = ?";
		List<Object> p = new ArrayList<>();
		p.add(Controller.loginUser.get("MEM_ID"));
		p.add(param.get("INFO_ORDER_NO"));
		
		return jdbc.selectList(sql, p);
	}

	// 가맹점 재료 출력 코드
	public List<Map<String, Object>> ingrList2(Map<String, Object> param) {
		String sql = " SELECT F.INGR_NAME AS 재료이름 "
					+ "FROM INFO_ORDER A INNER JOIN MENU B ON(A.MENU_NO = B.MENU_NO) "
					+ "INNER JOIN ORDERS C ON(A.ORDER_NO = C.ORDER_NO) "
					+ "INNER JOIN BUYER D ON(C.BUYER_ID = D.BUYER_ID) "
					+ "INNER JOIN ADD_INGR E ON (A.INFO_ORDER_NO = E.INFO_ORDER_NO) "
					+ "INNER JOIN INGR F ON(E.INGR_NO = F.INGR_NO) "
					+ "WHERE D.BUYER_ID = ?" + "AND A.INFO_ORDER_NO = ?";
		List<Object> p = new ArrayList<>();
		p.add(Controller.loginUser.get("BUYER_ID"));
		p.add(param.get("INFO_ORDER_NO"));
		
		return jdbc.selectList(sql, p);
	}

	// 1대1문의 등록
	public int insertInquiry(Map<String, Object> param) {
		String sql = " INSERT INTO INQUIRY(INQUIRY_NO,INQUIRY_TITLE,INQUIRY_EMAIL,INQUIRY_COMETL,INQUIRY_CONTENT,INQUIRY_REG_DATE) "
					+ " values (INQUIRY_NO_SEQ.NEXTVAL, ?, ?, ?, ?,SYSDATE)";
		List<Object> p = new ArrayList<>();
		p.add(param.get("INQUIRY_TITLE"));
		p.add(param.get("INQUIRY_EMAIL"));
		p.add(param.get("INQUIRY_COMETL"));
		p.add(param.get("INQUIRY_CONTENT"));

		return jdbc.update(sql, p);
	}

	// 관리자 1대1문의 조회
	public List<Map<String, Object>> selectInquiryList() {
		String sql = " SELECT *" 
					+ "FROM INQUIRY";
		
		return jdbc.selectList(sql);
	}

	// 관리자 1대1문의 상세조회
	public Map<String, Object> selectInquiry(Map<String, Object> param) {
		String sql = " SELECT *" 
					+ " FROM INQUIRY" 
					+ " WHERE INQUIRY_NO = ? ";
		List<Object> p = new ArrayList<>();
		p.add(param.get("INQUIRY_NO"));
		
		return jdbc.selectOne(sql, p);
	}

	// 관리자 1대1 문의 삭제
	public int deleteBoard(Map<String, Object> param) {
		String sql = " DELETE FROM INQUIRY " 
					+ "WHERE INQUIRY_NO= ?";
		List<Object> p = new ArrayList<>();
		p.add(param.get("INQUIRY_NO"));
		
		return jdbc.update(sql, p);
	}

}
