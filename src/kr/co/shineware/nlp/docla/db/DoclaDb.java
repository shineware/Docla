package kr.co.shineware.nlp.docla.db;

public class DoclaDb extends DbManager{
	private final static String TABLE_NAME = "wordDb";
	private final static String FIELD_WORD = "word";
	private final static String FIELD_TF = "tf";
	public DoclaDb(){
		super();
		super.setConnection("jdbc:sqlite:docla.word.db");
	}
	public void createTable(){
		String[] fields = {FIELD_WORD,FIELD_TF};
		super.createTable(TABLE_NAME, fields);
	}
	public void dropTable(){
		super.dropTable(TABLE_NAME);
	}
	public void incTf(String word){
		this.incTf(word, 1);
	}
	public void incTf(String word,int amountInc){
		try{
			String sql = "select * from "+TABLE_NAME+" where "+FIELD_WORD+"='"+word+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				int tf = rs.getInt(2);
				sql = String.format("update "+TABLE_NAME+" set "+FIELD_TF+"=%d where "+FIELD_WORD+"='%s'", tf+amountInc,word);
				stmt.executeUpdate(sql);
//				System.out.println("UPDATE SQL : "+sql);
			}else{
				sql = String.format("insert into "+TABLE_NAME+" values('%s',%d)", word, amountInc); 
				stmt.executeUpdate(sql);
//				System.out.println("INSERT SQL : "+sql);
			}
			rs.close(); 
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setTf(String word,int freq){
		try{
			String sql = "select * from "+TABLE_NAME+" where "+FIELD_WORD+"='"+word+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				sql = String.format("update "+TABLE_NAME+" set "+FIELD_TF+"=%d where "+FIELD_WORD+"='%s'", freq,word);
				stmt.executeUpdate(sql);
//				System.out.println("UPDATE SQL : "+sql);
			}else{
				sql = String.format("insert into "+TABLE_NAME+" values('%s',%d)", word, freq); 
				stmt.executeUpdate(sql);
//				System.out.println("INSERT SQL : "+sql);
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
			String sql = "select * from "+TABLE_NAME+" where "+FIELD_WORD+"='"+word+"'";
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
			String sql = "select * from "+TABLE_NAME;
			rs = stmt.executeQuery(sql);
			while(rs.next()){ 
				System.out.println(rs.getString(1)+","+rs.getInt(2)); 
			} 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
