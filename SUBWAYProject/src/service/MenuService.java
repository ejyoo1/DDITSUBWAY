package service;

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
		List<Map<String, Object>> selectsand = menuDao.selectsandList();
		List<Map<String, Object>> selectwrap = menuDao.selectwrapList();
		List<Map<String, Object>> selectsall = menuDao.selectsallList();
		List<Map<String, Object>> selectsandDe = menuDao.selectsandDeList();
		List<Map<String, Object>> selectwrapDe = menuDao.selectwrapDeList();
		List<Map<String, Object>> selectsallDe = menuDao.selectsallDeList();
		
		System.out.println("==============================");
		System.out.println("1. 샌드위치 조회\t 2. 랩 조회\t 3. 샐러드 조회\t");
		System.out.println("==============================");
		System.out.println("입력 >");
		
		int subMenuInput = ScanUtil.nextInt();
		
		switch (subMenuInput){
		case 1:
			//샌드위치 메뉴 리스트업
			System.out.println("=======================================");
			for(Map<String, Object> sandBoard : selectsand){
				System.out.println(sandBoard.get("menu_gu")
						+ "\t" + sandBoard.get("menu_nm"));
			}
			System.out.println("0. 이전으로");
			System.out.println("=======================================");
			
			//선택된 샌드위치 메뉴 상세정보 출력
			System.out.println("=======================================");
			for(Map<String, Object> sandDeBoard : selectsandDe){
				System.out.println(sandDeBoard.get("menu_gu")
					+ "\t" + sandDeBoard.get("menu_nm")
					+ "\t" + sandDeBoard.get("MENU_INGR")
					+ "\t" + sandDeBoard.get("MENU_PRICE"));
			}
			System.out.println("=======================================");
			
			break;
		case 2:
			break;
		case 3:
			break;
		}//switch mainMenuInput
		return View.MENU;
	}
}
