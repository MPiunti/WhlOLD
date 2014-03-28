package ckan_crawler.ckan_crawler;

import ckan_crawler.ckan_crawler.logger.Logger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Environment;


import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



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


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * Created by mPiunti
 * User: xaoc
 * Date: 23.3.14
 * Time: 17:42
 */
public class Runner implements IRunner {

	@Autowired
    private IBean1 bean1;
 	
	@Autowired Environment 
	environment; 
	
	
	/*
	 * 
	 * http://www.dati.gov.it/catalog/api/3/action/package_list

	http://www.dati.gov.it/catalog/api/3/action/group_list
	
	http://www.dati.gov.it/catalog/api/3/action/tag_list
	
	 
	 
	http://www.dati.gov.it/catalog/api/3/action/package_show?id=inps_389
	 
	http://www.dati.gov.it/catalog/api/3/action/package_show?id=regione-toscana_superfici_comunalid2f5cd03-b0f8-4411-8dd8-26a3c10492a0   tags[3]

	 */

    @Logger private Log log;

    @Override
    public void run() {
    	//log.info(" HIIIII " + this.environment.getProperty("ckan.base.url") );
        log.info(bean1.hello("CRAWLING: "));
        getCatalog();
        
    }
    
    public Map<String,String> getCatalog(){
		
    	RestTemplate restTemplate = new RestTemplate();
    	List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);		
		
		String API_URL = bean1.getBaseUrl() + bean1.getPackagelistUrl();	
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Accept","application/json ");
		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		Map<String, String> map = new HashMap<String, String>();
		try {

			ResponseEntity<HashMap> response = restTemplate.exchange(
			    API_URL,
				HttpMethod.POST,
				requestEntity,
			    HashMap.class);
			Map<String, Object> result = (Map<String, Object>)response.getBody();			
			
			//result = restTemplate.getForObject(API_URL, String.class, vars);	
			ArrayList<String> refids= (ArrayList<String>)result.get("result");
			log.info(result.get("success") + " RETRIEVED N: " + refids.toArray().length + " documents...");
			
			for (String s : refids){
			    log.info("refid: " + s);
			    response = restTemplate.exchange(
			    		"http://www.dati.gov.it/catalog/api/3/action/package_show?id="+s,
						HttpMethod.POST,
						requestEntity,
					    HashMap.class);
				result = (Map<String, Object>)response.getBody();
				LinkedHashMap<String,Object> doc = (LinkedHashMap <String,Object>)result.get("result");
				
				
				log.info(" 		DOC =  {" /*+ doc.get("id") */+"	"+ doc.get("name") + "	" +  doc.get("title") + "	" + doc.get("license_title") + "	" + doc.get("url")  +"}");
				
				LinkedHashMap <String,Object> org = (LinkedHashMap <String,Object>)doc.get("organization");
				log.info("			ORG = { " + org.get("id") +"	"+ org.get("descriotion")  + "}");
				
				for(LinkedHashMap<String, Object> tag : (ArrayList<LinkedHashMap <String,Object>>)doc.get("tags") )
					log.info("			TAG = { "+ tag.get("id") + "	"  + tag.get("name") + "	" + tag.get("display_name") + "	" + tag.get("id") + " }");		
				/*for (String field : doc.keySet() ){
					log.info("  		key-field: " + field);
				}*/
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}		
		return map;
	}

    @Autowired
    @Override
    public void setBean1(IBean1 bean1) {
    	//log.info(" HIIIII " );
        this.bean1 = bean1;
    }
}
