package kr.co.shineware.nlp.docla.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import kr.co.shineware.nlp.docla.interfaces.DoclaDb;

public class DoclaDbImpl implements DoclaDb{
	private Map<String,Integer> cooccureFreqMap;
	private Map<String,Integer> termFreqMap;
	private Map<String,Integer> categoryFreqMap;
	private static final String COOCUR_FORMAT= "%s_%s";
	public DoclaDbImpl(){
		this.init();
	}
	public void print(){
		;
	}
	private void init() {
		cooccureFreqMap = new HashMap<>();
		termFreqMap = new HashMap<>();
		categoryFreqMap = new HashMap<>();
	}
	
	private void putIntToMap(String key,int value,Map<String,Integer> map){
		map.put(key, value);
	}
	
	private int getIntFromMap(String key, Map<String,Integer> map){
		Integer tf = map.get(key);
		if(tf == null){
			return 0;
		}
		return tf;
	}
	
	@Override
	public int getCooccurFreq(String term, String category) {
		return this.getIntFromMap(String.format(COOCUR_FORMAT, term,category), this.cooccureFreqMap);
	}

	@Override
	public int getTotalTf() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTf(String term) {
		return this.getIntFromMap(term,this.termFreqMap);
	}

	@Override
	public int getTotalCategoryFreq() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCategoryFreq(String category) {
		return this.getIntFromMap(category,this.categoryFreqMap);
	}

	@Override
	public void incTf(String term) {
		this.putIntToMap(term, 1, termFreqMap);
	}

	@Override
	public void incTf(String term, int tf) {
		this.putIntToMap(term, this.getTf(term)+tf, termFreqMap);		
	}

	@Override
	public void incCategoryFreq(String category) {
		this.incCategoryFreq(category, 1);
	}

	@Override
	public void incCategoryFreq(String category, int freq) {
		this.putIntToMap(category, this.getCategoryFreq(category)+freq, categoryFreqMap);		
	}

	@Override
	public void save(String filename) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(String filename) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void incCooccurFreq(String term, String category) {
		this.incCooccurFreq(term, category, 1);
	}
	@Override
	public void incCooccurFreq(String term, String category, int freq) {
		this.putIntToMap(String.format(COOCUR_FORMAT, term,category), freq, this.cooccureFreqMap);
	}
	@Override
	public Set<String> getTerms() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> getCategories() {
		// TODO Auto-generated method stub
		return null;
	}

}
