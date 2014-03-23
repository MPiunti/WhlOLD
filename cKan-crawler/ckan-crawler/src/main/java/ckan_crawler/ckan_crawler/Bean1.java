package ckan_crawler.ckan_crawler;

import org.springframework.beans.factory.annotation.Value;

public class Bean1 implements IBean1 {

    private String baseUrl;

    @Override
    public String hello(String name) {
        return  name + baseUrl + " by mp";
    }

    @Override
    @Value("${ckan.base.url}")
    public void setBaseUrl(String url) {
        this.baseUrl = url;
    }
    
}
