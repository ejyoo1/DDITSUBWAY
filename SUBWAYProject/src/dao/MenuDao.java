package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.MenuService;
import util.JDBCUtil;
import util.ScanUtil;
import util.View;

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
	
	public List<Map<String, Object>> selectMenuList(String menuGu){
		String sql = "select MENU_NO_SEQ, MENU_GU, MENU_GU_SEQ, MENU_NM,"
				+ "MENU_INGR, MENU_PRICE from menu "
				+ "where MENU_GU = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(menuGu);
		return jdbc.selectList(sql, param);
	}
	
	public Map<String, Object> selectsandDet(int detSDNum){
		
		String sql = " select MENU_NO_SEQ, MENU_GU, MENU_GU_SEQ, MENU_NM, MENU_INGR, MENU_PRICE, "
				+ "(select  max(MENU_GU_SEQ) from menu where MENU_GU = 'SD') as maxgs "
				+ "from menu "
				+ "where MENU_GU = 'SD' and MENU_GU_SEQ = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(detSDNum);
		return jdbc.selectOne(sql, param);
	}
	
	public Map<String, Object> selectwrapDet(int detWRNum){
		String sql = " select MENU_NO_SEQ, MENU_GU, MENU_GU_SEQ, MENU_NM, MENU_INGR, MENU_PRICE, "
				+ "(select  max(MENU_GU_SEQ) from menu where MENU_GU = 'SD') as maxgs "
				+ "from menu "
				+ "where MENU_GU = 'WR' and MENU_GU_SEQ = ?";

		List<Object> param = new ArrayList<>();
		param.add(detWRNum);
		return jdbc.selectOne(sql, param);
	}
	
	public Map<String, Object> selectsallDet(int detSLNum){
		String sql = " select MENU_NO_SEQ, MENU_GU, MENU_GU_SEQ, MENU_NM, MENU_INGR, MENU_PRICE, "
				+ "(select  max(MENU_GU_SEQ) from menu where MENU_GU = 'SD') as maxgs "
				+ "from menu "
				+ "where MENU_GU = 'SL' and MENU_GU_SEQ = ?";

		List<Object> param = new ArrayList<>();
		param.add(detSLNum);
		return jdbc.selectOne(sql, param);
	}
}
