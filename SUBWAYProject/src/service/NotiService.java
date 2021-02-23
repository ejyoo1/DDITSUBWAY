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
	
	public int noticeHome() {
		System.out.println("==공지사항==");
//		로그인 타입 : 고객, 가맹점주별 메뉴 출력
		if(Controller.loginUser.get("LOGIN_CODE").equals(1) || Controller.loginUser.get("LOGIN_CODE").equals(2)) {
			List<Map<String, Object>> imporNoticeList = notiDao.selectImportantNotiList();
			System.out.println("=======================================");
			System.out.println("공지 사항 번호\t공지 사항 제목\t공지 작성 일자");
			System.out.println("---------------------------------------");
			for(Map<String, Object> imporNoti : imporNoticeList){
//				데이터 베이스에 삽입할 때 콤마 하나만 쓰는 경우 MISSING COMMA 오류가 발생하여 예외처리를 하였으나
//				출력할 시에도 문제가 되어 다시 REPLACE 함.
				String noticeTitleM = imporNoti.get("NOTICE_TITLE").toString().replaceAll("'''", "'");
				System.out.println(imporNoti.get("NOTICE_NO")
						+ "\t" + noticeTitleM
						+ "\t" + imporNoti.get("NOTICE_REG_DATE"));
			}
			List<Map<String,Object>> noticeAllList = notiDao.selectNotiAllList();
			for(Map<String, Object> noti : noticeAllList){
				System.out.println(noti.get("NOTICE_NO")
						+ "\t" + noti.get("NOTICE_TITLE")
						+ "\t" + noti.get("NOTICE_REG_DATE"));
			}
			System.out.println("1. 상세조회\t2. 이전으로");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: return View.NOTICE_LIST_INFO;
				case 2: return View.LOGIN_MAIN_MENU;
				default : System.out.println("잘못입력");
			}//close switch
			
		}else if (Controller.loginUser.get("LOGIN_CODE").equals(3)) {
			List<Map<String, Object>> imporNoticeList = notiDao.selectImportantNotiList();
			System.out.println("=======================================");
			System.out.println("공지 사항 번호\t공지 사항 제목\t공지 작성 일자\t관리자ID");
			System.out.println("---------------------------------------");
			for(Map<String, Object> imporNoti : imporNoticeList){
//				데이터 베이스에 삽입할 때 콤마 하나만 쓰는 경우 MISSING COMMA 오류가 발생하여 예외처리를 하였으나
//				출력할 시에도 문제가 되어 다시 REPLACE 함.
				String noticeTitleM = imporNoti.get("NOTICE_TITLE").toString().replaceAll("'''", "'");
				System.out.println(imporNoti.get("NOTICE_NO")
						+ "\t" + noticeTitleM
						+ "\t" + imporNoti.get("NOTICE_REG_DATE")
						+ "\t" + imporNoti.get("MANAGER_ID"));
			}
			List<Map<String,Object>> noticeAllList = notiDao.selectNotiAllList();
			for(Map<String, Object> noti : noticeAllList){
				System.out.println(noti.get("NOTICE_NO")
						+ "\t" + noti.get("NOTICE_TITLE")
						+ "\t" + noti.get("NOTICE_REG_DATE")
						+ "\t" + noti.get("MANAGER_ID"));
			}
			System.out.println("1. 공지사항 등록\t2. 공지사항 상세 조회\t3. 이전으로");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1: insertNotice(); break;
				case 2: return View.NOTICE_LIST_INFO;
				case 3: return View.LOGIN_MAIN_MENU;
				default : System.out.println("잘못입력");
			}//close switch
		}//close else if
		return View.NOTICE_LIST;
	}
	
	public int notiInfo(){
		System.out.println("==공지상세조회==");
		System.out.print ("조회할 게시글 번호 입력>");
		int noticeNo = ScanUtil.nextInt ();
		
		Map<String, Object> param = new HashMap<>();
		param.put ("NOTICE_NO", noticeNo);
		
//		조회할 게시글 데이터 가져옴
		Map<String, Object> noticeList = notiDao.selectNotiList(param);
//		출력
		System.out.println ("> 공지사항 번호 : " + noticeList.get ("NOTICE_NO"));
//		데이터 베이스에 삽입할 때 콤마 하나만 쓰는 경우 MISSING COMMA 오류가 발생하여 예외처리를 하였으나
//		출력할 시에도 문제가 되어 다시 REPLACE 함.
		String noticeTitleM = noticeList.get("NOTICE_TITLE").toString().replaceAll("'''", "'");
		System.out.println ("> 공지사항 제목 : " + noticeTitleM);
		System.out.println ("> 공지사항 내용 "); 
		String noticeContentsM = noticeList.get("NOTICE_CONTENTS").toString().replaceAll("'''", "'");
		System.out.println(noticeContentsM);
		System.out.println ("> 공지 작성 일자 : " + noticeList.get ("NOTICE_REG_DATE"));
		
//		상세 조회 메뉴 출력
		System.out.println("1. 공지사항 수정\t2. 공지사항 삭제\t3. 이전으로");
		System.out.print ("입력>");
		int userInput = ScanUtil.nextInt ();
		switch(userInput) {
			case 1: updateNotice(noticeNo); return View.NOTICE_LIST;
			case 2: deleteNotice(noticeNo); return View.NOTICE_LIST;
			case 3: return View.NOTICE_LIST;
			default: System.out.println("잘못입력");
		}
		
		return View.NOTICE_LIST_INFO;
	}//close notiInfo
	
