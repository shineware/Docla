package kr.co.shineware.nlp.docla;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import kr.co.shineware.interfaces.Analyzer;
import kr.co.shineware.util.common.collection.MapUtil;

public class Docla {
	private Analyzer analyzer;
	public Docla(){
		;
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
			br.close();
			System.out.println(MapUtil.sortByValue(tfMap,MapUtil.DESCENDING_ORDER));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void train(){
		;
	}
}
