package kr.co.shineware.nlp.docla;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import kr.co.shineware.nlp.docla.db.DoclaDbImpl;
import kr.co.shineware.nlp.docla.db.NaiveBayesian;
import kr.co.shineware.nlp.docla.interfaces.Analyzer;
import kr.co.shineware.nlp.docla.interfaces.DoclaDb;

public class Docla {

	private Analyzer analyzer;
	//	private DoclaDb doclaDb;
	private NaiveBayesian nb;

	public Docla(){
		//		doclaDb = new DoclaDbImpl();
		nb = new NaiveBayesian();
	}

	public void setAnalyzer(Analyzer analyzer){
		this.analyzer = analyzer;
	}

	public void print(String key){
		//		System.out.println(this.doclaDb.getTf(key));
	}

	public void addDoc(String filename,String label){
		try {
			Map<String,Integer> tfMap = new HashMap<>();
			File file = new File(filename);
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
				System.out.println(count++);
			}
			Set<Entry<String,Integer>> entrySet = tfMap.entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				nb.incCooccurence(entry.getKey(), label, entry.getValue());
				totalFreq += entry.getValue();
			}
			nb.incDf(label);
			nb.incTotalDf();
			nb.incCategoryTerm(label,totalFreq);
			//label
			//			doclaDb.incCategoryFreq(label);

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
			System.out.println(resultMap);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
