package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class OrderDao {
	
//	싱글톤 패턴 으로 제작
	
//	생성자 만듬(private)
	private OrderDao(){}
	
//	객체를 보관할 변수 생성
	private static OrderDao instance;
	
//	메서드 호출 시 객체 주소 부여 
	public static OrderDao getInstance(){
		if(instance == null){
			instance = new OrderDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();

	public List<Map<String, Object>> selectorderList() {
		String sql = null;
		return jdbc.selectList(sql);
	}

}
