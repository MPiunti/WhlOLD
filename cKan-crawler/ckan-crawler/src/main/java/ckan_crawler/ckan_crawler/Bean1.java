package ckan_crawler.ckan_crawler;

import org.springframework.beans.factory.annotation.Value;

public class Bean1 implements IBean1 {

    private String baseUrl, packagelistUrl, grouplistUrl, taglistUrl;

	@Override
    public String hello(String name) {
        return  name + baseUrl + " by mp";
    }

    @Override
    @Value("${ckan.base.url}")
    public void setBaseUrl(String url) {
        this.baseUrl = url;
    }

    @Override
    @Value("${ckan.packagelist.url}")
    public void setPackagelistUrl(String url) {
        this.packagelistUrl = url;
    }    

    @Override
    @Value("${ckan.grouplist.url}")
	public void setGrouplistUrl(String url) {
        this.grouplistUrl = url;
    }

    @Override
    @Value("${ckan.taglist.url")
    public void setTaglistUrl(String url) {
        this.taglistUrl = url;
    }
    
    @Override
    public String getBaseUrl() {
		return baseUrl;
	}

    @Override
	public String getPackagelistUrl() {
		return packagelistUrl;
	}

    @Override
	public String getGrouplistUrl() {
		return grouplistUrl;
	}

    @Override
	public String getTaglistUrl() {
		return taglistUrl;
	}
    
}
