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
	
	public HashMap<String,String>  geoCode(String key){
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("queryStr", key);
		String result = restTemplate.getForObject("http://maps.googleapis.com/maps/api/geocode/xml?address={queryStr}&sensor=false", 
				String.class, vars);
		return getLatLon(result);
	}
	
	public HashMap<String,String> getLatLon(String strstr){
		HashMap<String,String> coordinates = new HashMap<String,String>();
		try{		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(strstr)));
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();
			XPathExpression expr = xpath.compile("//geometry/location/lat");
			NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				String lat_val = nodes.item(i).getTextContent();
			    System.out.println("."+i+"++++Retrieved lat:" + lat_val); 
			    coordinates.put("LAT", lat_val);
			}
			expr = xpath.compile("//geometry/location/lng");
			nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				String lon_val = nodes.item(i).getTextContent();
			    System.out.println("."+i+"++++Retrieved lon:" + lon_val); 
			    coordinates.put("LON", lon_val);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return coordinates;
	}

}