package kr.co.shineware.nlp.docla.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager {
	private static Connection con; 
	private static Statement stmt; 
	private static ResultSet rs;
	public DbManager(){
		// 1. Driver를 로딩한다.
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("JDBC의 로딩이 정상적으로 이뤄졌습니다."); 

			// 2. Connection 얻어오기
			con = DriverManager.getConnection("jdbc:sqlite:test.db"); 
			System.out.println("데이터베이스의 연결에 성공하였습니다."); 

			// 3. Statement 얻기 --> 쿼리문 작성하여 적용하기 위한 용도 
			stmt = con.createStatement(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void dropTable(){
		try {
			stmt.executeUpdate("drop table if exists test;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void creatTable(){
		try {
			stmt.executeUpdate("create table test (term, tf);");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getTf(String word){
		int tf = 0;
		try{
			String sql = "select * from test where term='"+word+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				//				System.out.println(rs.getString("term"));
				tf = rs.getInt(2);
			}else{
				;
			}
			rs.close(); 
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tf;
	}
	public void add(String word,int freq){
		try{
			String sql = "select * from test where term='"+word+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				//				System.out.println(rs.getString("term"));
				int tf = rs.getInt(2);
				sql = String.format("update test set tf=%d where term='%s'", tf+freq,word);
				stmt.executeUpdate(sql);
				System.out.println("UPDATE SQL : "+sql);
			}else{
				sql = String.format("insert into test values('%s',%d)", word, freq); 
				stmt.executeUpdate(sql);
				System.out.println("INSERT SQL : "+sql);
			}
			rs.close(); 
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void printTables(){
		try{
			String sql = "select * from test";
			rs = stmt.executeQuery(sql);
			while(rs.next()){ 
				System.out.println(rs.getString(1)+","+rs.getInt(2)); 
			} 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void test(){
		try{

			// 4. 쿼리문 실행 -->> insert into (자동으로 commit 됩니다.) 
			String sql= "insert into test values(2,'철이','10','경기도')"; 
			stmt.executeUpdate(sql); 

			// 5. 업데이트  -->> update 합니다.  (자동으로 commit 됩니다.) 
			sql = "update test set addr='별나라'" + "where name='순이'"; 
			stmt.executeUpdate(sql); 

			// 6. Delete 삭제  -->> delete 합니다. (자동으로 commit 됩니다.) 
			sql = "delete from test where name='똘이장군'"; 
			stmt.executeUpdate(sql); 

			// 7. Select문 실행하여 데이터베이스 내용 출력하기 
			sql = "select name from test where name='순이'"; 
			rs = stmt.executeQuery(sql);

			while(rs.next()){ 
				System.out.println(rs.getString(1)); 
			} 
			rs.close(); 
			stmt.close(); 
			con.close(); 
		}catch(Exception cnfe){ 
			cnfe.printStackTrace(); 
		} 

	}
	public void remove(String word) {
		try{
			String sql = "delete from test where term='"+word+"'"; 
			stmt.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