//	공지사항 등록
	public int insertNotice() {
		System.out.println("=========== 공지사항 등록 =============");
		System.out.print ("공지사항 제목 >");
		String noticeTitle = ScanUtil.nextLine ();
//		오라클에서 작은 따옴표 저장 시 3개 이상을 써야 삽입되므로 replaceAll
		String noticeTitleM = noticeTitle.replaceAll("'", "'''");
		System.out.print ("공지사항 내용 >");
		String noticeContents = ScanUtil.nextLine ();
		String noticeContentsM = noticeContents.replaceAll("'", "'''");
		System.out.print ("공지사항 상단 노출 여부 [0:상단노출 함 / 1:상단노출 안함]>");
		String noticeImporCode = ScanUtil.nextLine();
		Map<String, Object> param = new HashMap<>();
		param.put ("NOTICE_TITLE", noticeTitleM);
		param.put ("NOTICE_CONTENTS", noticeContentsM);
		param.put ("NOTICE_IMPOR_CODE", noticeImporCode);
		param.put ("MANAGER_ID", Controller.loginUser.get("MANAGER_ID"));
		
		int result = notiDao.insertNotice(param);
		
		if(0 < result) {
			System.out.println ("공지사항 등록 성공");
		}else {
			System.out.println ("공지사항 등록 실패 재시도 하십시오.");
		}
		return View.NOTICE_LIST;
	}//close insertNotice

//	공지사항 수정
	public void updateNotice(int noticeNo) {
		System.out.println("=========== 공지사항 수정 =============");
		
		Map<String, Object> param = new HashMap<>();
		param.put ("NOTICE_NO", noticeNo);
		param.put("MANAGER_ID", Controller.loginUser.get("MANAGER_ID"));
		System.out.print ("공지사항 제목 >");
		String noticeTitle = ScanUtil.nextLine ();
//		오라클에서 작은 따옴표 저장 시 3개 이상을 써야 삽입되므로 replaceAll
		String noticeTitleM = noticeTitle.replaceAll("'", "'''");
		System.out.print ("공지사항 내용 >");
		String noticeContents = ScanUtil.nextLine ();
		String noticeContentsM = noticeContents.replaceAll("'", "'''");
		System.out.print ("공지사항 상단 노출 여부 [0:상단노출 함 / 1:상단노출 안함]>");
		String noticeImporCode = ScanUtil.nextLine();
		param.put ("NOTICE_TITLE", noticeTitleM);
		param.put ("NOTICE_CONTENTS", noticeContentsM);
		param.put ("NOTICE_IMPOR_CODE", noticeImporCode);
//		쿼리세팅
		int result = notiDao.updateNotice(param);
		if(0 < result) {
			System.out.println ("공지사항 수정 성공");
		}else {
			System.out.println ("공지사항 수정 실패 재시도 하십시오.");
		}
	}
	
//	공지사항 삭제
	public void deleteNotice(int noticeNo) {
		System.out.println("=========== 공지사항 삭제 =============");
		Map<String, Object> param = new HashMap<>();
		param.put ("NOTICE_NO", noticeNo);
//		쿼리 세팅
		int result = notiDao.deleteBoard(param);
		if(0 < result) {
			System.out.println ("공지사항 삭제 성공");
		}else {
			System.out.println ("공지사항 삭제 실패");
		}
	}
}
