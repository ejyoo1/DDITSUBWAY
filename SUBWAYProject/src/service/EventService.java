package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.EventDao;
import util.ScanUtil;
import util.VerifiedUtil;
import util.View;

public class EventService {
//	1.전체조회, 2.현재 진행중 이벤트 조회, 3.종료된 이벤트 조회 출력 전용 변수 생성
	public static int eventListCode = 1;
	//이벤트 서비스
	private EventService(){}
	private static EventService instance;
	public static EventService getInstance(){
		if(instance == null){
			instance = new EventService();
		}
		return instance;
	}
	
	private EventDao eventDao = EventDao.getInstance();
	private VerifiedUtil verifiedUtil = VerifiedUtil.getInstance();
	
	public int eventList() {//1. 전체조회 2. 현재 진행중인 이벤트 조회 3. 종료된 이벤트 조회
		
//		전체 목록 출력
		if(eventListCode == 1) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■전체 이벤트■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("     이벤트 번호     이벤트 제목     이벤트 시작날짜     이벤트 종료 날짜");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			List<Map<String, Object>> eventAllList = eventDao.selectAllEventList();
			for(Map<String, Object> event : eventAllList){
				System.out.println(
						"["		+ event.get("EVENT_ID")
						+ "][" 	+ event.get("EVENT_TITLE")
						+ "][" 	+ event.get("EVENT_STARTDATE")
						+ "][" 	+ event.get ("EVENT_ENDDATE")
						+ "]");
			}
		}else if(eventListCode == 2) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■현재 진행중인 이벤트■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("     이벤트 번호     이벤트 제목     이벤트 시작날짜     이벤트 종료 날짜");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			List<Map<String, Object>> eventNowList = eventDao.selectNowEventList();
			for(Map<String, Object> event : eventNowList){
				System.out.println(
						"["		+ event.get("EVENT_ID")
						+ "][" 	+ event.get("EVENT_TITLE")
						+ "][" 	+ event.get("EVENT_STARTDATE")
						+ "][" 	+ event.get ("EVENT_ENDDATE")
						+ "]");
			}
		}else if(eventListCode == 3) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■종료된 이벤트■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("     이벤트 번호     이벤트 제목     이벤트 시작날짜     이벤트 종료 날짜");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			List<Map<String, Object>> eventEndList = eventDao.selectEndEventList();
			for(Map<String, Object> event : eventEndList){
				System.out.println(
						"["		+ event.get("EVENT_ID")
						+ "][" 	+ event.get("EVENT_TITLE")
						+ "][" 	+ event.get("EVENT_STARTDATE")
						+ "][" 	+ event.get ("EVENT_ENDDATE")
						+ "]");
			}
		}
		
		if(Controller.loginUser.get("LOGIN_CODE").equals(1) || Controller.loginUser.get("LOGIN_CODE").equals(2)) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1.전체 이벤트 조회     2.현재 진행중인 이벤트 조회     3. 종료된 이벤트 조회     4.이벤트 상세조회     5.이전으로(메인 메뉴 페이지로 이동)");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞ 입력 > ");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: eventListCode = 1; break;
				case 2: eventListCode = 2; break;
				case 3: eventListCode = 3; break;
				case 4:	eventListCode = 1; return View.EVENT_LIST_INFO;
				case 5:	return View.LOGIN_MAIN_MENU;
				default : System.out.println("☞ 잘못된 번호입니다 ☜");
			}
		}else if (Controller.loginUser.get("LOGIN_CODE").equals(3)) {
			System.out.println("1.전체 이벤트 조회     2.현재 진행중인 이벤트 조회     3. 종료된 이벤트 조회     4.이벤트 상세조회     5.이벤트 등록     6.이전으로(메인 메뉴 페이지로 이동)");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞ 입력 > ");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: eventListCode = 1; break;
				case 2: eventListCode = 2; break;
				case 3: eventListCode = 3; break;
				case 4:	eventListCode = 1; return View.EVENT_LIST_INFO;
				case 5: insertEvent(); eventListCode = 1; break;
				case 6:	return View.LOGIN_MAIN_MENU;
				default : System.out.println("☞ 잘못된 번호입니다 ☜");
			}
		}
		return View.EVENT_LIST;
	}

