package eu.reply.whitehall.client.alchemy;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;


public class AlchemyClientAPI {

	
	@Autowired
    private AlchemyAPI alchemyAPI;	
	
	public static void main(String [ ] args){
		
		AlchemyClientAPI api_Cl = new AlchemyClientAPI();
		api_Cl.linkAlchemyText("Rome, Italy");
	}
	
	
	public Map<String,String> linkAlchemyText(String key) {		
		//AlchemyAPI api = new AlchemyAPI();
		Map<String,String> ALCHEMY_URIs =  new HashMap<String, String>();
		try {
			String queryStr = //key.replace(" ", "%20");
					URLEncoder.encode(key, "UTF-8");
			ALCHEMY_URIs = getAlchemyDisambiguation(alchemyAPI.TextGetRankedNamedEntities(queryStr));
		} catch (XPathExpressionException | IOException | SAXException
				| ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ALCHEMY_URIs;
	}
	
	/**
	 * Parse XML result 
	 * @param strstr
	 * @return
	 */
	public HashMap<String,String> getAlchemyDisambiguation(Document doc){
		HashMap<String,String> ALCHEMY_URIs = new HashMap<String,String>();
		String url_link, url_type;
		try{						
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

