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
import java.util.LinkedList;
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
	
	LinkedHashMap<String, LinkedList<String>> docNodes;
	LinkedHashMap<String, LinkedList<String>> tagNodes;
	LinkedHashMap<String, LinkedList<String>> licenseNodes;
	LinkedHashMap<String, LinkedList<String>> orgNodes;
	LinkedHashMap<String, LinkedList<String>> relationships;
	
	
	
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
        initMaps();
        getCatalog();
        
    }
    
    public void initMaps(){ 	
    	this.docNodes = new LinkedHashMap<String, LinkedList<String>>();
    	this.tagNodes = new LinkedHashMap<String, LinkedList<String>>();
    	this.licenseNodes = new LinkedHashMap<String, LinkedList<String>>();
    	this.orgNodes = new LinkedHashMap<String, LinkedList<String>>();
    	this.relationships = new LinkedHashMap<String, LinkedList<String>>();
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
			
			int i = 0, tot=refids.size();
			for (String s : refids){			
			    log.info("refid ( " + i++ + "/" + tot +"): " + s);
			    response = restTemplate.exchange(
			    		"http://www.dati.gov.it/catalog/api/3/action/package_show?id="+s,
						HttpMethod.POST,
						requestEntity,
					    HashMap.class);
				result = (Map<String, Object>)response.getBody();
				LinkedHashMap<String,Object> doc = (LinkedHashMap <String,Object>)result.get("result");
				
				parseResult(doc);
			} // end of DOCUMENTS
			
			log.info("==================================================================");
			log.info(" Document Nodes : " + docNodes.size());
			log.info(" Organization Nodes : " + orgNodes.size());
			log.info(" Tag Nodes : " + tagNodes.size());
			log.info(" License Nodes : " + licenseNodes.size());
			log.info(" RELATIIONSHIPS : " + relationships.size());
			log.info("==================================================================");
			
			
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
    
    
    
    public void parseResult(LinkedHashMap<String,Object> doc){
    	//log.info(" 		DOC =  {" /*+ doc.get("id") */+"	"+ doc.get("name") + "	" +  doc.get("title") + "	" +  doc.get("author") +"	" +  doc.get("author_email")  + "	" + doc.get("url")  +"}");
	    	LinkedList<String> docNode = new LinkedList<String>();
    		docNode.add(doc.get("name")+""); 
	    	docNode.add(doc.get("title")+"");
	    	docNode.add(doc.get("author")+"");
	    	docNode.add(doc.get("author_email")+"");
	    	docNode.add(doc.get("url")+"") ;
	    	docNodes.put(doc.get("name")+"",docNode);
	    	
	    	
		//log.info("			LICENSE = { " +  doc.get("license_id") +"	" + doc.get("license_title") + "}" );
			LinkedList<String> licenseNode = new LinkedList<String>();
			licenseNode.add(doc.get("license_id")+""); 
			licenseNode.add(doc.get("license_title")+"");
			licenseNodes.put(doc.get("license_id")+"", licenseNode);
			LinkedList<String> relLic = new LinkedList<String>();
			relLic.add(doc.get("name")+"");
			relLic.add(doc.get("license_id")+"");
			relLic.add("LICENCE");
			relationships.put(doc.get("name")+""+doc.get("license_id"), relLic);
		
		
		LinkedHashMap <String,Object> org = (LinkedHashMap <String,Object>)doc.get("organization");
		//log.info("			ORG = { " + org.get("id") +"	"+ org.get("description")  + "}");
			LinkedList<String> orgNode = new LinkedList<String>();
			orgNode.add(org.get("id")+""); 
			orgNode.add(org.get("description")+"");
			orgNodes.put(org.get("id")+"", orgNode);
			LinkedList<String> orgRel = new LinkedList<String>();
			orgRel.add(doc.get("name")+"");
			orgRel.add(org.get("id")+"");
			orgRel.add("PUBLISHED");
			relationships.put(doc.get("name")+""+org.get("id")+"", orgRel);
			
		for(LinkedHashMap<String, Object> tag : (ArrayList<LinkedHashMap <String,Object>>)doc.get("tags") ){
			//log.info("			TAG = { "+ tag.get("id") + "	"  + tag.get("name") + "	" + tag.get("display_name") + "	" + tag.get("id") + " }");
				LinkedList<String> tagNode = new LinkedList<String>();
				tagNode.add(tag.get("id")+""); 
				tagNode.add(tag.get("name")+"");
				tagNode.add(tag.get("display_name")+"");
				tagNodes.put(tag.get("id")+"", tagNode);
				LinkedList<String> tagRel = new LinkedList<String>();
				tagRel.add(doc.get("name")+"");
				tagRel.add(tag.get("id")+"");
				tagRel.add("TAGGED");
				relationships.put(doc.get("name")+""+tag.get("id")+"", tagRel);
		}
    }
}
