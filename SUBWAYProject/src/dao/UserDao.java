package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class UserDao {

	private UserDao(){}
	private static UserDao instance;
	public static UserDao getInstance(){
		if(instance == null){
			instance = new UserDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public int insertUser(Map<String, Object> param,String table){
		String sql ="";
		List<Object> p = new ArrayList<>();
		if(table.equals("MEMBER")) {
			sql = " INSERT INTO "+ table
					+ "(MEM_ID, MEM_PW, MEM_NM, MEM_REGNO, MEM_NUMBER, MEM_ZIP, MEM_ADD)"
					+ " VALUES(?, ?, ?, ?, ?, ?,?)";
			
			p.add(param.get("MEM_ID"));
			p.add(param.get("MEM_PW"));
			p.add(param.get("MEM_NM"));
			p.add(param.get("MEM_REGNO"));
			p.add(param.get("MEM_NUMBER"));
			p.add(param.get("MEM_ZIP"));
			p.add(param.get("MEM_ADD"));
		}else if(table.equals("BUYER")) {
			sql = " INSERT INTO "+ table
					+ "(BUYER_ID, BUYER_PW, BUYER_NAME, BUYER_COMTEL, BUYER_ZIP, BUYER_ADD)"
					+ " VALUES(?, ?, ?, ?, ?, ?)";
			
			p.add(param.get("BUYER_ID"));
			p.add(param.get("BUYER_PW"));
			p.add(param.get("BUYER_NAME"));
			p.add(param.get("BUYER_COMTEL"));
			p.add(param.get("BUYER_ZIP"));
			p.add(param.get("BUYER_ADD"));
		}else {
			System.out.println("정보없음");
		}
		
		return jdbc.update(sql, p);
	}

	public Map<String, Object> selectUser(String userId, String password, String table, String idColumn, String pwColumn) {
		String sql = "select * from "+ table +" where "+idColumn+" = ? and "+pwColumn+" = ?";
		List<Object> param = new ArrayList<>();
		param.add(userId);
		param.add(password);
		
		return jdbc.selectOne(sql, param);
	}
	
//  아이디 중복검사 체크
	public Map<String, Object> selectUser(String memId, String table, String idColumn) {
		String sql = " SELECT " + idColumn + " FROM " + table + " WHERE " + idColumn + " = ?";
		List<Object> param = new ArrayList<>();
		param.add(memId);
	
		return jdbc.selectOne(sql, param);
	}
}