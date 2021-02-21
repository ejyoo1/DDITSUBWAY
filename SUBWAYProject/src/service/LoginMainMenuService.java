package service;

import util.ScanUtil;
import util.View;

public class LoginMainMenuService {
	private LoginMainMenuService(){}
	private static LoginMainMenuService instance;
	public static LoginMainMenuService getInstance(){
		if(instance == null){
			instance = new LoginMainMenuService();
		}
		return instance;
	}
	public int loginMainHome() {
		return View.LOGIN_MAIN_MENU;
	}
}
