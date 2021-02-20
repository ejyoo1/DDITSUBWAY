package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import util.ScanUtil;

public class BoardDao {

	private BoardDao(){}
	private static BoardDao instance;
	public static BoardDao getInstance(){
		if(instance == null){
			instance = new BoardDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectBoardList(){
		String sql = "select a.board_no, a.title, a.content, b.user_name, a.reg_date"
				+ " from tb_jdbc_board a"
				+ " left outer join tb_jdbc_user b"
				+ " on a.user_id = b.user_id"
				+ " order by a.board_no desc";
		
		return jdbc.selectList(sql);
	}
	
	public int update(){
		String sql = "insert into tb_jdbc_board values("
				+ "(select nvl(max(board_no),0)+1 from tb_jdbc_board)"
				+ ",?,?,?,sysdate)";
		
		System.out.print("제목입력 > ");
		String title = ScanUtil.nextLine();
		System.out.print("내용입력 > ");
		String content = ScanUtil.nextLine();
		System.out.print("작성자입력 > ");
		String user = ScanUtil.nextLine();
		
		List<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(user);
		return jdbc.update(sql, param);
	}
	
}