package kr.co.shineware.nlp.docla.test;

public class HKIBDoc {
	private String title;
	private String category;
	private String text;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String toString(){
		return title+"\n"+category+"\n"+text;		
	}
}
