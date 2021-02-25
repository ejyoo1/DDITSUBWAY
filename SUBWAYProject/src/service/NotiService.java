package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.NotiDao;
import util.ScanUtil;
import util.View;

public class NotiService {
	//공지 서비스 
	private NotiService(){}
	private static NotiService instance;
	public static NotiService getInstance(){
		if(instance == null){
			instance = new NotiService();
		}
		return instance;
	}
	private NotiDao notiDao = NotiDao.getInstance();
	
//	공지사항 메인 홈 화면
	public int noticeHome() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■공지사항■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
//		* 로그인 타입 : 고객, 가맹점주 메뉴 출력
		if(Controller.loginUser.get("LOGIN_CODE").equals(1) || Controller.loginUser.get("LOGIN_CODE").equals(2)) {
			List<Map<String, Object>> imporNoticeList = notiDao.selectImportantNotiList();
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("   공지사항번호     공지사항제목     공지작성일자");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//			중요 공지사항 전체 리스트 출력
			for(Map<String, Object> imporNoti : imporNoticeList){
//				데이터 베이스 삽입 시 ''가 있을 때, '처리에서 오류가 발생하여 REPLACE를 통한 ''' 로 변경함(데이터베이스에서는 '로 표기하지만, 자바에서는 '''로 표기하므로 REPLACE로 다시 변경함)	
				String noticeTitleM = imporNoti.get("NOTICE_TITLE").toString().replaceAll("'''", "'");
				System.out.println("★" 
						+ "[" 	+ imporNoti.get("NOTICE_NO") 
						+ "][" 	+ noticeTitleM 
						+ "][" 	+ imporNoti.get("NOTICE_REG_DATE")
						+ "]");
			}//CLOSE FOR
			
//			일반 공지사항 전체 리스트 출력
			List<Map<String,Object>> noticeAllList = notiDao.selectNotiAllList();
			for(Map<String, Object> noti : noticeAllList){
				System.out.println(
						"[" 	+ noti.get("NOTICE_NO")
						+ "][" 	+ noti.get("NOTICE_TITLE")
						+ "][" 	+ noti.get("NOTICE_REG_DATE")
						+ "]");
			}//CLOSE FOR
			
//			상세조회, 이전으로 이동 페이지 설정
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1.상세조회     2.이전으로(메인 메뉴 페이지 이동)");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞ 입력 > ");
			int userInput = ScanUtil.nextInt();
//			사용자 입력 별 메뉴 이동
			switch(userInput) {
				case 1: return View.NOTICE_LIST_INFO;
				case 2: return View.LOGIN_MAIN_MENU;
				default : System.out.println("☞ 잘못된 번호입니다 ☜");
			}//CLOSE SWITCH
			
//		* 로그인 타입 : 관리자 메뉴 출력
		}else if (Controller.loginUser.get("LOGIN_CODE").equals(3)) {
			List<Map<String, Object>> imporNoticeList = notiDao.selectImportantNotiList();
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("   공지사항번호     공지사항제목     공지작성일자     관리자ID");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//			중요 공지사항 전체 리스트 출력
			for(Map<String, Object> imporNoti : imporNoticeList){
//				데이터 베이스 삽입 시 ''가 있을 때, '처리에서 오류가 발생하여 REPLACE를 통한 ''' 로 변경함(데이터베이스에서는 '로 표기하지만, 자바에서는 '''로 표기하므로 REPLACE로 다시 변경함)	
				String noticeTitleM = imporNoti.get("NOTICE_TITLE").toString().replaceAll("'''", "'");
				System.out.println("★" 
						+ "[" +imporNoti.get("NOTICE_NO")
						+ "][" + noticeTitleM
						+ "][" + imporNoti.get("NOTICE_REG_DATE")
						+ "][" + imporNoti.get("MANAGER_ID")
						+ "]");
			}//CLOSE FOR
			
//			일반 공지사항 전체 리스트 출력
			List<Map<String,Object>> noticeAllList = notiDao.selectNotiAllList();
			for(Map<String, Object> noti : noticeAllList){
				System.out.println(
						"[" 	+ noti.get("NOTICE_NO")
						+ "][" 	+ noti.get("NOTICE_TITLE")
						+ "][" 	+ noti.get("NOTICE_REG_DATE")
						+ "][" 	+ noti.get("MANAGER_ID"));
			}//CLOSE FOR
			
//			공지사항 등록, 공지사항 상세조회, 이전으로 이동 페이지 설정
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1.공지사항 등록     2.공지사항 상세 조회     3.이전으로(메인 메뉴 페이지 이동)");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("☞ 입력 > ");
			int userInput = ScanUtil.nextInt();
//			사용자 입력 별 메뉴 이동
			switch(userInput) {
				case 1: insertNotice(); break;
				case 2: return View.NOTICE_LIST_INFO;
				case 3: return View.LOGIN_MAIN_MENU;
				default : System.out.println("☞ 잘못된 번호입니다 ☜");
			}//CLOSE SWITCH
		}//CLOSE IF-ELSE
		