//	이벤트 상세 조회
	public int eventInfo() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■이벤트 상세■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		Map<String, Object> eventInfo;
		int eventId;
		while(true) {
			System.out.print("☞ 조회 게시글 번호 입력 > ");
			eventId = ScanUtil.nextInt ();
			
	//		게시글 번호 세팅
			Map<String, Object> param = new HashMap<>();
			param.put ("EVENT_ID", eventId);
			
//			조회할 이벤트 게시글 데이터 가져옴
			eventInfo = eventDao.selectInfo(param);
			if(eventInfo==null) {
				System.out.println("☞ 잘못된 번호입니다 ☜");
			}else {
				break;
			}
		}

//		출력
		System.out.println ("■ 이벤트 번호 : " + eventInfo.get ("EVENT_ID"));
		System.out.println ("■ 이벤트 제목 : " + eventInfo.get ("EVENT_TITLE"));
		System.out.println ("■ 이벤트 내용 "); 
		System.out.println(eventInfo.get ("EVENT_CONTENTS"));
		System.out.println ("■ 이벤트 시작 일자 : " + eventInfo.get ("EVENT_STARTDATE"));
		
		if(eventInfo.get ("EVENT_ENDDATE")==null) {
			System.out.println ("■ 이벤트 종료 일자 : 진행중");
		}else {
			System.out.println ("■ 이벤트 종료 일자 : " + eventInfo.get ("EVENT_ENDDATE"));
		}
		System.out.println ("■ 이벤트 내용 : " + eventInfo.get ("EVENT_CONTENTS"));
		System.out.println ("■ 이벤트 작성 일자 : " + eventInfo.get ("EVENT_REG_DATE"));
		
		if(Controller.loginUser.get("LOGIN_CODE").equals(1) || Controller.loginUser.get("LOGIN_CODE").equals(2)) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1.이전으로(이벤트 홈)");
			System.out.print("입력>");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: return View.EVENT_LIST;
				default: System.out.println("잘못입력");
			}
		}else if (Controller.loginUser.get("LOGIN_CODE").equals(3)) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1.이벤트 수정     2.이벤트 삭제     3. 이전으로(이벤트 홈)");
			System.out.print ("☞ 입력 > ");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: updateEvent(eventId); return View.EVENT_LIST;
				case 2: deleteEvent(eventId); return View.EVENT_LIST;
				case 3: return View.EVENT_LIST;
				default: System.out.println("☞ 잘못된 번호입니다 ☜");
			}
		}
		
		return View.EVENT_LIST_INFO;
	}

