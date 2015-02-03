package kr.co.shineware.nlp.docla.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HKIBParser {
	public boolean hasNext(){
		return false;
	}
	public void getDoc(File file){
		/*
		 * @DOCUMENT
#DocID : 10646
#CAT'03: /건강과 의학/의약학/한의학 전통의학
#CAT'07: /정치/정부부처/보건복지부
#TITLE : 한약재 규격품사용 의무화
#TEXT  : 
		 */
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
