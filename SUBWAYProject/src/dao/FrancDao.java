package dao;

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
}
