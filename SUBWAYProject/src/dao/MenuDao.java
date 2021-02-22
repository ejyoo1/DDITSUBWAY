package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import util.ScanUtil;

public class MenuDao {
	//switch문에 default 꼭 입력 
	private MenuDao(){}
	private static MenuDao instance;
	public static MenuDao getInstance(){
		if(instance == null){
			instance = new MenuDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectsandList(){
		String sql = "select menu_no_seq, menu_gu, menu_gu_seq, menu_nm,"
				+ "MENU_INGR, MENU_PRICE from menu "
				+ "where menu_gu like 'sd%'";
		
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> selectwrapList(){
		String sql = "select menu_no_seq, menu_gu, menu_gu_seq, menu_nm, "
				+ "MENU_INGR, MENU_PRICE from menu "
				+ "where menu_gu like 'wr%'";
		
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> selectsallList(){
		String sql = "select menu_no_seq, menu_gu, menu_gu_seq, menu_nm, "
				+ "MENU_INGR, MENU_PRICE from menu "
				+ "where menu_gu like 'sl%'";
		
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> selectsandDeList(){
		String sql = "select menu_no_seq, menu_gu, menu_gu_seq, menu_nm, "
				+ "MENU_INGR, MENU_PRICE from menu "
				+ "where menu_gu like 'sa%' and menu_gu = '?'";
		System.out.println("샌드위치 메뉴 번호 입력 > ");
		int sandNum = ScanUtil.nextInt();
		
		List<Object> param = new ArrayList<>();
		param.add(sandNum);
		return jdbc.selectList(sql, param);
	}
	public List<Map<String, Object>> selectwrapDeList(){
		String sql = "select menu_no_seq, menu_gu, menu_gu_seq, menu_nm, "
				+ "MENU_INGR, MENU_PRICE from menu "
				+ "where menu_gu like 'sa%' and menu_gu = '?'";
		System.out.println("랩 메뉴 번호 입력 > ");
		int sandNum = ScanUtil.nextInt();
		
		List<Object> param = new ArrayList<>();
		param.add(sandNum);
		return jdbc.selectList(sql, param);
	}
	public List<Map<String, Object>> selectsallDeList(){
		String sql = "select menu_no_seq, menu_gu, menu_gu_seq, menu_nm, "
				+ "MENU_INGR, MENU_PRICE from menu "
				+ "where menu_gu like 'sa%' and menu_gu = '?'";
		System.out.println("샐러드 메뉴 번호 입력 > ");
		int sandNum = ScanUtil.nextInt();
		
		List<Object> param = new ArrayList<>();
		param.add(sandNum);
		return jdbc.selectList(sql, param);
	}
}
