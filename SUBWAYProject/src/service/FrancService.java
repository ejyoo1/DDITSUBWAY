package service;

import util.View;

public class FrancService {
// 가맹점 관리 서비스
	private FrancService(){}
	private static FrancService instance;
	public static FrancService getInstance(){
		if(instance == null){
			instance = new FrancService();
		}
		return instance;
	}
	public int francList() {
		
		return View.EVENT_LIST;
	}
}
