package kr.co.shineware.nlp.docla;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import kr.co.shineware.interfaces.Analyzer;
import kr.co.shineware.nlp.docla.db.DoclaDb;
import kr.co.shineware.util.common.collection.MapUtil;

public class Docla {
	private final static String PREFIX_WORD = "_WORD_";
	private final static String PREFIX_LABEL = "_LABEL_";
	private Analyzer analyzer;
	private DoclaDb tfDb = null;
	private DoclaDb dfDb = null;
	private DoclaDb coDb = null;
	
	public Docla(){
		tfDb = new DoclaDb();
		tfDb.createTable();
		
		dfDb = new DoclaDb();
		dfDb.createTable();
		
		coDb = new DoclaDb();
		coDb.createTable();
	}
	public void initDb(){
		tfDb.dropTable();
		tfDb.createTable();
		
		dfDb.dropTable();
		dfDb.createTable();
		
		coDb.dropTable();
		coDb.createTable();
	}
	public void setAnalyzer(Analyzer analyzer){
		this.analyzer = analyzer;
	}
	public void addDoc(String filename,String label){		
		try {
			Map<String,Integer> tfMap = new HashMap<>();
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = br.readLine()) != null){
				String rawTokens = analyzer.analyze(line).trim();
				String[] tokens = rawTokens.split(" ");
				
				for (String token : tokens) {
					if(token.trim().length() == 0)continue;
					Integer tf = tfMap.get(token);
					if(tf == null){
						tf = 0;
					}
					tf++;
					tfMap.put(token, tf);
				}
			}
			Set<Entry<String,Integer>> entrySet = tfMap.entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				tfDb.incTf(entry.getKey(), entry.getValue());
				coDb.incTf(entry.getKey()+" "+label);
			}
			
			dfDb.incTf(label);
			
			br.close();
			System.out.println(MapUtil.sortByValue(tfMap,MapUtil.DESCENDING_ORDER));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		tfDb.print();
//		dfDb.print();
		coDb.print();
	}
	public void train(){
		;
	}
}
