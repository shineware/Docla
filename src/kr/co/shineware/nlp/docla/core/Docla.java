package kr.co.shineware.nlp.docla.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import kr.co.shineware.nlp.docla.db.NaiveBayesian;
import kr.co.shineware.nlp.docla.interfaces.Analyzer;
import kr.co.shineware.nlp.docla.util.MonitorMemory;
import kr.co.shineware.util.common.collection.MapUtil;

public class Docla {

	private Analyzer analyzer;
	private NaiveBayesian nb;

	public Docla(){
		nb = new NaiveBayesian();
	}

	public void setAnalyzer(Analyzer analyzer){
		this.analyzer = analyzer;
	}

	public void addDoc(String contents, String label){
		int totalFreq = 0;
		Map<String,Integer> tfMap = new HashMap<>();
		
		String rawTokens = analyzer.analyze(contents).trim();
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
		
		rawTokens = null;
		tokens = null;
		
		Set<Entry<String,Integer>> entrySet = tfMap.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			nb.incCooccurence(entry.getKey(), label, entry.getValue());
			totalFreq += entry.getValue();
		}
		nb.incDf(label);
		nb.incTotalDf();
		nb.incCategoryTerm(label,totalFreq);
	}
	
	public void addDoc(File file,String label){
		try {
			Map<String,Integer> tfMap = new HashMap<>();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			int totalFreq = 0;
			int count = 0;
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
				if(count++ % 1000 == 0){
					MonitorMemory.showMemory();
					System.out.println(tfMap.size());
				}
				rawTokens = null;
				tokens = null;
				line = null;
			}
			Set<Entry<String,Integer>> entrySet = tfMap.entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				nb.incCooccurence(entry.getKey(), label, entry.getValue());
				totalFreq += entry.getValue();
			}
			nb.incDf(label);
			nb.incTotalDf();
			nb.incCategoryTerm(label,totalFreq);

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void train(){
		//		int totalTf = doclaDb.getTotalTf();
	}

	public String classification(String filename) {
		try{
			Map<String, Double> resultMap = new HashMap<>();
			Set<String> categoryNames = nb.getCategoryNames();
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = br.readLine()) != null){
				String rawTokens = analyzer.analyze(line).trim();
				String[] tokens = rawTokens.split(" ");

				for (String categoryName : categoryNames) {
					double categoryResult = 0.0;
					for (String token : tokens) {
						if(token.trim().length() == 0)continue;
						double condProb = (double)nb.getCooccurence(token, categoryName) / nb.getCategoryTermsFreq(categoryName);
						double postProb = (double)nb.getDf(categoryName) / nb.getTotalDf();
						categoryResult += (Math.log(condProb) + Math.log(postProb));
					}
					if(categoryResult == 0.0)continue;
					Double resultScore = resultMap.get(categoryName);
					if(resultScore == null){
						resultScore = 0.0;
					}
					resultScore += categoryResult;
					resultMap.put(categoryName, resultScore);
				}
			}
			resultMap = MapUtil.sortByValue(resultMap, MapUtil.DESCENDING_ORDER);
			Set<Entry<String,Double>> entrySet = resultMap.entrySet();
			int nbest = 5;
			for (Entry<String, Double> entry : entrySet) {
				System.out.println(entry.getKey()+" : "+entry.getValue());
				nbest--;
				if(nbest == 0){
					break;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
