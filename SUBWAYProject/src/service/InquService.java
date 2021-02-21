package service;

import util.View;

public class InquService {
	//1:1 문의 관리 서비스 
	private InquService(){}
	private static InquService instance;
	public static InquService getInstance(){
		if(instance == null){
			instance = new InquService();
		}
		return instance;
	}
	
	public int inquList() {
		
		return View.INQU_LIST;
	}
	//고객센터 관리 서비스 
	
}
