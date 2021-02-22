package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class NotiDao {
	private NotiDao(){}
	private static NotiDao instance;
	public static NotiDao getInstance(){
		if(instance == null){
			instance = new NotiDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> selectImportantNotiList() {
		String sql = " SELECT * FROM NOTICE WHERE NOTICE_IMPOR_CODE = 0 ORDER BY NOTICE_REG_DATE";
		return jdbc.selectList(sql);
	}
	public List<Map<String, Object>> selectNotiList() {
		String sql = "SELECT * FROM NOTICE WHERE NOTICE_IMPOR_CODE = 1 ORDER BY NOTICE_REG_DATE";
		return jdbc.selectList(sql);
	}

}
