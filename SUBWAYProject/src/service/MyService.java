package service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.MyDao;

public class MyService {
	//마이페이지 서비스
	private MyService(){}
	private static MyService instance;
	public static MyService getInstance(){
		if(instance == null){
			instance = new MyService();
		}
		return instance;
	}
	
	private MyDao myDao = MyDao.getInstance();
	public int myPageHome() {   //마이페이지 시작
		if(Controller.loginUser.get("LOGIN_CODE").equals(1)){        //고객 마이페이지
			System.out.println("1.내정보 수정\t2. 주문 내역 확인\t3. 1:1 문의") ;
			System.out.print("입력>");
			int input = ScanUtil.nextInt();
			switch(input){
				case 1: userModifyInfo(); break; // 내정보수정
				case 2: userOrderList(); break;// 주문내역조회
				case 3: inquiry();		break; //1대1문의
				default: System.out.println("잘못입력"); break;
			}
		}else if (Controller.loginUser.get("LOGIN_CODE").equals(2)){  //가맹점 마이페이지
			System.out.println("1.내정보 수정\t2. 주문 내역 확인\t3. 1:1 문의") ;
			System.out.print("입력>");
			int input = ScanUtil.nextInt();
			switch(input){
				case 1: storeModifyInfo(); break; // 내정보수정
				case 2: storeOrderList();  break;// 주문내역조회
				case 3: inquiry();		 break;//1대1문의
				default: System.out.println("잘못입력"); break;
			}	
		}else if (Controller.loginUser.get("LOGIN_CODE").equals(3)){   //관리자 고객 센터 관리  
			System.out.println("1.1:1문의 상세 조회\t2.이전으로") ;
			System.out.print("입력>");
			int input = ScanUtil.nextInt();
			switch(input){
				case 1: managerInquiry(); break; //1대1문의 상세조회 
				case 2: return View.LOGIN_MAIN_MENU; 
				default: System.out.println("잘못입력"); break;
			}
		}
		return View.MYPAGE_MENU;
	}


		// 고객 정보수정 페이지
	public int userModifyInfo() {
		System.out.println("비밀번호를 입력해 주세요");
		System.out.print("입력>");
		String password = ScanUtil.nextLine();  //비밀번호 저장
		Map<String, Object> user = myDao.selectUser(password); // 비밀번호 확인절차
		if(user == null){
			System.out.println("비밀번호를 잘못 입력하셨습니다.");
        }else{
			System.out.println("=========== 현재 정보 =============");
//			현재 회원정보 출력
			List<Map<String, Object>> userList = myDao.selectUserList();
			System.out.println("회원명\t생년월일\t전화번호\t우편번호\t주소");
			System.out.println("---------------------------------------");
			for(Map<String, Object> User : userList){
				System.out.print (User.get("MEM_NM") + "\t");
				System.out.print (User.get("MEM_REGNO") + "\t");
				System.out.print (User.get("MEM_NUMBER") + "\t");
				System.out.print (User.get("MEM_ZIP") + "\t");
				System.out.print (User.get("MEM_ADD") + "\t");
				System.out.println ();
			}
	
            System.out.println();
            System.out.println("=========== 변경할 정보 입력 =========");
    		System.out.print("회원명>");
    		String memName = ScanUtil.nextLine();
    		System.out.print("생년월일>");
    		String memregno = ScanUtil.nextLine();
    		System.out.print("전화번호>");
    		String memnumber = ScanUtil.nextLine();
            System.out.print("우편번호>");
    		String memzip = ScanUtil.nextLine();
            System.out.print("주소>");
    		String memadd = ScanUtil.nextLine();
                
            //입력한 데이터 UserDao의 inserUser에서 Update하기
            Map<String, Object> param = new HashMap<>();
    		param.put("MEM_NM", memName);
    		param.put("MEM_REGNO", memregno);
    		param.put("MEM_NUMBER", memnumber);
            param.put("MEM_ZIP", memzip);
            param.put("MEM_ADD", memadd);
        
//    		영향 받은 행이 잇다면, 수정성공 메시지가 출력되고 그것이 아니면 실패 메시지가 생성된다.
    		int result = myDao.updateUser(param);         
              if(0 < result){
    			System.out.println(result + "개 내정보 수정 성공");
    		}else{
    			System.out.println("내정보 수정 실패");
    		}
        }
		//마이페이지홈으로 리턴 
        return View.MYPAGE_MENU; 
	}
		
	
		
		
	// 가맹점 정보수정 페이지
	public int storeModifyInfo() {
		
		System.out.println("비밀번호를 입력해 주세요");
		System.out.print("입력>");
		String password = ScanUtil.nextLine();  //비밀번호 저장
		Map<String, Object> user =myDao.selectUser(password); // 비밀번호 확인절차
		if(user == null){
			System.out.println("비밀번호를 잘못 입력하셨습니다.");
        }else{
			System.out.println("가맹점 확인");
            System.out.println();
            System.out.println("=========== 변경할 정보 =============");
    		System.out.print("가맹점  명>");
    		String buyername = ScanUtil.nextLine();
    		System.out.print("가맹점 전화번호>");
    		String buyercomtel = ScanUtil.nextLine();
    		System.out.print("가맹점 우편번호>");
    		String buyerzip = ScanUtil.nextLine();
            System.out.print("가맹점 주소>");
    		String buyeradd = ScanUtil.nextLine();
                
            //입력한 데이터 UserDao의 inserUser에서 Update하기
            Map<String, Object> param = new HashMap<>();
    		param.put("BUYER_NAME", buyername);
    		param.put("BUYER_COMTEL", buyercomtel);
    		param.put("BUYER_ZIP", buyerzip);
            param.put("BUYER_ADD", buyeradd);
           	    		
//	    		영향 받은 행이 잇다면, 수정성공 메시지가 출력되고 그것이 아니면 실패 메시지가 생성된다.
    		int result = myDao.insertStore(param);         
              if(0 < result){
    			System.out.println("내정보 수정 성공");
    		}else{
    			System.out.println("내정보 수정 실패");
    		}
        }
        //마이페이지홈으로 리턴 
        return View.MYPAGE_MENU; 
	}
	
	
	
