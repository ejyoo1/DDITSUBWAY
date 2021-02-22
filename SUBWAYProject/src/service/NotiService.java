package service;

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
		List<Map<String,Object>> noticeList = notiDao.selectNotiList();
		for(Map<String, Object> noti : noticeList){
			System.out.println(noti.get("NOTICE_NO")
					+ "\t" + noti.get("NOTICE_TITLE")
					+ "\t" + noti.get("NOTICE_REG_DATE"));
		}
		int input = ScanUtil.nextInt();
		
		return View.NOTICE_LIST;
	}
}
