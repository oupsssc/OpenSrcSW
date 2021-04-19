package openS0311;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class searcher {
	public searcher(String postFile, String query) {
		HashMap<String, Double> simMap = CalcSim(postFile, query);
		List<Entry<String, Double>> list = new ArrayList<>(simMap.entrySet());
		list.sort(Entry.comparingByValue());
		for(int i=0;i<list.size()-1;i++) {
			for(int j=i;j<list.size()-1;j++) {
				double tmp1=list.get(j).getValue();
				double tmp2=list.get(j+1).getValue();
				if(tmp1==tmp2) {
					String[] buf1 = list.get(j).getKey().split(":");
					String[] buf2 = list.get(j+1).getKey().split(":");
					if(Integer.parseInt(buf1[1])<Integer.parseInt(buf2[1])){
						Collections.swap(list,j,j+1);
					}
				}
			}
			
		}
		for(int i=list.size()-1;i>list.size()-4;i--) {
			String[] buf = list.get(i).getKey().split(":");
			System.out.println(buf[0]);
		}

	}

	public HashMap<String,Double> InnerProduct(String postFile, String query) {
		
		try {
			String que = query;
			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = null;
			Keyword kwrd = null;
			kl = ke.extractKeyword(que, true);
			String[] queName = new String[kl.size()];
			int[] queWeight = new int[kl.size()];
			for(int i=0;i<kl.size();i++) {
				kwrd = kl.get(i);
				queName[i]=kwrd.getString();
				queWeight[i]=1;
			}		
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();	//collection.xml에서 title
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document collection = docBuilder.parse("collection.xml");
			Element root = collection.getDocumentElement();
			Element el = null;
			Element sub_el = null;
			NodeList doc_list=root.getElementsByTagName("doc");
			NodeList n_list= null;
			String[] title_arr = new String[doc_list.getLength()];
			String[] id_arr = new String[doc_list.getLength()];
			for(int i=0;i<doc_list.getLength();i++) {
				el = (Element)doc_list.item(i);	//Node를 Element로 형변환
				id_arr[i]=el.getAttribute("id");
				n_list = el.getElementsByTagName("title");
				for(int k=0;k<n_list.getLength();k++) {
					sub_el = (Element)n_list.item(k);
					title_arr[i]=sub_el.getFirstChild().getNodeValue();
				}
			}
					
			
			FileInputStream filein = new FileInputStream(postFile);
			ObjectInputStream obin = new ObjectInputStream(filein);
			Object ob = obin.readObject();
			obin.close();
			HashMap hsmap = (HashMap) ob;
			
			HashMap <String,Double>simMap = new HashMap<String,Double>();

			for(int i=0;i<doc_list.getLength();i++) {	//각 문서별로
				simMap.put(title_arr[i]+":"+id_arr[i],0.0);
				for(int j=0;j<kl.size();j++) {	//query의 키워드가
					if(hsmap.containsKey(queName[j])) {	//index.post에 있으면
						ArrayList<String> value = (ArrayList<String>)hsmap.get(queName[j]);
						for(String v:value) {
							String[] buf = v.split(" ");
							if(Integer.parseInt(buf[0])==i) {
								double tmp = simMap.get(title_arr[i]+":"+id_arr[i]);
								simMap.put(title_arr[i]+":"+id_arr[i], tmp+Double.parseDouble(buf[1])*(double)queWeight[j]);
							}
						}
					}
				}
			}
			return simMap;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
		HashMap <String,Double>falseMap = new HashMap<String,Double>();
		return falseMap;
	}
	
	public HashMap<String,Double> CalcSim(String postFile, String query) {
		
		HashMap <String,Double>hsMap = new HashMap<String,Double>();
		return hsMap;

	}
}
