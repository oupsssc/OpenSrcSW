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
		lines[0]="��� �а��� �ް� �� ����";
		lines[1]="��� �� �ұ� ����";
		lines[2]="÷�� ������ �α�";
		lines[3]="�ʹ� ��� �买 ä�� �ұ�";
		lines[4]="�ʹ� ���� Ȱ��";
		StringTokenizer st = new StringTokenizer(query," ");
		while(st.hasMoreElements()) {
			String key = st.nextToken().trim();
			System.out.println(key);
		}	
		
	}
	

}
