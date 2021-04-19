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

	public HashMap<String,Double> CalcSim(String postFile, String query) {
		
		
		HashMap <String,Double>hsMap = new HashMap<String,Double>();
		return hsMap;
	}
}
