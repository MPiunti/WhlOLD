package eu.reply.whitehall.client.geocode;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;


public class GoogleGeoCodeClient {
	
	@Autowired
    private RestTemplate    restTemplate;
	
	public String geoCode(String key){
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("queryStr", key);
		String result = restTemplate.getForObject("http://maps.googleapis.com/maps/api/geocode/xml?address={queryStr}&sensor=false", 
				String.class, vars);
		return result;
	}

}