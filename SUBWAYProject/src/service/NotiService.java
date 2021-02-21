package service;

import util.View;

public class NotiService {
	//공지 서비스 
	private NotiService(){}
	private static NotiService instance;
	public static NotiService getInstance(){
		if(instance == null){
			instance = new NotiService();
		}
		return instance;
	}
	public int noticeHome() {
		
		return View.NOTICE_LIST;
	}
	
	
}
