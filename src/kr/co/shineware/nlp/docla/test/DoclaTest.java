package kr.co.shineware.nlp.docla.test;

import kr.co.shineware.nlp.docla.Docla;
import kr.co.shineware.nlp.docla.analyzer.KomoranAnalyzer;

public class DoclaTest {
	public static void main(String[] args){
		Docla docla = new Docla();
//		docla.initDb();
		docla.setAnalyzer(new KomoranAnalyzer());
		docla.addDoc("news.test", "society");
		docla.addDoc("/Users/shin285/Documents/workspace_shineware/NaverNewsCrawler/content_crawler_result_test/IT일반.snippets", "it");
		String label = docla.classification("news.test");
		
	}
}
