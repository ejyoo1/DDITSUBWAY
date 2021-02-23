package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.EventDao;
import util.ScanUtil;
import util.View;

public class EventService {
//	전체조회, 현재 진행중 이벤트 조회, 종료된 이벤트 조회 출력 전용 변수 생성
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
			for(Map<String, Object> event : eventAllList){
				System.out.println(event.get("EVENT_ID")
						+ "\t" + event.get("EVENT_TITLE")
						+ "\t" + event.get("EVENT_STARTDATE")
						+ "\t" + event.get("EVENT_ENDDATE")
						);
			}
		}else if(eventListCode == 2) {
			System.out.println("=============현재 진행중 이벤트==================");
			List<Map<String, Object>> eventNowList = eventDao.selectNowEventList();
			for(Map<String, Object> event : eventNowList){
				System.out.println(event.get("EVENT_ID")
						+ "\t" + event.get("EVENT_TITLE")
						+ "\t" + event.get("EVENT_STARTDATE")
						+ "\t" + event.get("EVENT_ENDDATE")
						);
			}
		}else if(eventListCode == 3) {
			System.out.println("================종료된 이벤트==================");
			List<Map<String, Object>> eventEndList = eventDao.selectEndEventList();
			for(Map<String, Object> event : eventEndList){
				System.out.println(event.get("EVENT_ID")
						+ "\t" + event.get("EVENT_TITLE")
						+ "\t" + event.get("EVENT_STARTDATE")
						+ "\t" + event.get("EVENT_ENDDATE")
						);
			}
		}
		System.out.println("1. 현재 진행중인 이벤트 조회\t2. 종료된 이벤트 조회\t3.이벤트 상세조회\t4. 이전으로");
		System.out.println("입력>");
		int userInput = ScanUtil.nextInt();
		switch(userInput) {
			case 1: eventListCode = 2; break;
			case 2: eventListCode = 3; break;
			case 3:	return View.EVENT_LIST_INFO;
			case 4:	return View.LOGIN_MAIN_MENU;
			default: System.out.println("잘못입력");
		}
		return View.EVENT_LIST;
	}

	public int eventInfo() {
		System.out.println("==이벤트조회==");
		System.out.print ("조회할 게시글 번호 입력>");
		int eventNo = ScanUtil.nextInt ();
		
		Map<String, Object> param = new HashMap<>();
		param.put ("EVENT_NO", eventNo);
		
//		조회할 이벤트 게시글 데이터 가져옴
		Map<String, Object> eventInfo = eventDao.selectInfo(param);
//		출력
		System.out.println ("> 이벤트 번호 : " + eventInfo.get ("EVENT_ID"));
		System.out.println ("> 이벤트 제목 : " + eventInfo.get ("EVENT_TITLE"));
		System.out.println ("> 이벤트 내용 "); 
		System.out.println(eventInfo.get ("EVENT_CONTENTS"));
		System.out.println ("> 이벤트 시작 일자 : " + eventInfo.get ("EVENT_STARTDATE"));
		System.out.println ("> 이벤트 종료 일자 : " + eventInfo.get ("EVENT_ENDDATE"));
		System.out.println ("> 이벤트 내용 : " + eventInfo.get ("EVENT_CONTENTS"));
		System.out.println ("> 이벤트 작성 일자 : " + eventInfo.get ("EVENT_REG_DATE"));
		
		System.out.println("1. 이전으로");
		
		return View.EVENT_LIST_INFO;
	}
	
}
