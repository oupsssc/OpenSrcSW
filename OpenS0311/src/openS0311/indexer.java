package openS0311;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public indexer(String indexFile) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document index = docBuilder.parse(indexFile);
			Element root = index.getDocumentElement();
			Element el = null;
			Element sub_el = null;
			NodeList doc_list=root.getElementsByTagName("doc");
			NodeList n_list= null;
			String[] title_arr = new String[doc_list.getLength()];
			String[] body_arr = new String[doc_list.getLength()];
			String[] id_arr = new String[doc_list.getLength()];
			
			for(int i=0;i<doc_list.getLength();i++) {
				el = (Element)doc_list.item(i);	//Node를 Element로 형변환
				id_arr[i]=el.getAttribute("id");
				n_list = el.getElementsByTagName("title");
				for(int k=0;k<n_list.getLength();k++) {
					sub_el = (Element)n_list.item(k);
					title_arr[i]=sub_el.getFirstChild().getNodeValue();
				}
				n_list = el.getElementsByTagName("body");
				for(int k=0;k<n_list.getLength();k++) {
					sub_el = (Element)n_list.item(k);
					body_arr[i]=sub_el.getFirstChild().getNodeValue();
				}
			}
			
			HashMap<String,Integer> dfMap = new HashMap<String,Integer>();
			
			for(int i=0;i<body_arr.length;i++) {	//dfMap init
				StringTokenizer st = new StringTokenizer(body_arr[i],":|#");
				while(st.hasMoreElements()) {
					String key = st.nextToken().trim();
					if(dfMap.containsKey(key)) {
						dfMap.put(key, dfMap.get(key)+1);		// df를 1 올려줌
					}
					else {
						dfMap.put(key,1);
					}
					st.nextToken();
				}	
			}
			int tf;
			double temp,w;
			ArrayList<String> list;
			
			HashMap<String,List> indexMap = new HashMap<String,List>();
			for(int i=0;i<body_arr.length;i++) {	
				StringTokenizer st = new StringTokenizer(body_arr[i],":|#");
				while(st.hasMoreElements()) {
					String key = st.nextToken().trim();
					tf = Integer.parseInt(st.nextToken().trim());
					temp = tf*Math.log(doc_list.getLength()/(double)dfMap.get(key));
					w = Math.round(temp*100)/100.0;
					if(i!=0&&indexMap.containsKey(key)) {	//i가 첫번째일때는 중복아니므로 제외
						//value의 리스트에 추가후 put
						list=(ArrayList<String>) indexMap.get(key);
						list.add(id_arr[i]+" "+w);
						indexMap.put(key, list);
					}
					else {
						list=new ArrayList<String>();
						list.add(id_arr[i]+" "+w);
						indexMap.put(key,list);
					}
				}	
			}
			
			FileOutputStream fileStream = new FileOutputStream("index.post");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
			
			objectOutputStream.writeObject(indexMap);
			objectOutputStream.close();
	
			
//			출력확인
//			try {	
//				FileInputStream filein = new FileInputStream("index.post");
//				ObjectInputStream obin = new ObjectInputStream(filein);
//				Object ob = obin.readObject();
//				obin.close();
//				System.out.println("type : "+ob.getClass());
//				HashMap hsmap = (HashMap) ob;
//				Iterator<String> it = hsmap.keySet().iterator();
//				while(it.hasNext()) {
//					String key1 = it.next();
//					ArrayList<String> value = (ArrayList<String>) hsmap.get(key1);
//					System.out.println(key1 + " -> "+value );
//				}
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}			
			
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
