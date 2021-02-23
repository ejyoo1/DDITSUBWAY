package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.EventDao;
import util.ScanUtil;
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
	
//	다오 서비스 객체 획득
	private EventDao eventDao = EventDao.getInstance();
	
	public int eventList() {//1. 전체조회 2. 현재 진행중인 이벤트 조회 3. 종료된 이벤트 조회
		System.out.println("=======================================");
		System.out.println("이벤트 번호\t이벤트 제목\t이벤트 시작날짜\t이벤트 종료 날짜");
		System.out.println("---------------------------------------");
//		전체 목록 출력
		if(eventListCode == 1) {
			System.out.println("===============전체 이벤트==================");
			List<Map<String, Object>> eventAllList = eventDao.selectAllEventList();
			String eventEndDateString = "";
			for(Map<String, Object> event : eventAllList){
//				이벤트 종료날짜 null 데이터 처리
				if(event.get ("EVENT_ENDDATE")==null) {
					eventEndDateString = "진행중";
				}else {
					eventEndDateString = event.get ("EVENT_ENDDATE").toString();
				}
				System.out.println(event.get("EVENT_ID")
						+ "\t" + event.get("EVENT_TITLE")
						+ "\t" + event.get("EVENT_STARTDATE")
						+ "\t" + eventEndDateString
						);
			}
		}else if(eventListCode == 2) {
			System.out.println("=============현재 진행중 이벤트==================");
			List<Map<String, Object>> eventNowList = eventDao.selectNowEventList();
			String eventEndDateString = "";
			for(Map<String, Object> event : eventNowList){
//				이벤트 종료날짜 null 데이터 처리
				if(event.get ("EVENT_ENDDATE")==null) {
					eventEndDateString = "진행중";
				}else {
					eventEndDateString = event.get ("EVENT_ENDDATE").toString();
				}
				System.out.println(event.get("EVENT_ID")
						+ "\t" + event.get("EVENT_TITLE")
						+ "\t" + event.get("EVENT_STARTDATE")
						+ "\t" + eventEndDateString
						);
			}
		}else if(eventListCode == 3) {
			System.out.println("================종료된 이벤트==================");
			List<Map<String, Object>> eventEndList = eventDao.selectEndEventList();
			for(Map<String, Object> event : eventEndList){
//				종료된 날짜는 null일 수 없음.
				System.out.println(event.get("EVENT_ID")
						+ "\t" + event.get("EVENT_TITLE")
						+ "\t" + event.get("EVENT_STARTDATE")
						+ "\t" + event.get ("EVENT_ENDDATE")
						);
			}
		}
		
		if(Controller.loginUser.get("LOGIN_CODE").equals(1) || Controller.loginUser.get("LOGIN_CODE").equals(2)) {
			System.out.println("1. 전체 이벤트 조회\t2. 현재 진행중인 이벤트 조회\t3. 종료된 이벤트 조회\t4.이벤트 상세조회\t5. 이전으로");
			System.out.print("입력>");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: eventListCode = 1; break;
				case 2: eventListCode = 2; break;
				case 3: eventListCode = 3; break;
				case 4:	return View.EVENT_LIST_INFO;
				case 5:	return View.LOGIN_MAIN_MENU;
				default: System.out.println("잘못입력");
			}
			return View.EVENT_LIST;
		}else if (Controller.loginUser.get("LOGIN_CODE").equals(3)) {
			System.out.println("1. 전체 이벤트 조회\t2. 현재 진행중인 이벤트 조회\t3. 종료된 이벤트 조회\t4.이벤트 상세조회\t5. 이벤트 등록\t6. 이전으로");
			System.out.print("입력>");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: eventListCode = 1; break;
				case 2: eventListCode = 2; break;
				case 3: eventListCode = 3; break;
				case 4:	eventListCode = 1; return View.EVENT_LIST_INFO;
				case 5: insertEvent(); eventListCode = 1; break;
				case 6:	return View.LOGIN_MAIN_MENU;
				default: System.out.println("잘못입력");
			}
			return View.EVENT_LIST;
		}
		return View.EVENT_LIST;
	}

	public int eventInfo() {
		System.out.println("==이벤트조회==");
		System.out.print ("조회할 게시글 번호 입력>");
		int eventId = ScanUtil.nextInt ();
		
		Map<String, Object> param = new HashMap<>();
		param.put ("EVENT_ID", eventId);
		
//		조회할 이벤트 게시글 데이터 가져옴
		Map<String, Object> eventInfo = eventDao.selectInfo(param);
//		출력
		System.out.println ("> 이벤트 번호 : " + eventInfo.get ("EVENT_ID"));
		System.out.println ("> 이벤트 제목 : " + eventInfo.get ("EVENT_TITLE"));
		System.out.println ("> 이벤트 내용 "); 
		System.out.println(eventInfo.get ("EVENT_CONTENTS"));
		System.out.println ("> 이벤트 시작 일자 : " + eventInfo.get ("EVENT_STARTDATE"));
		
		if(eventInfo.get ("EVENT_ENDDATE")==null) {
			System.out.println ("> 이벤트 종료 일자 : 진행중");
		}else {
			System.out.println ("> 이벤트 종료 일자 : " + eventInfo.get ("EVENT_ENDDATE"));
		}
		System.out.println ("> 이벤트 내용 : " + eventInfo.get ("EVENT_CONTENTS"));
		System.out.println ("> 이벤트 작성 일자 : " + eventInfo.get ("EVENT_REG_DATE"));
		
		if(Controller.loginUser.get("LOGIN_CODE").equals(1) || Controller.loginUser.get("LOGIN_CODE").equals(2)) {
			System.out.println("1. 이전으로");
			System.out.print("입력>");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: return View.EVENT_LIST;
				default: System.out.println("잘못입력");
			}
		}else if (Controller.loginUser.get("LOGIN_CODE").equals(3)) {
			System.out.println("1. 이벤트 수정\t2. 이벤트 삭제\t3. 이전으로");
			System.out.print("입력>");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: updateEvent(eventId); return View.EVENT_LIST;
				case 2: deleteEvent(eventId); return View.EVENT_LIST;
				case 3: return View.EVENT_LIST;
				default: System.out.println("잘못입력");
			}
		}
		
		return View.EVENT_LIST_INFO;
	}

