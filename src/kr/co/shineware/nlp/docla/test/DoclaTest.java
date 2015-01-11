package kr.co.shineware.nlp.docla.test;

import kr.co.shineware.nlp.docla.Docla;
import kr.co.shineware.nlp.docla.analyzer.KomoranAnalyzer;
import kr.co.shineware.nlp.docla.db.DbManager;

public class DoclaTest {
	public static void main(String[] args){
		Docla docla = new Docla();
		docla.setAnalyzer(new KomoranAnalyzer());
		docla.addDoc("news.test", "sports");
		
		DbManager dbm = new DbManager();
//		dbm.dropTable();
//		dbm.creatTable();
//		dbm.test();
		int tf = dbm.getTf("감기");
		System.out.println(tf);
		dbm.add("감기", 2);
		dbm.remove("감기");
		dbm.printTables();
	}
}
