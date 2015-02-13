package kr.co.shineware.nlp.docla.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NaiveBayesian{
	
	private Map<String, Integer> dfMap;
	private Map<String, Integer> cooccurMap;
	private Map<String, Integer> categoryTermMap;
	private int totalDf;
	
	public NaiveBayesian(){
		this.init();
	}
	
	public void save(String filename){
		ObjectOutputStream dos;
		try {
			dos = new ObjectOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(filename))));
			dos.writeObject(dfMap);
			dos.writeObject(cooccurMap);
			dos.writeObject(categoryTermMap);
			dos.writeInt(totalDf);
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void load(String filename){
		ObjectInputStream dis;
		try {
			dis = new ObjectInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(filename))));
			dfMap = (Map<String, Integer>) dis.readObject();
			cooccurMap = (Map<String, Integer>) dis.readObject();
			categoryTermMap = (Map<String, Integer>) dis.readObject();
			totalDf = dis.readInt();
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		this.cooccurMap = null;
		this.cooccurMap = new HashMap<String, Integer>();
		
		this.categoryTermMap = null;
		this.categoryTermMap = new HashMap<String, Integer>();
		
		this.dfMap = null;
		this.dfMap = new HashMap<String, Integer>();
		
		this.totalDf = 0;
	}
	
	public void incCooccurence(String term,String category){
		this.incCooccurence(term, category,1);
	}
	public void incCooccurence(String term, String category, int inc) {
		this.updateMap(term+"_"+category, inc,this.cooccurMap);
	}
	
	public void incCategoryTerm(String category){
		this.incCategoryTerm(category,1);
	}
	
	public void incCategoryTerm(String category, int inc) {
		this.updateMap(category, inc, this.categoryTermMap);		
	}

	public void incDf(String categoryName){
		this.incDf(categoryName,1);
	}
	public void incDf(String categoryName, int inc) {
		this.updateMap(categoryName, inc, this.dfMap);
	}
	
	public void incTotalDf(){
		this.incTotalDf(1);
	}
	
	public void incTotalDf(int inc) {
		this.totalDf += inc;		
	}
	
	public int getCooccurence(String term, String category){
		return this.getValue(term+"_"+category, this.cooccurMap)+1;
	}
	
	public int getCategoryTermsFreq(String category){
		return this.getValue(category, this.categoryTermMap)+1;
	}
	
	public int getDf(String categoryName){
		return this.getValue(categoryName, this.dfMap);
	}
	
	public int getTotalDf(){
		return this.totalDf;
	}
	
	public Set<String> getCategoryNames(){
		return this.dfMap.keySet();
	}
	
	private int getValue(String key,Map<String,Integer> map){
		Integer value = map.get(key);
		if(value == null){
			return 0;
		}
		return value;
	}
	private void updateMap(String key,int value,Map<String,Integer> map){
		Integer freq = map.get(key);
		if(freq == null){
			freq = 0;
		}
		map.put(key, freq+value);
	}
}