	// 고객주문내역확인
	public int userOrderList() {
		List<Map<String, Object>> userOrderList = myDao.userOrderList();
		System.out.println("주문 정보 번호\t메뉴이름\t가맹점명\t총주문금액\t고객 주문 날짜");
		System.out.println("---------------------------------------");
		for(Map<String, Object> User : userOrderList){
			System.out.print (User.get("주문정보번호") + "\t");
			System.out.print (User.get("메뉴이름") + "\t");
			System.out.print (User.get("가맹점명") + "\t");
			System.out.print (User.get("총주문금액") + "\t");
			System.out.print (User.get("고객주문날짜") + "\t");
			System.out.println ();
		}
//		게시물을 가지고 사용자가 어떤 행위를 할지 결정한다.
		System.out.println("=======================================");
		System.out.println("1.상세조회\t2. 이전으로");
		System.out.print("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:	orderRead(); break;
		case 2: return View.MYPAGE_MENU; 
		default: System.out.println("잘못입력"); break;
		}
		return View.MYPAGE_MENU;
		
	}

	//고객 주문내역 상세조회
	public int orderRead() {
		System.out.print ("조회할 게시글 번호 입력>");
		int info_order_no = ScanUtil.nextInt ();
		
		Map<String, Object> param = new HashMap<>();
		param.put ("INFO_ORDER_NO", info_order_no);
		
		Map<String, Object> User = myDao.orderReadDetail(param);
//		게시글 내용 출력
		System.out.println ("주문정보번호 : " + User.get ("주문정보번호"));
		System.out.println ("메뉴이름 : " + User.get ("메뉴이름"));
		System.out.println ("가맹점명 : " + User.get ("가맹점명"));
		System.out.println ("총주문금액 : " + User.get ("총주문금액"));
		System.out.println ("고객주문날짜 : " + User.get ("고객주문날짜"));
		System.out.println ("주문수량 : " + User.get ("주문수량"));
		
		//재료 출력 호출
		List<Map<String, Object>> ingrList = myDao.ingrList(param);
		ArrayList<String> ingrlist = new ArrayList<>();
		for(Map<String, Object> ingr : ingrList){
			ingrlist.add(ingr.get("재료이름").toString());
		}
		System.out.println ("재료이름 : " + ingrlist.toString() );
		
//		이 게시글을 사용하여 사용자가 어떤 행위를 할지 결정한다.
		System.out.println("=======================================");
		System.out.println("1.이전으로");
		System.out.print("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
			case 1: return View.MYPAGE_MENU; 
			default: System.out.println("잘못입력"); break;
		}
		return View.MYPAGE_MENU;  
	}
		


