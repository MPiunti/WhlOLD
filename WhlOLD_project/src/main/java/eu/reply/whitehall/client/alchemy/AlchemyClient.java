package eu.reply.whitehall.client.alchemy;



import java.io.StringReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class AlchemyClient {
	
	@Autowired
    private RestTemplate    restTemplate;	

	private static final String API_KEY="04effd1aee91316ed0ff9e074de2c3329bfd3db4";
	private static final String API_ENTITIES_WEB = "http://access.alchemyapi.com/calls/url/URLGetRankedNamedEntities";
	private static final String API_ENTITIES_TEXT = "http://access.alchemyapi.com/calls/text/TextGetRankedNamedEntities";
	//private static final String API_URL_PREFIX = "http://lookup.dbpedia.org/api/search.asmx/PrefixSearch?QueryClass=&MaxHits=1&QueryString={queryStr}";
	
	public static void main(String [ ] args){
		AlchemyClient alchemy = new AlchemyClient();		
		alchemy.linkAlchemyText("LeBron%20James");
	}
	
	
	public Map<String,String> linkAlchemyText(String key){
		
		restTemplate = new RestTemplate();
		
		String API_URL = API_ENTITIES_TEXT + "?apikey="+API_KEY+"&text=";			
		String queryStr="",result="";
		Map<String, String> map = new HashMap<String, String>();
		try {
			queryStr = //key.replace(" ", "%20");
					URLEncoder.encode(key, "UTF-8");	
			API_URL += queryStr;
			System.out.println(" queryStr:" + queryStr);
			map.put("queryStr", queryStr);
			//Accept-Encoding: gzip,deflate,sdch		
			HttpHeaders requestHeaders = new HttpHeaders();
			//requestHeaders.add("Accept-Encoding", "gzip,deflate,sdch");
			/*requestHeaders.add("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.6,en;q=0.4");
			requestHeaders.add("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
			requestHeaders.add("Content-Type","text/plain; charset=utf-8"); 
			requestHeaders.add("Accept","* /* "); */
			requestHeaders.add("Content-Length", Integer.toString(API_URL.length()));
			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
			ResponseEntity<String> response = restTemplate.exchange(
			    API_URL,
				HttpMethod.POST,
			    requestEntity,
			    String.class);
			result = response.getBody().toString();			
			
			//result = restTemplate.getForObject(API_URL, String.class, vars);	
			System.out.println(" queryStr:" + queryStr + " RESULT: " + result);
			map = getAlchemyDisambiguation(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return map;
	}
	
	/**
	 * Parse XML result 
	 * @param strstr
	 * @return
	 */
	public HashMap<String,String> getAlchemyDisambiguation(String XMLstr){
		HashMap<String,String> ALCHEMY_URIs = new HashMap<String,String>();
		String url_link, url_type;
		try{		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    //factory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(XMLstr)));
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();
			
			XPathExpression expr = xpath.compile("results/entities/entity/disambiguated/*");
			NodeList db_links= (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < db_links.getLength(); i++) {
				url_link = db_links.item(i).getTextContent();
				url_type = db_links.item(i).getNodeName();
			    System.out.println("."+i+"++++Retrieved ALCHEMY LINK:" + url_type +" url: "+ url_link ); 
			    ALCHEMY_URIs.put(url_type, url_link );
			}
			
			expr = xpath.compile("results/entities/entity/type");
			db_links= (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < db_links.getLength(); i++) {
				url_type = db_links.item(i).getTextContent();
			    System.out.println("."+i+"++++Retrieved ALCHEMY TYPE:" + url_type  ); 
			    ALCHEMY_URIs.put("DESCR", url_type );
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ALCHEMY_URIs;
	}


}

