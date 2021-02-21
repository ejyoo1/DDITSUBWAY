package service;

import util.View;

public class MenuService {
	//메뉴서비스
	private MenuService(){}
	private static MenuService instance;
	public static MenuService getInstance(){
		if(instance == null){
			instance = new MenuService();
		}
		return instance;
	}
	public int menuHome() {
		
		return View.MENU;
	}
}
