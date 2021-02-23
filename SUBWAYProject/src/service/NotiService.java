package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<Map<String, Object>> imporNoticeList = notiDao.selectImportantNotiList();
		System.out.println("=======================================");
		System.out.println("공지 사항 번호\t공지 사항 제목\t공지 작성 일자");
		System.out.println("---------------------------------------");
		for(Map<String, Object> imporNoti : imporNoticeList){
			System.out.println(imporNoti.get("NOTICE_NO")
					+ "\t" + imporNoti.get("NOTICE_TITLE")
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
		}		
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
		System.out.println ("> 공지사항 제목 : " + noticeList.get ("NOTICE_TITLE"));
		System.out.println ("> 공지사항 내용 "); 
		System.out.println(noticeList.get ("NOTICE_CONTENTS"));
		System.out.println ("> 공지 작성 일자 : " + noticeList.get ("NOTICE_REG_DATE"));
		
		System.out.println("1. 이전으로");
		System.out.print ("입력>");
		int userInput = ScanUtil.nextInt ();
		switch(userInput) {
			case 1: return View.NOTICE_LIST;
			default: System.out.println("잘못입력");
		}
		
		return View.NOTICE_LIST_INFO;
	}
}
