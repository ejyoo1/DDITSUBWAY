package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.MenuDao;
import util.ScanUtil;
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
	private MenuDao menuDao = MenuDao.getInstance();
	
	public int menuselect() {
		
		System.out.println("==============================");
		System.out.println("1. 샌드위치 조회\t 2. 랩 조회\t 3. 샐러드 조회\t 0. 이전(메인)으로");
		System.out.println("==============================");
		System.out.print("입력 >");
		
		int subMenuInput = ScanUtil.nextInt();
		
		switch (subMenuInput){
		case 0:
			return View.LOGIN_MAIN_MENU;
		case 1:
			selSandMenuList();
			break;
		case 2:
			selWrapMenuList();
			break;
		case 3:
			selSallMenuList();
			break;
		}//switch mainMenuInput
		return View.MENU;
	}

	

	private void selSandMenuList() {
		String menUGu = "SD";
		List<Map<String, Object>> selMenuList = menuDao.selectMenuList(menUGu);
		System.out.println("=======================================");
		for(Map<String, Object> menuBoard : selMenuList){
			System.out.println(menuBoard.get("MENU_GU_SEQ")
					+ "\t" + menuBoard.get("MENU_NM"));
		}
		System.out.println("0. 이전으로");
		System.out.println("=======================================");
		
		System.out.print("메뉴번호 입력 >");
		int deNum = ScanUtil.nextInt();
		
		Map<String, Object> selectsdDe = menuDao.selectsandDet(deNum);
		if(deNum == 0){
			selMenuList = menuDao.selectMenuList(menUGu);
		}
		else if(selectsdDe == null){//선택된 메뉴 상세정보 출력
			System.out.println("잘못 입력하셨습니다 다시 입력하세요");
			selSandMenuList();
		}
		else if(((BigDecimal)selectsdDe.get("MENU_GU_SEQ")).intValue() > 0 
			 || ((BigDecimal)selectsdDe.get("MENU_GU_SEQ")).intValue() <= ((BigDecimal)selectsdDe.get("maxgs")).intValue()){
					
			System.out.println("=======================================");
			System.out.println("메뉴번호 \t 메뉴이름 \t\t 메뉴 기본재료 \t\t\t 메뉴가격");
			System.out.println("---------------------------------------");
			System.out.println(selectsdDe.get("MENU_GU_SEQ")
				+ "\t" + selectsdDe.get("MENU_NM")
				+ "\t" + selectsdDe.get("MENU_INGR")
				+ "\t" + selectsdDe.get("MENU_PRICE"));
			System.out.println("=======================================");
			//break;
		}
		
	}
	
	private void selWrapMenuList() {
		String menUGu = "WR";
		List<Map<String, Object>> selMenuList = menuDao.selectMenuList(menUGu);
		System.out.println("=======================================");
		for(Map<String, Object> menuBoard : selMenuList){
			System.out.println(menuBoard.get("MENU_GU_SEQ")
					+ "\t" + menuBoard.get("MENU_NM"));
		}
		System.out.println("0. 이전으로");
		System.out.println("=======================================");
		
		System.out.print("메뉴번호 입력 >");
		int deNum = ScanUtil.nextInt();
		
		Map<String, Object> selectwrDe = menuDao.selectwrapDet(deNum);
		if(deNum == 0){
			selMenuList = menuDao.selectMenuList(menUGu);
		}
		else if(selectwrDe == null){//선택된 메뉴 상세정보 출력
			System.out.println("잘못 입력하셨습니다 다시 입력하세요");
			selSandMenuList();
		}
		else if(((BigDecimal)selectwrDe.get("MENU_GU_SEQ")).intValue() > 0 
			 || ((BigDecimal)selectwrDe.get("MENU_GU_SEQ")).intValue() <= ((BigDecimal)selectwrDe.get("maxgs")).intValue()){
					
			System.out.println("=======================================");
			System.out.println("메뉴번호 \t 메뉴이름 \t\t 메뉴 기본재료 \t\t\t 메뉴가격");
			System.out.println("---------------------------------------");
			System.out.println(selectwrDe.get("MENU_GU_SEQ")
				+ "\t" + selectwrDe.get("MENU_NM")
				+ "\t" + selectwrDe.get("MENU_INGR")
				+ "\t" + selectwrDe.get("MENU_PRICE"));
			System.out.println("=======================================");
			//break;
		}
		
	}

	private void selSallMenuList() {
		String menUGu = "SL";
		List<Map<String, Object>> selMenuList = menuDao.selectMenuList(menUGu);
		System.out.println("=======================================");
		for(Map<String, Object> menuBoard : selMenuList){
			System.out.println(menuBoard.get("MENU_GU_SEQ")
					+ "\t" + menuBoard.get("MENU_NM"));
		}
		System.out.println("0. 이전으로");
		System.out.println("=======================================");
		
		System.out.print("메뉴번호 입력 >");
		int deNum = ScanUtil.nextInt();
		
		Map<String, Object> selectslDe = menuDao.selectsallDet(deNum);
		if(deNum == 0){
			selMenuList = menuDao.selectMenuList(menUGu);
		}
		else if(selectslDe == null){//선택된 메뉴 상세정보 출력
			System.out.println("잘못 입력하셨습니다 다시 입력하세요");
			selSandMenuList();
		}
		else if(((BigDecimal)selectslDe.get("MENU_GU_SEQ")).intValue() > 0 
			 || ((BigDecimal)selectslDe.get("MENU_GU_SEQ")).intValue() <= ((BigDecimal)selectslDe.get("maxgs")).intValue()){
					
			System.out.println("=======================================");
			System.out.println("메뉴번호 \t 메뉴이름 \t\t 메뉴 기본재료 \t\t\t 메뉴가격");
			System.out.println("---------------------------------------");
			System.out.println(selectslDe.get("MENU_GU_SEQ")
				+ "\t" + selectslDe.get("MENU_NM")
				+ "\t" + selectslDe.get("MENU_INGR")
				+ "\t" + selectslDe.get("MENU_PRICE"));
			System.out.println("=======================================");
			//break;
		
		}
	}
}
