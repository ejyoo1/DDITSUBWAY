package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
		default :
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요");
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
			selSallMenuList();
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
	
	
	public int menumanage() {
		List<Map<String, Object>> selAMenuList = menuDao.selAllMenuList();
		
		System.out.println("=======================================");
		System.out.println("순번 \t 메뉴 분류 \t 메뉴번호 \t 메뉴이름 \t\t 메뉴 기본재료 \t\t\t 메뉴가격");
		System.out.println("---------------------------------------");


		int userMenuNum = 1;
		for(Map<String, Object> selAllBoard : selAMenuList){
			System.out.println(userMenuNum
					+ "\t" + selAllBoard.get("MENU_GU")
					+ "\t" + selAllBoard.get("MENU_GU_SEQ")
					+ "\t" + selAllBoard.get("MENU_NM")
					+ "\t" + selAllBoard.get("MENU_INGR")
					+ "\t" + selAllBoard.get("MENU_PRICE"));
			/*
			selAllBoard.put("userMenuNum", userMenuNum);
			userMenuMap.put("MENU_NO", selAllBoard.get("MENU_NO"));
			userMenuMap.put("MENU_GU", selAllBoard.get("MENU_GU"));
			userMenuMap.put("MENU_GU_SEQ", selAllBoard.get("MENU_GU_SEQ"));
			userMenuMap.put("MENU_NM", selAllBoard.get("MENU_NM"));
			userMenuMap.put("MENU_INGR", selAllBoard.get("MENU_INGR"));
			userMenuMap.put("MENU_PRICE", selAllBoard.get("MENU_PRICE"));*/
			
			//selAMenuList.add(selAllBoard);
			userMenuNum ++;
		}
		System.out.println("=======================================");
		System.out.println("==============================");
		System.out.println("1. 메뉴 등록 \t 2. 메뉴 수정 \t 3. 메뉴 삭제 \t 0. 이전(메인)으로");
		System.out.println("==============================");
		System.out.print("입력 >");
		
		int subMenuInput = ScanUtil.nextInt();
		
		switch (subMenuInput){
		case 0:
			return View.LOGIN_MAIN_MENU;
		case 1:
			entMenuList();
			break;
		case 2:
			updMenuList(selAMenuList);
			break;
		case 3:
			delMenuList(selAMenuList);
			break;
		default:
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요");
			break;
		}//switch mainMenuInput
		return View.MENU_MANA;
	}

	
	private void entMenuList() {
		String menuGu = "";
		String menuNM = "";
		String menuIngr = "";
		int menuPri = 0;
		
		System.out.print("메뉴 분류 입력(SD/WR/SL) > ");
		menuGu = ScanUtil.nextLine();
		
		System.out.print("메뉴 이름 입력 > ");
		menuNM = ScanUtil.nextLine();
		
		System.out.print("메뉴 기본(메인) 재료 입력 > ");
		menuIngr = ScanUtil.nextLine();
		
		System.out.print("메뉴 가격 입력 > ");
		menuPri = ScanUtil.nextInt();
		
		int entMenu = menuDao.entMenu(menuGu, menuNM, menuIngr, menuPri);
		if(entMenu == 0){
			System.out.println("등록 실패. 다시 시도해 주세요.");
		}
		else{
			System.out.println("등록 완료");
		}
	}



	private void updMenuList(List<Map<String, Object>> selAMenuList) {
		int menuNo = 0;
		System.out.println("수정할 메뉴번호 입력(MENU_NO) > ");
		menuNo = ScanUtil.nextInt();
		
		int max = selAMenuList.size();
		String menuGu = "";
		String menuNM = "";
		String menuIngr = "";
		int menuPri = 0;
		
		if(menuNo <= 0 || menuNo > max){
			System.out.println("잘못 입력하셨습니다 다시 입력하세요");
			menumanage();
		}
		else if(menuNo > 0 || menuNo <= max){
					
			int selMenuNum = ((BigDecimal)selAMenuList.get(menuNo-1).get("MENU_NO")).intValue();
			System.out.println("해당 번호에 수정할 내용 입력");
			
			System.out.print("메뉴 분류 > ");
			menuGu = ScanUtil.nextLine();
			
			System.out.print("메뉴 이름 > ");
			menuNM = ScanUtil.nextLine();
			
			System.out.print("메뉴 재료 > ");
			menuIngr = ScanUtil.nextLine();
			
			System.out.print("메뉴 가격 > ");
			menuPri = ScanUtil.nextInt();


			int selList = menuDao.updMenu(selMenuNum, menuGu, menuNM, menuIngr, menuPri);
		
		}
		
	}



	private void delMenuList(List<Map<String, Object>> selAMenuList) {
		// TODO Auto-generated method stub
		int menuNo = 0;
		System.out.println("수정할 메뉴번호 입력(MENU_NO) > ");
		menuNo = ScanUtil.nextInt();
		
		int max = selAMenuList.size();
		if(menuNo <= 0 || menuNo > max){
			System.out.println("잘못 입력하셨습니다 다시 입력하세요");
			menumanage();
		}
		else if(menuNo > 0 || menuNo <= max){
						
				int selMenuNum = ((BigDecimal)selAMenuList.get(menuNo-1).get("MENU_NO")).intValue();
				
				int selList = menuDao.delMenu(selMenuNum);
			
		}
	}
}
