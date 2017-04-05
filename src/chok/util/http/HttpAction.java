package chok.util.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpAction 
{
	private String url;
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	private Map<String, String> parmasMap = new HashMap<String, String>();
	
	public HttpAction(String url, List<NameValuePair> params) {
		super();
		this.url = url;
		if(params != null)
		{
			this.params = params;
		}
	}
	public HttpAction(String url, Map<String, String> m) {
		super();
		this.url = url;
		this.parmasMap = m;
		this.params.clear();
		for (Map.Entry<String, String> e : m.entrySet())
		{
			NameValuePair param = new BasicNameValuePair(e.getKey(), e.getValue());
			this.params.add(param);
		}
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<NameValuePair> getParams() {
		return params;
	}
	public void setParams(List<NameValuePair> params) {
		this.params = params;
	}
	public void putParam(NameValuePair param) {
		this.params.add(param);
	}
	public Map<String, String> getParamsMap(){
		return this.parmasMap;
	}
}
