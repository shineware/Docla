package kr.co.shineware.nlp.docla.test;

import kr.co.shineware.nlp.docla.Docla;
import kr.co.shineware.nlp.docla.analyzer.KomoranAnalyzer;

public class DoclaTest {
	public static void main(String[] args){
		Docla docla = new Docla();
		docla.initDb();
		docla.setAnalyzer(new KomoranAnalyzer());
		docla.addDoc("news.test", "sports");
	}
}
