package kr.co.shineware.nlp.docla.test;

import java.io.File;
import java.util.Set;

import kr.co.shineware.nlp.docla.analyzer.KomoranAnalyzer;
import kr.co.shineware.nlp.docla.core.Docla;

public class DoclaTest {
	public static void main(String[] args) throws Exception{
		Docla docla = new Docla();
		docla.setAnalyzer(new KomoranAnalyzer());
		HKIBParser parser = new HKIBParser();
		System.out.println("LOADING");
		docla.load("docla.db");
		System.out.println("LOAD DONE");
		Set<String> categoryNames = docla.getCategoryNames();
		for (String category : categoryNames) {
			System.out.println(category);
		}
		
		parser.setDoc(new File("HKIB20000/HKIB-20000_005.txt"));
		HKIBDoc doc = null;
		int correct = 0;
		int incorrect = 0;
		while((doc = parser.next()) != null){
//			System.out.println(doc);
			String category = docla.classification(doc.getText());
			if(category == null){
				incorrect++;
				continue;
			}
			String originalCategory = doc.getCategory();
//			System.out.println(category);
//			System.out.println(originalCategory);
//			System.out.println();
			if(category.equals(originalCategory)){
				correct++;
			}else{
				incorrect++;
			}
//			System.out.println(doc.getCategory());
		}
		System.out.println((double)correct/(double)(correct+incorrect));
	}
}
