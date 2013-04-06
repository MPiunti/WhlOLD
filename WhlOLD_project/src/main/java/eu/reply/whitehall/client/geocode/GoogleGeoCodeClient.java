package eu.reply.whitehall.client.geocode;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import org.xml.sax.InputSource;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;


public class GoogleGeoCodeClient {
	
	@Autowired
    private RestTemplate    restTemplate;
	
	public String geoCode(String key){
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("queryStr", key);
		String result = restTemplate.getForObject("http://maps.googleapis.com/maps/api/geocode/xml?address={queryStr}&sensor=false", 
				String.class, vars);
		getLatLong(result);
		return result;
	}
	
	
	public void getLatLong(String strstr){
		try{		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(strstr)));
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();
			XPathExpression expr = xpath.compile("geometry");
			NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
			    System.out.println(nodes.item(i).getNodeValue()); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}