package midTerm;

import java.io.File;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;


import org.w3c.dom.Document;



public class genSnippet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = args[3];
		String query = args[5];
		File file = new File(filename);
		String[] lines = new String[5];
		int[] chlines = new int[lines.length];
		lines[0]="라면 밀가루 달걀 밥 생선";
		lines[1]="라면 물 소금 반죽";
		lines[2]="첨부 봉지면 인기";
		lines[3]="초밥 라면 밥물 채소 소금";
		lines[4]="초밥 종류 활어";
		StringTokenizer st = new StringTokenizer(query," ");
		while(st.hasMoreElements()) {
			String key = st.nextToken().trim();
			System.out.println(key);
		}	
		
	}
	

}