//		상세조회 또는 공지사항 등록 수행 후 공지사항 리스트 출력되도록 설정
		return View.NOTICE_LIST;
	}

	
//	공지사항 상세 조회 페이지 : NOTICE_LIST_INFO
	public int notiInfo(){
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■공지사항 상세 조회■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		Map<String, Object> noticeList;
		int noticeNo;
		while(true) {
			System.out.print("☞ 조회 게시글 번호 입력 > ");
			noticeNo = ScanUtil.nextInt ();
			
	//		게시글 번호 세팅
			Map<String, Object> param = new HashMap<>();
			param.put ("NOTICE_NO", noticeNo);
			
	//		상세 조회할 게시글의 DB 데이터를 가져옴
			noticeList = notiDao.selectNotiList(param);
			if(noticeList==null) {
				System.out.println("☞ 잘못된 번호입니다 ☜");
			}else {
				break;
			}
		}
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//		상세 조회할 게시글의 DB 데이터 출력
		System.out.println ("■ 공지사항 번호 : " + noticeList.get ("NOTICE_NO"));
//		데이터 베이스 삽입 시 ''가 있을 때, '처리에서 오류가 발생하여 REPLACE를 통한 ''' 로 변경함(데이터베이스에서는 '로 표기하지만, 자바에서는 '''로 표기하므로 REPLACE로 다시 변경함)
		String noticeTitleM = noticeList.get("NOTICE_TITLE").toString().replaceAll("'''", "'");
		System.out.println ("■ 공지사항 제목 : " + noticeTitleM);
		System.out.println ("■ 공지사항 내용 "); 
		String noticeContentsM = noticeList.get("NOTICE_CONTENTS").toString().replaceAll("'''", "'");
		System.out.println(noticeContentsM);
		System.out.println ("■ 공지 작성 일자 : " + noticeList.get ("NOTICE_REG_DATE"));
		
//		고객, 가맹점 상세 조회 메뉴 출력
		if(Controller.loginUser.get("LOGIN_CODE").equals(1) || Controller.loginUser.get("LOGIN_CODE").equals(2)) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1.이전으로(공지사항 홈)");
			System.out.print ("☞ 입력 > ");
			int userInput = ScanUtil.nextInt ();
			switch(userInput) {
				case 1: return View.NOTICE_LIST;
				default: System.out.println("☞ 잘못된 번호입니다 ☜");
			}
//		관리자 상세 조회 메뉴 출력
		}else if (Controller.loginUser.get("LOGIN_CODE").equals(3)) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1.공지사항 수정     2.공지사항 삭제     3.이전으로(공지사항 홈)");
			System.out.print ("☞ 입력 > ");
			int userInput = ScanUtil.nextInt ();
			switch(userInput) {
				case 1: updateNotice(noticeNo); return View.NOTICE_LIST;
				case 2: deleteNotice(noticeNo); return View.NOTICE_LIST;
				case 3: return View.NOTICE_LIST;
				default: System.out.println("☞ 잘못된 번호입니다 ☜");
			}
		}
		return View.NOTICE_LIST_INFO;
	}//close notiInfo
	
