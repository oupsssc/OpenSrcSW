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

import org.jsoup.Jsoup;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class makeCollection {
	
	public makeCollection(String htmlDir) {
		
		try {
			File dir = new File(htmlDir);
			File files[] = dir.listFiles();
			org.jsoup.nodes.Document[] docsJ = new org.jsoup.nodes.Document[files.length];
			String[] titleJ = new String[files.length];
			String[] bodyJ = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				docsJ[i] = Jsoup.parse(files[i], "UTF-8");
			}
			for (int i = 0; i < files.length; i++) {
				titleJ[i] = docsJ[i].select("title").text();
				bodyJ[i] = docsJ[i].select("body").text();
				
			}

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document collection = docBuilder.newDocument();
			collection.setXmlStandalone(true);
			Element docs = collection.createElement("docs");
			collection.appendChild(docs);

			Element doc;
			Element title;
			Element body;
			for (int i = 0; i < files.length; i++) {
				doc = collection.createElement("doc");
				doc.setAttribute("id", Integer.toString(i));
				title = collection.createElement("title");
				title.appendChild(collection.createTextNode(titleJ[i]));
				doc.appendChild(title);
				body = collection.createElement("body");
				body.appendChild(collection.createTextNode(bodyJ[i]));
				doc.appendChild(body);
				docs.appendChild(doc);
			}
			
			

			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			DOMSource source = new DOMSource(collection);
			StreamResult result = new StreamResult(new FileOutputStream(new File("collection.xml")));
			
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
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
