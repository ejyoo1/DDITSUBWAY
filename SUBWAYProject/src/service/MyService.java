package service;

import util.View;

public class MyService {
	//마이페이지 서비스
	private MyService(){}
	private static MyService instance;
	public static MyService getInstance(){
		if(instance == null){
			instance = new MyService();
		}
		return instance;
	}
	
	public int start() {
		
		return View.MY;
	}
}
