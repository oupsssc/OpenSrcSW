package openS0311;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makeKeyword {
	public makeKeyword(String colFile) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document collection = docBuilder.parse(colFile);
			Element root = collection.getDocumentElement();
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

			String[] indexbody_arr = new String[body_arr.length];
			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = null;
			Keyword kwrd = null;
			for(int i=0;i<indexbody_arr.length;i++) {
				kl = ke.extractKeyword(body_arr[i], true);
				indexbody_arr[i]="";
				for(int j=0;j<kl.size();j++) {
					kwrd = kl.get(j);
					indexbody_arr[i]+=kwrd.getString();
					indexbody_arr[i]+=":";
					indexbody_arr[i]+=kwrd.getCnt();
					if(j==kl.size()-1)break;
					indexbody_arr[i]+="#";
				}
			}
			
					
			
			
			Document index = docBuilder.newDocument();
			index.setXmlStandalone(true);
			Element docs = index.createElement("docs");
			index.appendChild(docs);
			
			Element doc;
			Element title;
			Element body;
			for (int i = 0; i < doc_list.getLength(); i++) {
				doc = index.createElement("doc");
				doc.setAttribute("id", id_arr[i]);
				title = index.createElement("title");
				title.appendChild(index.createTextNode(title_arr[i]));
				doc.appendChild(title);
				body = index.createElement("body");
				body.appendChild(index.createTextNode(indexbody_arr[i]));
				doc.appendChild(body);
				docs.appendChild(doc);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			DOMSource source = new DOMSource(index);
			StreamResult result = new StreamResult(new FileOutputStream(new File("index.xml")));
			
			transformer.transform(source, result);
			
			
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
