package openS0311;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			File dir = new File("2nd html");
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

			Document xml = docBuilder.newDocument();
			xml.setXmlStandalone(true);
			Element docs = xml.createElement("docs");
			xml.appendChild(docs);

			Element doc;
			Element title;
			Element body;
			for (int i = 0; i < files.length; i++) {
				doc = xml.createElement("doc");
				doc.setAttribute("id", Integer.toString(i));
				title = xml.createElement("title");
				title.appendChild(xml.createTextNode(titleJ[i]));
				doc.appendChild(title);
				body = xml.createElement("body");
				body.appendChild(xml.createTextNode(bodyJ[i]));
				doc.appendChild(body);
				docs.appendChild(doc);
			}
			
			

			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			DOMSource source = new DOMSource(xml);
			StreamResult result = new StreamResult(new FileOutputStream(new File("collection.xml")));
			
			transformer.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
