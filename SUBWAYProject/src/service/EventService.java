package service;

import util.View;

public class EventService {
	//이벤트 서비스
	private EventService(){}
	private static EventService instance;
	public static EventService getInstance(){
		if(instance == null){
			instance = new EventService();
		}
		return instance;
	}
	public int eventList() {
		
		return View.EVENT_LIST;
	}
	
}
