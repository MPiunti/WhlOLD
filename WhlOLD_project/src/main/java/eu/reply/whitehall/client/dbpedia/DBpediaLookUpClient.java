package eu.reply.whitehall.client.dbpedia;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;


public class DBpediaLookUpClient {
	
	@Autowired
    private RestTemplate    restTemplate;
	
	public String linkDbPedia(String key){
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("queryStr", key);
		String result = restTemplate.getForObject("http://lookup.dbpedia.org/api/search.asmx/KeywordSearch?QueryString={queryStr}&MaxHits=1", String.class, vars);
		                          //"http://lookup.dbpedia.org/api/search.asmx/PrefixSearch?QueryClass=&MaxHits=5&QueryString={queryStr}&MaxHits=1"
		                                          //"http://lookup.dbpedia.org/api/search.asmx/PrefixSearch?QueryClass=&MaxHits=5&QueryString={queryStr}&MaxHits=1"
		return result;
	}

}