//	공지사항 등록
	public void insertNotice() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■공지사항 등록■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print ("☞ 공지사항 제목 > ");
		String noticeTitle = ScanUtil.nextLine ();
//		DB에 삽입 시 작은 따옴표 존재하는 경우 3개 이상을 써야 DB 에 ' 삽입되므로 REPLACEALL 사용
		String noticeTitleM = noticeTitle.replaceAll("'", "'''");
		System.out.print ("☞ 공지사항 내용 > ");
		String noticeContents = ScanUtil.nextLine ();
		String noticeContentsM = noticeContents.replaceAll("'", "'''");
		System.out.print ("☞ 공지사항 상단 노출 여부 [0:상단노출 함 / 1:상단노출 안함] > ");
		String noticeImporCode = ScanUtil.nextLine();
		
//		입력값 세팅
		Map<String, Object> param = new HashMap<>();
		param.put ("NOTICE_TITLE", noticeTitleM);
		param.put ("NOTICE_CONTENTS", noticeContentsM);
		param.put ("NOTICE_IMPOR_CODE", noticeImporCode);
		param.put ("MANAGER_ID", Controller.loginUser.get("MANAGER_ID"));
		
//		DAO 호출하여 결과 가져옴
		int result = notiDao.insertNotice(param);
		
//		공지사항 등록 결과 처리
		if(0 < result) {
			System.out.println ("☞ 공지사항 등록 성공(공지사항 홈으로 이동합니다.) ☜");
		}else {
			System.out.println ("☞ 공지사항 등록 실패 재시도 하십시오(공지사항 홈으로 이동합니다.) ☜");
		}
	}//close insertNotice

//	공지사항 수정
	public void updateNotice(int noticeNo) {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■공지사항 수정■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
//		입력과 세팅
		Map<String, Object> param = new HashMap<>();
		param.put ("NOTICE_NO", noticeNo);
		param.put("MANAGER_ID", Controller.loginUser.get("MANAGER_ID"));
		System.out.print ("☞ 공지사항 제목 > ");
		String noticeTitle = ScanUtil.nextLine ();
//		DB에 삽입 시 작은 따옴표 존재하는 경우 3개 이상을 써야 DB 에 ' 삽입되므로 REPLACEALL 사용
		String noticeTitleM = noticeTitle.replaceAll("'", "'''");
		System.out.print ("☞ 공지사항 내용 > ");
		String noticeContents = ScanUtil.nextLine ();
		String noticeContentsM = noticeContents.replaceAll("'", "'''");
		System.out.print ("☞  공지사항 상단 노출 여부 [0:상단노출 함 / 1:상단노출 안함] > ");
		String noticeImporCode = ScanUtil.nextLine();
		param.put ("NOTICE_TITLE", noticeTitleM);
		param.put ("NOTICE_CONTENTS", noticeContentsM);
		param.put ("NOTICE_IMPOR_CODE", noticeImporCode);
		
//		DAO 호출하여 결과 가져옴
		int result = notiDao.updateNotice(param);
		
//		공지사항 수정 결과 처리
		if(0 < result) {
			System.out.println ("☞ 공지사항 수정 성공(공지사항 홈으로 이동합니다.)  ☜");
		}else {
			System.out.println ("☞ 공지사항 수정 실패 재시도 하십시오(공지사항 홈으로 이동합니다.) ☜");
		}
	}
	
//	공지사항 삭제
	public void deleteNotice(int noticeNo) {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■공지사항 삭제■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		Map<String, Object> param = new HashMap<>();
		param.put ("NOTICE_NO", noticeNo);
//		DAO 호출하여 결과 가져옴
		int result = notiDao.deleteBoard(param);
		if(0 < result) {
			System.out.println ("☞ 공지사항 삭제 성공(공지사항 홈으로 이동합니다.)  ☜");
		}else {
			System.out.println ("☞ 공지사항 삭제 실패(공지사항 홈으로 이동합니다.) ☜");
		}
	}
}
