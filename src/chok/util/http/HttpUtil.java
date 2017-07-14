package chok.util.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil
{
	static Logger log = LoggerFactory.getLogger("HttpUtil");
	
	public static <T> HttpResult<T> create(HttpAction actionObj, Class<T> clazz, String method)
	{
		HttpResult<T> o = new HttpResult<T>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try
		{
			//设置网络超时
//			int k = 0;
//			for (Map.Entry<String, String> entry : actionObj.getParamsMap().entrySet()) {
////				Log.i("<* Request PARAMS ["+(k++)+"] *>", entry.getKey() + " = " + entry.getValue());
//			}
			//发送请求
			CloseableHttpResponse resp = null;
			if(method.equals("POST"))
			{
				//初始化HttpPost
				HttpPost req = new HttpPost(actionObj.getUrl());
				if(log.isInfoEnabled()) log.info("['POST' URL] >>> " + actionObj.getUrl());
				req.setEntity(new UrlEncodedFormEntity(actionObj.getParams(), "UTF-8"));
				//执行请求
				resp = httpclient.execute(req);
			}
			else if(method.equals("GET"))
			{
				String url = actionObj.getUrl();
				//初始化HttpGet
				for (int i=0; i<actionObj.getParams().size(); i++)
				{
					if (i==0)
						url += "?" + actionObj.getParams().get(i).getName() + "=" + actionObj.getParams().get(i).getValue();
					else
						url += "&" + actionObj.getParams().get(i).getName() + "=" + actionObj.getParams().get(i).getValue();
				}
				if(log.isInfoEnabled()) log.info("['GET' URL] >>> " + url);
				HttpGet req = new HttpGet(url);
				//执行请求
				resp = httpclient.execute(req);
			}
			else
			{
				o.setSuc(false);
				o.setErrMsg("请求失败：请正确填写 GET / POST");
			}
			//请求响应
			if(resp.getStatusLine().getStatusCode() == 200)
			{
				if(clazz.isAssignableFrom(String.class))
				{
					o.setData((T) EntityUtils.toString(resp.getEntity(),"UTF-8"));
				}
				else if(clazz.isAssignableFrom(Map.class))
				{
					String rs = EntityUtils.toString(resp.getEntity(),"UTF-8");
					String[] rsArr = rs.split("&");
					Map<String,String> m = new HashMap<String,String>();
					for(String r : rsArr)
					{
						String[] mapArr = r.split("=");
						m.put(mapArr[0], mapArr[1]);
					}
					o.setData((T) m);
				}
				else if(clazz.isAssignableFrom(InputStream.class))
				{
					o.setData((T) resp.getEntity().getContent());
				}
				o.setContentLength(resp.getEntity().getContentLength());
				o.setSuc(true);
				o.setSucMsg("请求成功："+resp.getStatusLine().getStatusCode());
			}
			else 
			{
				o.setSuc(false);
				o.setErrMsg("请求失败："+resp.getStatusLine().getStatusCode());
			}
		}
		catch(Exception e)
		{
			o.setSuc(false);
			o.setErrMsg("请求异常："+e.getMessage());
			e.printStackTrace();
		}
		return o;
	}
}