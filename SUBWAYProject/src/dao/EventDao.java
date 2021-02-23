package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	public List<Map<String, Object>> selectAllEventList() {
		String sql = " SELECT EVENT_ID, "
				+ "EVENT_TITLE, "
				+ "TO_CHAR(EVENT_STARTDATE,'YYYY-MM-DD') AS EVENT_STARTDATE, "
				+ "TO_CHAR(EVENT_ENDDATE,'YYYY-MM-DD') AS EVENT_ENDDATE "
				+ "FROM EVENT";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> selectNowEventList() {
		String sql = " SELECT EVENT_ID, "
				+ "EVENT_TITLE, "
				+ "TO_CHAR(EVENT_STARTDATE,'YYYY-MM-DD') AS EVENT_STARTDATE, "
				+ "TO_CHAR(EVENT_ENDDATE,'YYYY-MM-DD') AS EVENT_ENDDATE "
				+ "FROM EVENT WHERE EVENT_ENDDATE > SYSDATE";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> selectEndEventList() {
		String sql = " SELECT EVENT_ID, "
				+ "EVENT_TITLE, "
				+ "TO_CHAR(EVENT_STARTDATE,'YYYY-MM-DD') AS EVENT_STARTDATE, "
				+ "TO_CHAR(EVENT_ENDDATE,'YYYY-MM-DD') AS EVENT_ENDDATE "
				+ "FROM EVENT WHERE EVENT_ENDDATE < SYSDATE";
		return jdbc.selectList(sql);
	}

	public Map<String, Object> selectInfo(Map<String, Object> param) {
		String sql = " SELECT EVENT_ID, "
				+ "EVENT_TITLE, "
				+ "TO_CHAR(EVENT_STARTDATE,'YYYY-MM-DD') EVENT_STARTDATE, "
				+ "TO_CHAR(EVENT_ENDDATE,'YYYY-MM-DD') EVENT_ENDDATE, "
				+ "EVENT_CONTENTS, "
				+ "TO_CHAR(EVENT_REG_DATE,'YYYY-MM-DD') EVENT_REG_DATE "
				+ "FROM EVENT "
				+ "WHERE EVENT_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add (param.get ("EVENT_ID"));
		return jdbc.selectOne(sql, p);
	}
}
