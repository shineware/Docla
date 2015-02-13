package kr.co.shineware.nlp.docla.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HKIBParser {
	private BufferedReader br;
	
	public HKIBDoc next() throws Exception{
		if(br == null){
			return null;
		}
		HKIBDoc doc = new HKIBDoc();
		String line = null;
		String title = null;
		String category = null;
		boolean isTextArea = false;
		StringBuffer sb = new StringBuffer();
		
		while((line = br.readLine()) != null){
			line = line.trim();
			if(line.length() == 0){
				continue;
			}
			if(line.startsWith("@DOCUMENT")){
				isTextArea = false;
				if(category != null){
					doc.setTitle(title);
					doc.setCategory(category);
					doc.setText(sb.toString());
					return doc;
				}
				category = null;
				title = null;
				sb = null;
				sb = new StringBuffer();
			}
			if(line.startsWith("#CAT'03: ")){
				category = line.substring(9);
				continue;
			}
			if(line.startsWith("#TITLE : ")){
				title = line.substring(9);
				continue;
			}
			if(line.startsWith("#TEXT  :")){
				isTextArea = true;
				continue;
			}
			if(isTextArea){
				sb.append(line);
				sb.append("\n");
			}
		}
		br.close();
		br = null;
		if(category != null){
			doc.setCategory(category);
			doc.setText(sb.toString());
			doc.setTitle(title);
			return doc;
		}
		return null;
	}
	public void setDoc(File file){
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getDoc(File file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			String title = null;
			String category = null;
			boolean isTextArea = false;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null){
				line = line.trim();
				if(line.length() == 0){
					continue;
				}
				if(line.startsWith("@DOCUMENT")){
					isTextArea = false;
					if(category != null){
						System.out.println(category);
						System.out.println(title);
						System.out.println(sb.toString());
					}
					category = null;
					title = null;
					sb = null;
					sb = new StringBuffer();
				}
				if(line.startsWith("#CAT'03: ")){
					category = line.substring(9);
					continue;
				}
				if(line.startsWith("#TITLE : ")){
					title = line.substring(9);
					continue;
				}
				if(line.startsWith("#TEXT  :")){
					isTextArea = true;
					continue;
				}
				if(isTextArea){
					sb.append(line);
					sb.append("\n");
				}
			}
			br.close();
			if(category != null){
				System.out.println(category);
				System.out.println(title);
				System.out.println(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
