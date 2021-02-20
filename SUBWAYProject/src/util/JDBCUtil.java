package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {
//	싱클톤 패턴 : 인스턴스의 생성을 제한하여 하나의 인스턴스만사용하는 디자인 패턴
//	클래스 내부에서 객체를 하나만 만들고 다른 클래스에서 필요하다고 하면 빌려주는 형태임.( 객체를 딱 한개만 생성한다 ! => 외부에서 객체를 못만들게 해야함. )
	
	//객체 생성은 딱 한개만 할 수 있도록 제한.(클래스 외부에서 이 클래스의 객체를 생성하지 못함. = 생성자를 호출할 수 없으므로)
	private JDBCUtil () {
		
	}
	
//	객체 생성은 클래스 내부에서 생성한다.
//	변수 생성 : 인스턴스를 보관할 변수
	private static JDBCUtil instance;
	
//	인스턴스를 빌려주는 메서드
//	이 메서드가 처음으로 호출하는 경우 객체를 생성하고 두번째 부터는 이미 생성된 객체를 반환함.
	public static JDBCUtil getInstance() {
		if(instance == null) {
			instance = new JDBCUtil();
		}
		return instance;
	}
	
	
//	JDBC 연결
	String url 	= "jdbc:oracle:thin:@localhost:1521:xe";
	String id	= "pc19";
	String pw	= "java";
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	
//	메서드 생성(ibatis, mybatis와 비슷한 구조 : jdbc를 편히 사용할 수 잇게 하는 메서드임.)
	/* selectOne : 한줄을 조회하는 메서드
	 * selectList : 여러줄을 조회하는 메서드
	 * update : insert, update, delete 시 사용하는 메서드
	 * (String sql) : 물음표가 없는 경우
	 * (String sql, List<Object> param) : 물음표가 있는 경우
	 * 
	 * Map<String, Object> selectOne(String sql){}
	 * Map<String, Object> selectOne(String sql, List<Object> param){}
	 * List<Map<String, Object>> selectList(String sql){}
	 * List<Map<String, Object>> selectList(String sql, List<Object> param){}
	 * int update(String sql){}
	 * int update(String sql, List<Object> param){}
	 * 
	 * */
//	한줄 쿼리
//	sql을 파라미터로 가져와서 디비에 연결한 뒤 쿼리를 실행해서 한줄 결과를 가져온 뒤 맵에 담아서 리턴
	public Map<String, Object> selectOne(String sql){//다른 패키지에도 사용할 수 있어야 되므로 'public' 접근 제어자를 붙임
//		쿼리를 조회했는데 한줄이 무조건 조회 될 수 있다는 보장이 없으므로, 여기에선 null을 하고 실제 데이터가 잇을 때(rs.next() = true)일 때, 객체를 생성하도록 한다.
//		나중에 쿼리를 사용 시 결과값이 정상적으로 조회됐는지 확인을 하는데 if(row==null){}을 하여 조회되지 않는 경우를 조회할 수 있다.
//		그런데 이 부분을 null을 하지않고 객체를 생성한 경우 정상적으로 조회됐는지의 여부를 if(row.size() == 0){}로 확인해야된다.
//		if(row.size()==0){}보다는 if(row==null){}이 더 직관적임.
		Map<String, Object> row = null;
		try {
//			DB연결
			con = DriverManager.getConnection (url,id,pw);
//			쿼리실행
//			쿼리를 실행하고
			ps = con.prepareStatement (sql);
//			실행(select)
//			몇개에 영향을 받았는지 결과값을 리턴
			rs = ps.executeQuery ();
//			resultSet에 무슨 데이터가 잇는지 모르기에 메타데이터를 얻고
			ResultSetMetaData metaData = rs.getMetaData ();
//			컬럼 수를 알면 데이터를 뽑을 수 잇음.
			int columnCount = metaData.getColumnCount ();
//			값을 추출
//			만약에 여러줄이 조회되는 경우 이 부분에 여러줄의 결과를 가질 수 있는 로직을 구현해서 리턴을 제어하면 됨.
//			무조건 한줄이라는 보장은 없음.
//			여기서 만약에 문제가 생기면 여러줄의 조회가 된 테이블에서 가장 마지막에 조회된 테이블이 출력될것임.
			while(rs.next()) {//true
				row = new HashMap<>();
				for(int i = 1 ; i <= columnCount ; i++) {
//					for문 안으로 들어왔다는 것은 데이터가 존재한다는 것이므로 해쉬맵 객체를 생성한다.
	//				추출한 내용을 HashMap<String, Object> 에 담아서 리턴함.
	//				해쉬맵에 put 해서 저장(키:컬럼명, 값:컬럼값)
	//				무엇을 가져왓는지 모르기에 getObject로 받음
					row.put (metaData.getColumnName (i), rs.getObject (i));//접근 시 1번부터 시작함.
				}//close for
			}//close if
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//			객체 반환
			if( rs != null ) try { rs.close (); } catch(Exception e) {}
			if( ps != null ) try { ps.close (); } catch(Exception e) {}
			if( con != null ) try { con.close (); } catch(Exception e) {}
		}//close finally
		return row;		
	}
//	한줄쿼리 물음표 있음
	public Map<String, Object> selectOne(String sql, List<Object> param){
		Map<String, Object> row = null;
		try {
//			DB 연결
			con = DriverManager.getConnection (url,id,pw);
//			쿼리실행
			ps = con.prepareStatement (sql);
//			메서드 사용하여 값을 sql에 넘겨주어야 함.
//			list : 여러개 순서대로 이므로 for 문을 돌려서 넘겨줌
			for(int i = 0 ; i < param.size () ; i++) {
//				물음표 모두 세팅 완료. (list된 타입이 어떤 타입인지 모르고 제네릭이 <Object> 이므로 Object로 받은것임.
				ps.setObject ( i + 1 , param.get (i) );//물음표는 1부터 시작하므로 +1을 함.
			}
//			실행(select)
			rs =ps.executeQuery ();
//			resultSet에 무슨 데이터가 있는지 모르기에 메타데이터를 얻고
			ResultSetMetaData metaData = rs.getMetaData ();
//			컬럼 수를 알면 데이터를 뽑을 수 있음
			int columnCount = metaData.getColumnCount ();
//			값을 추출
			while(rs.next()) {
				row = new HashMap<>();
				for(int i = 1 ; i <= columnCount ; i++) {
//					추출한 내용을 HashMap<String, Object>에 담아서 리턴함.
//					해쉬맵에 put 해서 저장(키:커럼명, 값:컬럼값)
//					무엇을 가져왔는지 모르기에 getObject로 받음
					row.put (metaData.getColumnName (i), rs.getObject(i));
				}//close for
			}//close if
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//			객체 반환
			if( rs != null ) try { rs.close (); } catch(Exception e) {}
			if( ps != null ) try { ps.close (); } catch(Exception e) {}
			if( con != null ) try { con.close (); } catch(Exception e) {}
		}//close finally
		return row;
	}
	
//	물음표가 없는 여러줄 쿼리 select
	public List<Map<String, Object>> selectList(String sql){
		List<Map<String, Object>> list = new ArrayList<>();
		try {
//			DB 연결
			con = DriverManager.getConnection (url,id,pw);
//			쿼리 실행
			ps = con.prepareStatement (sql);
//			실행(select)
			rs = ps.executeQuery ();
//			resultSet에 무슨 데이터가 있는지 모르기에 메타데이터를 얻고
			ResultSetMetaData metaData = rs.getMetaData ();
//			컬럼 수를 알면 데이터를 뽑을 수 잇음.
			int columnCount = metaData.getColumnCount ();
//			값을 추출
			while(rs.next()) {
//				한줄한줄 가져온 행을 해쉬맵에 담아서(우리가 알고있는 맵이 해쉬맵뿐) list에 저장해야 하는 목적을 가진 hashmap을 생성함.
				HashMap<String, Object> row = new HashMap<>();
//				한줄 한줄을 해쉬맵에 담음.
				for(int i = 1 ; i <= columnCount ; i++) {
//					추출한 내용을 List<Map<String, Object>> list = new ArrayList<>();에 담아서 리턴하는 목적임.
//					한줄을 해쉬맵 형태로 만들어서 담아야함. 그래서 for문 돌리기 전에 hashmap을 만듬
//					해쉬맵에 put 해서 저장(키:컬럼명, 값:컬럼값)
//					무엇을 가져왔을 지 모르기에 getObject로 받음.
					row.put (metaData.getColumnName (i), rs.getObject (i));
				}//close for
//				arrList에 저장
				list.add (row);
			}//close while
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//			객체 반환
			if( rs != null ) try { rs.close (); } catch(Exception e) {}
			if( ps != null ) try { ps.close (); } catch(Exception e) {}
			if( con != null ) try { con.close (); } catch(Exception e) {}
		}//close finally
		
		return list;
	}
//	해야될것? 
//	DB 연결 -> 넘어온 쿼리 실행 -> 물음표가 있으니 list param에 있는것을 sql에 삽입 -> result 셋이 왓으면 값을 추출한 뒤 -> list<map>형식으로 만들어서 리턴을 함.
	public List<Map<String, Object>> selectList(String sql, List<Object> param){
//		리턴타입에 맞는 객체 생성
		List<Map<String, Object>> list = new ArrayList<>();
		try {
//			DB 연결
			con = DriverManager.getConnection (url,id,pw);
//			쿼리 실행
			ps = con.prepareStatement (sql);
//			메서드 사용하여 값을 sql에 넘겨주어야 함.
//			list : 여러개 순서대로 이므로 for 문을 돌려서 넘겨줌
			for(int i = 0 ; i < param.size () ; i++) {
//				물음표 모두 세팅 완료. (list된 타입이 어떤 타입인지 모르고 제네릭이 <Object> 이므로 Object로 받은것임.
				ps.setObject ( i + 1 , param.get (i) );//물음표는 1부터 시작하므로 +1을 함.
			}
//			실행(select)			
			rs = ps.executeQuery ();//resultSet
//			resultSet에 무슨 데이터가 잇는지 모르기에 메타데이터를 얻고
			ResultSetMetaData metaData = rs.getMetaData ();
//			컬럼 수를 알면 데이터를 뽑을 수 있음.
			int columnCount = metaData.getColumnCount ();
//			값을 추출
			while(rs.next ()) {
//				한줄한줄 가져온 행을 해쉬맵에 담아서 list에 저장해야하는 목적을 가진 hashmap을 생성
				HashMap<String, Object> row = new HashMap<>();
				for(int i = 1 ; i <= columnCount ; i++) {//1부터 시작해야함(0부터 시작하면 오류남)
//					추출한 내용을 List<Map<String, Object>> list = new ArrayList<>(); 담아서 리턴해야됨.
//					한줄을 해쉬맵 형태로 만들어서 담아야함. 그래서 for문 돌리기전에 hashmap을 만듬
//					해쉬맵에 put해서 저장(키:컬럼명, 값:컬럼값)
					row.put (metaData.getColumnName (i), rs.getObject (i));//디비는 1부터 인덱스 계산을 함.
				}
				list.add (row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//			객체 반환
			if( rs != null ) try { rs.close (); } catch(Exception e) {}
			if( ps != null ) try { ps.close (); } catch(Exception e) {}
			if( con != null ) try { con.close (); } catch(Exception e) {}
		}
		
		
		return list;
	}
	
	public int update(String sql) {
//		리턴 타입 변수 생성
		int result = 0;
		try {
//			DB 연결
			con = DriverManager.getConnection(url,id,pw);
			
//			쿼리 실행
			ps = con.prepareStatement (sql);
			
//			쿼리 결과 삽입
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//			객체 반환
//			if( rs != null ) try { rs.close (); } catch(Exception e) {}
			if( ps != null ) try { ps.close (); } catch(Exception e) {}
			if( con != null ) try { con.close (); } catch(Exception e) {}
		}//close finally
		return result;
	}
	
	public int update(String sql, List<Object> param) {
		int result = 0;
		try {
//			db 연결
			con = DriverManager.getConnection (url,id,pw);
//			쿼리 실행
			ps = con.prepareStatement (sql);
//			메서드 사용하여 값을 sql에 넘겨주어야 함.
//			list : 여러개 순서대로 이므로 for 문을 돌려서 넘겨줌
			for(int i = 0 ; i < param.size () ; i++) {
//				물음표 모두 세팅 완료. (list된 타입이 어떤 타입인지 모르고 제네릭이 <Object> 이므로 Object로 받은것임.
				ps.setObject ( i + 1 , param.get (i) );//물음표는 1부터 시작하므로 +1을 함.
			}
			
			result = ps.executeUpdate ();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
