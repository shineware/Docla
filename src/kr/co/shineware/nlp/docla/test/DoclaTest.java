package kr.co.shineware.nlp.docla.test;

import java.io.File;

import kr.co.shineware.nlp.docla.analyzer.KomoranAnalyzer;
import kr.co.shineware.nlp.docla.core.Docla;

public class DoclaTest {
	public static void main(String[] args){
//		Docla docla = new Docla();
//		docla.setAnalyzer(new KomoranAnalyzer());
//		long begin = System.currentTimeMillis();
//		docla.addDoc("news.test", "society");
//		docla.addDoc("/Users/shin285/Documents/workspace_shineware/NaverNewsCrawler/content_crawler_result_test/IT일반.snippets", "it");
//		String label = docla.classification("news.test");
//		long end = System.currentTimeMillis();
//		
//		System.out.println("Elapsed time = "+(end-begin)/1000.0+" sec");
//		
		HKIBParser parser = new HKIBParser();
		parser.getDoc(new File("HKIB20000/HKIB-20000_001.txt"));
		
	}
}
