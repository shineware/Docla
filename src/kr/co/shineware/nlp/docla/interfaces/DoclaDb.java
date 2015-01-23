package kr.co.shineware.nlp.docla.interfaces;

public interface DoclaDb {
	public int getCooccurFreq(String term,String category);
	public int getTotalTf();
	public int getTf(String term);
	public int getTotalCategoryFreq();
	public int getCategoryFreq(String category);
	
	public void incCooccurFreq(String term,String category);
	public void incCooccurFreq(String term,String category,int freq);
	public void incTf(String term);
	public void incTf(String term,int tf);
	public void incCategoryFreq(String category);
	public void incCategoryFreq(String category,int freq);
	
	public void save(String filename);
	public void load(String filename);
}
