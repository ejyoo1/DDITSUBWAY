package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.FrancDao;
import util.ScanUtil;
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
	
//	가맹점 관리 서비스 객체 획득
	private FrancDao franDao = FrancDao.getInstance();
	
	public int francList() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■가맹점 관리■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("가맹점 아이디     가맹점 이름     가맹점 전화번호     가맹점 우편번호     가맹점 주소");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//		가맹점 전체 목록 출력
		List<Map<String, Object>> franAllList = franDao.selectAllFrancList();
		
//		컬럼 출력
		for(Map<String, Object> fran : franAllList){
			System.out.println(
					"[" + fran.get("BUYER_ID")
					+ "][" + fran.get("BUYER_NAME")
					+ "][" + fran.get("BUYER_COMTEL")
					+ "][" + fran.get("BUYER_ZIP")
					+ "][" + fran.get("BUYER_ADD")
					+ "]");
		}
		
		System.out.println("1.가맹 삭제     2.이전으로(메인 메뉴 페이지 이동)");
		System.out.print("입력>");
		int userInput = ScanUtil.nextInt();
		switch(userInput) {
			case 1: deleteEvent(); return View.FRANC_LIST;
			case 2: return View.LOGIN_MAIN_MENU;
			default: System.out.println("☞ 잘못된 번호입니다 ☜");
		}
		return View.FRANC_LIST;
	}

	public void deleteEvent() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■가맹점 삭제■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print("☞ 삭제할 가맹점 아이디 입력 (대소문자 구분함) > "); 
		String buyerId = ScanUtil.nextLine(); 
		
		Map<String, Object> param = new HashMap<>();
		param.put ("BUYER_ID", buyerId);
//		쿼리 세팅
		int result = franDao.deleteFranc(param);
		if(0 < result) {
			System.out.println ("☞ 가맹점 삭제 성공(가맹점 관리 홈으로 이동합니다.) ☜");
		}else {
			System.out.println ("☞ 가맹점 삭제 실패(가맹점 관리 홈으로 이동합니다.) ☜");
		}
		
	}
}
