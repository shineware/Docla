package kr.co.shineware.nlp.docla;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import kr.co.shineware.nlp.docla.db.DoclaDbImpl;
import kr.co.shineware.nlp.docla.interfaces.Analyzer;
import kr.co.shineware.nlp.docla.interfaces.DoclaDb;

public class Docla {
	
	private Analyzer analyzer;
	private DoclaDb doclaDb;
	
	public Docla(){
		doclaDb = new DoclaDbImpl();
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
				//tf
				doclaDb.incTf(entry.getKey(), entry.getValue());
				//label tf co-occur
				doclaDb.incCooccurFreq(entry.getKey(), label);
			}
			//label
			doclaDb.incCategoryFreq(label);
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void train(){
		;
	}
	
	public String classification(String file) {
		
		return null;
	}
}