//	이벤트 등록
	public void insertEvent() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■이벤트 등록■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print ("☞ 이벤트 제목 > ");
		String eventTitle = ScanUtil.nextLine ();
		
		String eventStartDate = "";
		boolean userFlag = true;
		while(userFlag) {
			System.out.print ("☞ 이벤트 시작 날짜 (ex.2020-10-16) > ");
			eventStartDate = ScanUtil.nextLine ();
			boolean userEventDateFlag = verifiedUtil.verifiedDate(eventStartDate);
			if(userEventDateFlag) {
				System.out.println("☞ 날짜 검사 : 성공 ☜");
				break;
			}else {
				System.out.println("☞ 날짜 검사 : 실패 ☜");
				System.out.println("올바른 형식이 아닙니다.");
			}
		}
		
		String eventEndDate = "";
		while(userFlag) {
			System.out.print ("☞ 이벤트 종료 날짜 (ex.2020-10-16) > ");
			eventEndDate = ScanUtil.nextLine ();
			boolean userEventDateFlag = verifiedUtil.verifiedDate(eventEndDate);
			if(userEventDateFlag) {
				System.out.println("☞ 날짜 검사 : 성공 ☜");
				break;
			}else {
				System.out.println("☞ 날짜 검사 : 실패 ☜");
				System.out.println("올바른 형식이 아닙니다.");
			}
		}
		
		System.out.print ("☞ 이벤트 내용 > ");
		String eventContents = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put ("EVENT_TITLE", eventTitle);
		param.put ("EVENT_STARTDATE", eventStartDate);
		param.put ("EVENT_ENDDATE", eventEndDate);
		param.put ("EVENT_CONTENTS", eventContents);
		param.put ("MANAGER_ID", Controller.loginUser.get("MANAGER_ID"));
		
		int result = eventDao.insertEvent(param);
		
		if(0 < result) {
			System.out.println ("☞ 이벤트 등록 성공(이벤트 화면으로 이동합니다.)");
		}else {
			System.out.println ("☞ 이벤트 등록 실패(이벤트 화면으로 이동합니다.)");
		}
	}//close insertEvent
	
//	이벤트 수정
	public void updateEvent(int eventId) {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■이벤트 수정■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		Map<String, Object> param = new HashMap<>();
		param.put ("EVENT_ID", eventId);
		System.out.print ("☞ 이벤트 제목 > ");
		String eventTitle = ScanUtil.nextLine ();

		String eventStartDate = "";
		boolean userFlag = true;
		while(userFlag) {
			System.out.print ("☞ 이벤트 시작 날짜 (ex.2020-10-16) > ");
			eventStartDate = ScanUtil.nextLine ();
			boolean userEventDateFlag = verifiedUtil.verifiedDate(eventStartDate);
			if(userEventDateFlag) {
				System.out.println("날짜 검사 : 정확하게 입력하였습니다.");
				break;
			}else {
				System.out.println("☞ 날짜 검사 : 실패 ☜");
				System.out.println("올바른 형식이 아닙니다.");
			}
		}
		
		String eventEndDate = "";
		while(userFlag) {
			System.out.print ("☞ 이벤트 종료 날짜 (ex.2020-10-16) > ");
			eventEndDate = ScanUtil.nextLine ();
			boolean userEventDateFlag = verifiedUtil.verifiedDate(eventEndDate);
			if(userEventDateFlag) {
				System.out.println("☞ 날짜 검사 : 성공 ☜");
				break;
			}else {
				System.out.println("☞ 날짜 검사 : 실패 ☜");
				System.out.println("올바른 형식이 아닙니다.");
			}
		}
		
		
		System.out.print ("☞ 이벤트 내용 >");
		String eventContents = ScanUtil.nextLine();
		
		param.put ("EVENT_TITLE", eventTitle);
		param.put ("EVENT_STARTDATE", eventStartDate);
		param.put ("EVENT_ENDDATE", eventEndDate);
		param.put ("EVENT_CONTENTS", eventContents);
		param.put ("MANAGER_ID", Controller.loginUser.get("MANAGER_ID"));

//		쿼리세팅
		int result = eventDao.updateEvent(param);
		if(0 < result) {
			System.out.println ("이벤트 수정 성공(이벤트 화면으로 이동합니다.)");
		}else {
			System.out.println ("이벤트 수정 실패(이벤트 화면으로 이동합니다.)");
		}
	}
	
//	이벤트 삭제
	public void deleteEvent(int eventId) {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■이벤트 삭제■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		Map<String, Object> param = new HashMap<>();
		param.put ("EVENT_ID", eventId);
//		쿼리 세팅
		int result = eventDao.deleteEvent(param);
		if(0 < result) {
			System.out.println ("이벤트 삭제 성공(이벤트 화면으로 이동합니다.)");
		}else {
			System.out.println ("이벤트 삭제 실패(이벤트 화면으로 이동합니다.)");
		}
	}
}

