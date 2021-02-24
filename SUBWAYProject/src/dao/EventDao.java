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
				+ "NVL(TO_CHAR(EVENT_ENDDATE,'YYYY-MM-DD'),'진행중') AS EVENT_ENDDATE "
				+ "FROM EVENT "
				+ "ORDER BY EVENT_ID";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> selectNowEventList() {
		String sql = " SELECT EVENT_ID, "
				+ "EVENT_TITLE, "
				+ "EVENT_STARTDATE, "
				+ "EVENT_ENDDATE "
				+ "FROM ("
						+ "SELECT EVENT_ID, "
						+ "EVENT_TITLE, "
						+ "TO_CHAR(EVENT_STARTDATE,'YYYY-MM-DD') AS EVENT_STARTDATE, "
						+ "NVL(TO_CHAR(EVENT_ENDDATE,'YYYY-MM-DD'),'진행중') AS EVENT_ENDDATE FROM EVENT) "
				+ "WHERE EVENT_ENDDATE >= TO_CHAR(SYSDATE,'YYYY-MM-DD') "
				+ "OR EVENT_ENDDATE = '진행중'";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> selectEndEventList() {
		String sql = " SELECT EVENT_ID, "
				+ "EVENT_TITLE, "
				+ "TO_CHAR(EVENT_STARTDATE,'YYYY-MM-DD') AS EVENT_STARTDATE, "
				+ "NVL(TO_CHAR(EVENT_ENDDATE,'YYYY-MM-DD'),'진행중') AS EVENT_ENDDATE "
				+ "FROM EVENT WHERE EVENT_ENDDATE <= SYSDATE ORDER BY EVENT_ID";
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
//이벤트 등록
	public int insertEvent(Map<String, Object> param) {
		String sql = " INSERT INTO EVENT ("
				+ "EVENT_ID, MANAGER_ID, EVENT_TITLE, EVENT_STARTDATE, EVENT_ENDDATE, EVENT_CONTENTS, EVENT_REG_DATE) "
				+ "VALUES("
				+ "EVENT_ID_SEQ.NEXTVAL, "//EVENT_ID
				+ "?, "//MANAGER_ID
				+ "?, "//EVENT_TITLE
				+ "?, "//EVENT_STARTDATE
				+ "?, "//EVENT_ENDDATE
				+ "?, "//EVENT_CONTENTS
				+ "SYSDATE)";//EVENT_REG_DATE
		List<Object> p = new ArrayList<>();
		p.add (param.get("MANAGER_ID"));
		p.add (param.get ("EVENT_TITLE"));
		p.add (param.get ("EVENT_STARTDATE"));
		p.add (param.get("EVENT_ENDDATE"));
		p.add (param.get("EVENT_CONTENTS"));
		return jdbc.update(sql, p);
	}

	public int updateEvent(Map<String, Object> param) {
		String sql = " UPDATE EVENT SET "
				+ "EVENT_TITLE = ?, "
				+ "EVENT_STARTDATE = ?, "
				+ "EVENT_ENDDATE = ?, "
				+ "EVENT_CONTENTS = ?, "
				+ "MANAGER_ID = ? "
				+ "WHERE EVENT_ID = ?";
		
		List<Object> p = new ArrayList<>();
		p.add (param.get ("EVENT_TITLE"));
		p.add (param.get ("EVENT_STARTDATE"));
		p.add (param.get ("EVENT_ENDDATE"));
		p.add (param.get("EVENT_CONTENTS"));
		p.add (param.get("MANAGER_ID"));
		p.add (param.get ("EVENT_ID"));
		return jdbc.update(sql, p);
	}

	public int deleteEvent(Map<String, Object> param) {
		String sql = " DELETE FROM EVENT WHERE EVENT_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add (param.get ("EVENT_ID"));
		return jdbc.update (sql, p);
	}
}
