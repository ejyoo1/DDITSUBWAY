package dao;

import util.JDBCUtil;

public class EventDao {
	private EventDao(){}
	private static EventDao instance;
	public static EventDao getInstance(){
		if(instance == null){
			instance = new EventDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
}
