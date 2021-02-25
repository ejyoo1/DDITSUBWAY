package service;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.MyDao;

public class InquiryMenu {
	
	private InquiryMenu(){
	}
	
	private static InquiryMenu instance;
	
	public static InquiryMenu getInstance() {
		if (instance == null) {
			instance = new InquiryMenu();
		}
		return instance;
	}

	private MyDao myDao = MyDao.getInstance();
	
	// 1대1문의 작성
		public int inquiry() {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■1:1문의 작성 ■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("문의 작성을 시작합니다.");
			System.out.print("☞  문의 제목>");
			String inquirytitle = ScanUtil.nextLine();
			System.out.print("☞  문의 이메일>");
			String inquiryemail = ScanUtil.nextLine();
			System.out.print("☞  문의 전화번호>");
			String inquirynumber = ScanUtil.nextLine();
			System.out.print("☞  문의 내용>");
			String inquirycontent = ScanUtil.nextLine();

			Map<String, Object> param = new HashMap<>();
			param.put("INQUIRY_TITLE", inquirytitle);
			param.put("INQUIRY_EMAIL", inquiryemail);
			param.put("INQUIRY_COMETL", inquirynumber);
			param.put("INQUIRY_CONTENT", inquirycontent);

			int result = myDao.insertInquiry(param);

			// 영향 받은 행이 있다면 등록성공 메시지가 출력되고 아니면 실패 메시지가 출력된다.
			if (0 < result) {
				System.out.println("1:1문의 등록 성공");
			} else {
				System.out.println("1:1문의 등록 실패");
			}
			// 등록 완료되면 비로그인은 HOME, 로그인은 마이페이지 메뉴로 돌아간다.
			if (Controller.loginUser == null){
				return View.HOME;
			}else{
				return View.MYPAGE_MENU;
			}
	}
}


