package eu.reply.whitehall.client.geocode;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
	
	private final String GOOGLEAPI_MAPS = "http://maps.googleapis.com/maps/api/geocode/xml?address={queryStr}&sensor=false";
	
	public HashMap<String,Float>  geoCode(String key){
		Map<String, String> vars = new HashMap<String, String>();
		String queryStr,result=null;
		try {
			queryStr = URLEncoder.encode(key, "UTF-8");
			vars.put("queryStr", queryStr);
			result = restTemplate.getForObject(GOOGLEAPI_MAPS, 
					String.class, vars);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		return getLatLon(result);
	}
	
	public HashMap<String,Float> getLatLon(String strstr){
		HashMap<String,Float> coordinates = new HashMap<String,Float>();
		float lat = Float.NaN;
		float lng = Float.NaN;
		
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
				lat = Float.parseFloat(lat_val);
			    System.out.println("."+i+"++++Retrieved lat:" + lat_val); 
			    coordinates.put("LAT", lat);
			}
			expr = xpath.compile("//geometry/location/lng");
			nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				String lon_val = nodes.item(i).getTextContent();
				lng = Float.parseFloat(lon_val);
			    System.out.println("."+i+"++++Retrieved lon:" + lon_val); 
			    coordinates.put("LON", lng);
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return coordinates;
	}

}