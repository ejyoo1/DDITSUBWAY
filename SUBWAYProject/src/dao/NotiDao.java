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

}
