package ckan_crawler.ckan_crawler;

public interface IBean1 {
    String hello(String name);
    void setBaseUrl(String var1);
    
    public String getBaseUrl() ;
	public String getPackagelistUrl();
	public String getGrouplistUrl();
	public String getTaglistUrl();
	public String getPackageshowUrl();
    
    public void setVar1(String url);
	public void setPackagelistUrl(String url);
    public void setGrouplistUrl(String url);
    public void setTaglistUrl(String url);
    public void setPackageshowUrl(String url);
}