	// 가맹점주문내역확인
	public void storeOrderList() {
		
	}
	
	
	//1대1문의 작성
	public int inquiry() {
		System.out.println("=========== 1대1문의 =============");
		System.out.print("문의 제목>");
		String inquirytitle = ScanUtil.nextLine();
		System.out.print("문의 이메일>");
		String inquiryemail = ScanUtil.nextLine();
		System.out.print("문의 전화번호>");
		String inquirynumber = ScanUtil.nextLine();
		System.out.print("문의 내용>");
		String inquirycontent = ScanUtil.nextLine();
	
		Map<String, Object> param = new HashMap<>();
		param.put("INQUIRY_TITLE", inquirytitle);
		param.put("INQUIRY_EMAIL", inquiryemail);
		param.put("INQUIRY_COMETL", inquirynumber);
		param.put("INQUIRY_CONTENT", inquirycontent);
		
		int result = myDao.insertInquiry(param);
		
//		영향 받은 행이 잇다면, 등록성공 메시지가 출력되고 그것이 아니면 실패 메시지가 생성된다.
		if(0 < result){
			System.out.println("1대1문의 등록 성공");
		}else{
			System.out.println("1대1문의 등록 실패");
		}
//		등록 완료되면 마이페이지로 이동된다.
		return View.MYPAGE_MENU;
	}
	
	
	//관리자 1대1문의 조회
	public int managerInquiry(){
		List<Map<String, Object>> inquiryList = myDao.selectInquiryList();
		System.out.println("=======================================");
		System.out.println("문의 번호\t문의 제목\t문의 작성일자");
		System.out.println("---------------------------------------");
		for(Map<String, Object> inquiry : inquiryList){
			System.out.print (inquiry.get("INQUIRY_NO") + "\t");
			System.out.print (inquiry.get("INQUIRY_TITLE") + "\t");
			System.out.print (inquiry.get("INQUIRY_ REG_DATE") + "\t");
			System.out.println ();
		}
//		게시물을 가지고 사용자가 어떤 행위를 할지 결정한다.
		System.out.println("=======================================");
		System.out.println("1.문의 상세 조회\t2. 이전으로");
		System.out.print("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:	inquiryReadDelete(); break;
		case 2: return View.MYPAGE_MENU;
		default: System.out.println("잘못입력"); break;
		}
		return View.MYPAGE_MENU;
		
	}
	//1대1문의 상세조회 후 삭제
	public int inquiryReadDelete() {
		System.out.println("=========== 게시글 조회 =============");
		System.out.print ("조회할 게시글 번호 입력>");
		int inquiry_no = ScanUtil.nextInt ();
		
		Map<String, Object> param = new HashMap<>();
		param.put ("INQUIRY_NO", inquiry_no);
		
		Map<String, Object> inquiry = myDao.selectInquiry(param);
//		68. 게시글 내용 출력
		System.out.println ("문의 번호 : " + inquiry.get ("INQUIRY_NO"));
		System.out.println ("문의 제목: " + inquiry.get ("INQUIRY_TITLE"));
		System.out.println ("문의 이메일: " + inquiry.get ("INQUIRY_EMAIL"));
		System.out.println ("문의 전화번호: " + inquiry.get ("INQUIRY_COMETL"));
		System.out.println ("문의 내용: " + inquiry.get ("INQUIRY_CONTENT"));
		System.out.println ("문의 작성일자: " + inquiry.get ("SYSDATE"));
//		69. 이 게시글을 사용하여 사용자가 어떤 행위를 할지 결정한다.
		System.out.println("=======================================");
		System.out.println("1.1:1 문의삭제\t2.이전으로");
		System.out.print("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
			case 1: deleteBoard(inquiry_no); break;
			case 2: return managerInquiry();
			default: System.out.println("잘못입력"); break;
		}
		return View.MYPAGE_MENU;  
	}

	
	//1대1 문의 삭제
	public int deleteBoard (int inquiry_no) {
		System.out.println("=========== 1:1 문의 삭제 =============");
		Map<String, Object> param = new HashMap<>();
		param.put ("INQUIRY_NO", inquiry_no);
		int result = myDao.deleteBoard(param);
		if(0 < result) {
			System.out.println ("삭제 성공");
		}else {
			System.out.println ("삭제 실패");
		}
		return View.MYPAGE_MENU;
		
	}
}

	
	