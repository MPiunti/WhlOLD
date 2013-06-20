package eu.reply.whitehall.client.deezer;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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


public class DeezerClient {
	
	@Autowired
    private RestTemplate    restTemplate;	

	private static final String API_URL="http://api.deezer.com/2.0/search?q={queryStr}";
	
	public static void main(String [ ] args){
		DeezerClient deezer = new DeezerClient();		
		deezer.linkDeezer("Get+Lucky");
	}
	
	
	public HashMap<String,String> linkDeezer(String key){

		restTemplate = new RestTemplate();
		String queryStr="",result="";
		Map<String, String> vars = new HashMap<String, String>();
		try {
			queryStr = key.replace(" ", "%20");
					//URLEncoder.encode(key, "UTF-8");
			System.out.println(" queryStr:" + queryStr);
			vars.put("queryStr", queryStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		result = restTemplate.getForObject(API_URL, String.class, vars);	
		System.out.println(" queryStr:" + queryStr + " RESULT: " + result);
		return getDeezerTrack(result);
	}
	
	/**
	 * Parse JSON result 
	 * @param jstr
	 * @return
	 */
	public HashMap<String,String> getDeezerTrack(String JsonStr){
		HashMap<String,String> Deezers = new HashMap<String,String>();
		 ObjectMapper mapper = new ObjectMapper();
		try {			
			 // (note: can also use more specific type, like ArrayNode or ObjectNode!)
			 JsonNode rootNode = mapper.readValue(JsonStr, JsonNode.class); // src can be a File, URL, InputStream etc
			 JsonNode result = rootNode.get("data"); 
			 String title = result.get(0).get("title").getTextValue();
			 String artist = result.get(0).get("artist").get("name").getTextValue();
			 String  album = result.get(0).get("album").get("title").getTextValue();
			 String  mp3 = result.get(0).get("preview").getTextValue();
			 Deezers.put("TITLE", title );
			 Deezers.put("ARTIST", artist );
			 Deezers.put("ALBUM", album);
			 Deezers.put("MP3", mp3);
			 
			 System.out.println("°° Retrieved Deezer Entry: " +artist+ " --" +album+ "-- mp3:" + mp3 ); 
			 
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // or URL, Stream, Reader, String, byte[]
		
		return Deezers;

	}


}

