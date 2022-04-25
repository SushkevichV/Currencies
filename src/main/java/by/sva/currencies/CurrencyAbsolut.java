package by.sva.currencies;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CurrencyAbsolut {
	
	public List<Currency> getCurrencies(String ... curNames) { // принимает любое количество аргументов
		List<Currency> currencies = new ArrayList<>();
		String fileName = "src/exchange-rates.xml";
		String urlName = "https://absolutbank.by/exchange-rates.xml";
		
		downloadUsingStream(urlName, fileName);
		
        DocumentBuilder documentBuilder;
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(fileName);
			
			for (String curName : curNames) {
				currencies.add(getCurrency(document, curName));
			}
		} catch (ParserConfigurationException | SAXException | IOException | DOMException | XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file = new File(fileName);
		file.delete();
		
		return currencies;
	}
	
	// Очень интересная библиотека для поиска определенных данных в xml-файле
	// Описание https://www.w3schools.com/xml/xpath_intro.asp
	// синтаксис https://www.w3schools.com/xml/xpath_syntax.asp
	private static Currency getCurrency(Document document, String curName) throws DOMException, XPathExpressionException {
        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        
        // значения тэга "buy" в тэге "rate" с атрибутом currency='usd' в тэге branch с атрибутом @id='1' в тэге branches и значение тэга "sell" с таким же путем
        XPathExpression expr = xpath.compile("branches/branch[@id='1']/rate[@currency='" + curName.toLowerCase() + "']/buy | branches/branch[@id='1']/rate[@currency='" + curName.toLowerCase() + "']/sell");
        NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        
        /*
        // проходит по всей коллекции
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            System.out.println("Value " + i + " : " + node.getTextContent());
        }
        */
        if (nodes.item(0).getTextContent() != null) {
        	Currency currency = new Currency(curName, Double.valueOf(nodes.item(0).getTextContent()), Double.valueOf(nodes.item(1).getTextContent()));
        	return currency;
        }
    return null;
    }

	// загружает с нета и сохраняет на диске
	private static XMLStreamReader downloadUsingStream(String urlName, String fileName){
        URL url;
		try {
			url = new URL(urlName);
			BufferedInputStream bis= new BufferedInputStream(url.openStream());
			FileOutputStream fos = new FileOutputStream(fileName);
			byte[] buffer = new byte[1024];
	        int count=0;
				while((count = bis.read(buffer,0,1024)) != -1)
				{
				    fos.write(buffer, 0, count);
				}
	        fos.close();
	        bis.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

    }

}
