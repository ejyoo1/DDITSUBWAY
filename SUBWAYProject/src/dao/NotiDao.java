package dao;

import java.util.ArrayList;
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
	
//	중요 공지사항 목록 조회
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> selectImportantNotiList() {
		String sql = " SELECT * "
				+ "FROM NOTICE "
				+ "WHERE NOTICE_IMPOR_CODE = 0 "
				+ "ORDER BY NOTICE_REG_DATE";
		return jdbc.selectList(sql);
	}
	
//	일반 공지사항 목록 조회
	public List<Map<String, Object>> selectNotiAllList() {
		String sql = " SELECT * "
				+ "FROM NOTICE "
				+ "WHERE NOTICE_IMPOR_CODE = 1 "
				+ "ORDER BY NOTICE_REG_DATE";
		return jdbc.selectList(sql);
	}
	
//	상세 공지사항 조회
	public Map<String, Object> selectNotiList(Map<String, Object> param) {
		String sql = "SELECT * "
				+ "FROM NOTICE "
				+ "WHERE NOTICE_NO = ?";
		List<Object> p = new ArrayList<>();
		p.add (param.get ("NOTICE_NO"));
		return jdbc.selectOne(sql, p);
	}

//	공지사항 등록
	public int insertNotice(Map<String, Object> param) {
		String sql = " INSERT INTO NOTICE("
				+ "NOTICE_NO, "
				+ "MANAGER_ID, "
				+ "NOTICE_TITLE, "
				+ "NOTICE_CONTENTS, "
				+ "NOTICE_REG_DATE, "
				+ "NOTICE_IMPOR_CODE) "
				+ "VALUES(NOTICE_NO_SEQ.NEXTVAL, "
				+ "?, "
				+ "?, "
				+ "?, "
				+ "SYSDATE, "
				+ "?)";
		
		List<Object> p = new ArrayList<>();
		p.add (param.get("MANAGER_ID"));
		p.add (param.get ("NOTICE_TITLE"));
		p.add (param.get ("NOTICE_CONTENTS"));
		p.add (param.get("NOTICE_IMPOR_CODE"));
		return jdbc.update(sql, p);
	}

//	공지사항 수정
	public int updateNotice(Map<String, Object> param) {
		String sql = " UPDATE NOTICE SET NOTICE_TITLE = ?, "
				+ "NOTICE_CONTENTS = ?, "
				+ "NOTICE_IMPOR_CODE = ?, "
				+ "MANAGER_ID = ? "
				+ "WHERE NOTICE_NO = ?";
		
		List<Object> p = new ArrayList<>();
		p.add (param.get ("NOTICE_TITLE"));
		p.add (param.get ("NOTICE_CONTENTS"));
		p.add (param.get ("NOTICE_IMPOR_CODE"));
		p.add (param.get ("MANAGER_ID"));
		p.add (param.get("NOTICE_NO"));
		return jdbc.update(sql, p);
	}

//	공지사항 삭제
	public int deleteBoard(Map<String, Object> param) {
		String sql = " DELETE FROM NOTICE WHERE NOTICE_NO = ?";
		List<Object> p = new ArrayList<>();
		p.add (param.get ("NOTICE_NO"));
		return jdbc.update (sql, p);
	}

}
