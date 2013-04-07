package eu.reply.whitehall.client.dbpedia;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class DBpediaLookUpClient {
	
	@Autowired
    private RestTemplate    restTemplate;
	
	public HashMap<String,String> linkDbPedia(String key){
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("queryStr", key);
		//String result = restTemplate.getForObject("http://lookup.dbpedia.org/api/search.asmx/KeywordSearch?QueryString={queryStr}&MaxHits=1", String.class, vars);
		String result = restTemplate.getForObject("http://lookup.dbpedia.org/api/search.asmx/PrefixSearch?QueryClass=&MaxHits=1&QueryString={queryStr}", String.class, vars);
		return getDBPediaURI(result);
	}
	
	public HashMap<String,String> getDBPediaURI(String strstr){
		HashMap<String,String> DBPediaURIs = new HashMap<String,String>();
		try{		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(strstr)));
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();
			XPathExpression expr = xpath.compile("//Result/URI");
			NodeList db_links= (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < db_links.getLength(); i++) {
				String db_link = db_links.item(i).getTextContent();
			    //System.out.println("."+i+"++++Retrieved DBPedia LINK:" + db_link); 
			    DBPediaURIs.put("URI", db_link );
			}
			expr = xpath.compile("//Result/Description");
			db_links= (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < db_links.getLength(); i++) {
				String db_descr = db_links.item(i).getTextContent();
			   // System.out.println("."+i+"++++Retrieved DBPedia LINK:" + db_descr); 
			    DBPediaURIs.put("DESCR", db_descr );
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return DBPediaURIs;
	}


}
