package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class FrancDao {
	private FrancDao(){}
	private static FrancDao instance;
	public static FrancDao getInstance(){
		if(instance == null){
			instance = new FrancDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> selectAllFrancList() {
		String sql = " SELECT BUYER_ID, "
				+ "BUYER_NAME, "
				+ "BUYER_COMTEL, "
				+ "BUYER_ZIP, "
				+ "BUYER_ADD FROM BUYER";
		return jdbc.selectList(sql);
	}
	
//	공지사항 삭제
	public int deleteFranc(Map<String, Object> param) {
		String sql = " DELETE FROM BUYER WHERE BUYER_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add (param.get ("BUYER_ID"));
		return jdbc.update (sql, p);
	}
}
