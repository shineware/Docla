package kr.co.shineware.nlp.docla.analyzer;

import java.util.List;

import kr.co.shineware.interfaces.Analyzer;
import kr.co.shineware.nlp.komoran.core.analyzer.Komoran;
import kr.co.shineware.util.common.model.Pair;

public class KomoranAnalyzer implements Analyzer{
	private Komoran komoran;
	public KomoranAnalyzer(){
		komoran = new Komoran("/Users/shin285/Documents/workspace_shineware/KOMORAN_2.0_beta/models");
	}
	@Override
	public String analyze(String in) {
		StringBuffer sb = new StringBuffer();
		List<List<Pair<String,String>>> analyzeResult = komoran.analyze(in);
		for (List<Pair<String, String>> wordResultList : analyzeResult) {
			for (Pair<String, String> morphPos : wordResultList) {
				if(morphPos.getFirst().trim().length() == 0 || morphPos.getSecond().charAt(0) != 'N'){
					continue;
				}
				sb.append(morphPos.getFirst()+"/"+morphPos.getSecond()+" ");
			}
		}
		return sb.toString().trim();
	}
	
}