//	이벤트 등록
	public int insertEvent() {
		System.out.println("=========== 이벤트 등록 =============");
		System.out.print ("이벤트 제목 >");
		String eventTitle = ScanUtil.nextLine ();
		System.out.print ("이벤트 시작 날짜 (ex.2020/10/16)>");
		String eventStartDate = ScanUtil.nextLine ();
		System.out.print ("이벤트 종료 날짜 (ex.2020/10/16)>");
		String eventEndDate = ScanUtil.nextLine();
		System.out.print ("이벤트 내용 >");
		String eventContents = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put ("EVENT_TITLE", eventTitle);
		param.put ("EVENT_STARTDATE", eventStartDate);
		param.put ("EVENT_ENDDATE", eventEndDate);
		param.put ("EVENT_CONTENTS", eventContents);
		param.put ("MANAGER_ID", Controller.loginUser.get("MANAGER_ID"));
		
		int result = eventDao.insertEvent(param);
		
		if(0 < result) {
			System.out.println ("이벤트 등록 성공");
		}else {
			System.out.println ("이벤트 등록 실패 재시도 하십시오.");
		}
		return View.NOTICE_LIST;
	}//close insertEvent
	
//	이벤트 수정
	public void updateEvent(int eventId) {
		System.out.println("=========== 이벤트 수정 =============");
		Map<String, Object> param = new HashMap<>();
		param.put ("EVENT_ID", eventId);
		System.out.print ("이벤트 제목 >");
		String eventTitle = ScanUtil.nextLine ();
		System.out.print ("이벤트 시작 날짜 (ex.2020/10/16)>");
		String eventStartDate = ScanUtil.nextLine ();
		System.out.print ("이벤트 종료 날짜 (ex.2020/10/16)>");
		String eventEndDate = ScanUtil.nextLine();
		System.out.print ("이벤트 내용 >");
		String eventContents = ScanUtil.nextLine();
		
		param.put ("EVENT_TITLE", eventTitle);
		param.put ("EVENT_STARTDATE", eventStartDate);
		param.put ("EVENT_ENDDATE", eventEndDate);
		param.put ("EVENT_CONTENTS", eventContents);
		param.put ("MANAGER_ID", Controller.loginUser.get("MANAGER_ID"));

//		쿼리세팅
		int result = eventDao.updateEvent(param);
		if(0 < result) {
			System.out.println ("이벤트 수정 성공");
		}else {
			System.out.println ("이벤트 수정 실패 재시도 하십시오.");
		}
	}
	
//	이벤트 삭제
	public void deleteEvent(int eventId) {
		System.out.println("=========== 이벤트 삭제 =============");
		Map<String, Object> param = new HashMap<>();
		param.put ("EVENT_ID", eventId);
//		쿼리 세팅
		int result = eventDao.deleteEvent(param);
		if(0 < result) {
			System.out.println ("이벤트 삭제 성공");
		}else {
			System.out.println ("이벤트 삭제 실패");
		}
	}
}

