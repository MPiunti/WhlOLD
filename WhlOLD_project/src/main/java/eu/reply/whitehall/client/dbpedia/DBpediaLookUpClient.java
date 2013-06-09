package eu.reply.whitehall.client.dbpedia;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class DBpediaLookUpClient {
	
	@Autowired
    private RestTemplate    restTemplate;	

	private static final String API_URL_KEYWORD = "http://lookup.dbpedia.org/api/search.asmx/KeywordSearch?QueryString={queryStr}&MaxHits=1";
	private static final String API_URL_PREFIX = "http://lookup.dbpedia.org/api/search.asmx/PrefixSearch?QueryClass=&MaxHits=1&QueryString={queryStr}";
	
	public HashMap<String,String> linkDbPedia(String key){
				
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("queryStr", key);
				
		String result = restTemplate.getForObject(API_URL_KEYWORD, String.class, vars);
		//String result = restTemplate.getForObject(API_URL_PREFIX, String.class, vars);
		return getDBPediaURIfromJson(result);
	}
	
	/**
	 * Using Jackson to Traverse the Json response
	 */
	public HashMap<String,String> getDBPediaURIfromJson(String JsonStr){
		
		HashMap<String,String> DBPediaURIs = new HashMap<String,String>();
		 ObjectMapper mapper = new ObjectMapper();
		try {			
			 // (note: can also use more specific type, like ArrayNode or ObjectNode!)
			 JsonNode rootNode = mapper.readValue(JsonStr, JsonNode.class); // src can be a File, URL, InputStream etc
			 JsonNode result = rootNode.get("results"); 
			 String db_link = result.get(0).get("uri").getTextValue();
			 String  db_descr = result.get(0).get("description").getTextValue();
			 String  db_label = result.get(0).get("label").getTextValue();
			 DBPediaURIs.put("URI", db_link );
			 DBPediaURIs.put("DESCR", db_descr);
			 DBPediaURIs.put("LABEL", db_descr);
			 
			  System.out.println("°° Retrieved DBPedia Entry: " +db_label+ " -- LINK:" + db_link ); 
			 
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // or URL, Stream, Reader, String, byte[]
		
		return DBPediaURIs;
		
	}
	
	
	/**
	 * Parse XML result 
	 * @param strstr
	 * @return
	 */
	public HashMap<String,String> getDBPediaURI(String XMLstr){
		HashMap<String,String> DBPediaURIs = new HashMap<String,String>();
		try{		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    factory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(XMLstr)));
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();
			
			XPathExpression expr = xpath.compile("//Result/URI");
			NodeList db_links= (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < db_links.getLength(); i++) {
				String db_link = db_links.item(i).getTextContent();
			    System.out.println("."+i+"++++Retrieved DBPedia LINK:" + db_link); 
			    DBPediaURIs.put("URI", db_link );
			}
			expr = xpath.compile("//Result/Description");
			db_links= (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < db_links.getLength(); i++) {
				String db_descr = db_links.item(i).getTextContent();
			    System.out.println("."+i+"++++Retrieved DBPedia Description:" + db_descr); 
			    DBPediaURIs.put("DESCR", db_descr );
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return DBPediaURIs;
	}


}

