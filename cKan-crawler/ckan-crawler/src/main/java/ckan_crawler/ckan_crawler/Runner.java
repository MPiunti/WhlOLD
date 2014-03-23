package ckan_crawler.ckan_crawler;

import ckan_crawler.ckan_crawler.logger.Logger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;


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
 	

    @Logger private Log log;

    @Override
    public void run() {
    	//log.info(" HIIIII " + this.environment.getProperty("ckan.base.url") );
        log.info(bean1.hello("CRAWLING: "));
        getCatalog();
        
    }
    
    public Map<String,String> getCatalog(){
		
    	RestTemplate restTemplate = new RestTemplate();
		
		String API_URL = bean1.getBaseUrl() + bean1.getPackagelistUrl();			

		Map<String, String> map = new HashMap<String, String>();
		try {

			ResponseEntity<String> response = restTemplate.exchange(
			    API_URL,
				HttpMethod.POST,
			    null,
			    String.class);
			String result = response.getBody();			
			
			//result = restTemplate.getForObject(API_URL, String.class, vars);	
			System.out.println(" RESULT: " + result);
			
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
