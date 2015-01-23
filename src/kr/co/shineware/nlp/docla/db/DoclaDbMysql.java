package kr.co.shineware.nlp.docla.db;

public class DoclaDbMysql extends DbManager{
	
	private String tableName = "wordDb";
	private final static String FIELD_WORD = "token";
	private final static String FIELD_TF = "freq";
	
	public DoclaDbMysql(String tableName){
		super();
		super.setConnection("jdbc:sqlite:docla.freq.db");
		this.tableName = tableName;
	}
	public void createTable(){
		String[] fields = {FIELD_WORD,FIELD_TF};
		super.createTable(tableName, fields);
	}
	public void dropTable(){
		super.dropTable(tableName);
	}
	public void incTf(String word){
		this.incTf(word, 1);
	}
	public void incTf(String word,int amountInc){
		try{
			String sql = "select * from "+tableName+" where "+FIELD_WORD+"='"+word+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				int tf = rs.getInt(2);
				sql = String.format("update "+tableName+" set "+FIELD_TF+"=%d where "+FIELD_WORD+"='%s'", tf+amountInc,word);
				stmt.executeUpdate(sql);
			}else{
				sql = String.format("insert into "+tableName+" values('%s',%d)", word, amountInc); 
				stmt.executeUpdate(sql);
			}
			rs.close(); 
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setTf(String word,int freq){
		try{
			String sql = "select * from "+tableName+" where "+FIELD_WORD+"='"+word+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				sql = String.format("update "+tableName+" set "+FIELD_TF+"=%d where "+FIELD_WORD+"='%s'", freq,word);
				stmt.executeUpdate(sql);
			}else{
				sql = String.format("insert into "+tableName+" values('%s',%d)", word, freq); 
				stmt.executeUpdate(sql);
			}
			rs.close(); 
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public int getTf(String word){
		int tf = 0;
		try{
			String sql = "select * from "+tableName+" where "+FIELD_WORD+"='"+word+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
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
	public void print() {
		try{
			String sql = "select * from "+tableName;
			rs = stmt.executeQuery(sql);
			while(rs.next()){ 
				System.out.println(rs.getString(1)+","+rs.getInt(2)); 
			} 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
