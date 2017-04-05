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

public class HttpUtil
{
	public static <T> HttpResult<T> create(HttpAction actionObj, Class<T> clazz, String method)
	{
//		Log.i("<* Request URL *>", actionObj.getUrl());
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
//				Log.i("<* Request METHOD *>", "POST");
				//初始化HttpPost
				HttpPost req = new HttpPost(actionObj.getUrl());
				req.setEntity(new UrlEncodedFormEntity(actionObj.getParams(), "UTF-8"));
//				req.setEntity(new UrlEncodedFormEntity(actionObj.getParams(), HTTP.UTF_8));
				//执行请求
				resp = httpclient.execute(req);
			}
			else if(method.equals("GET"))
			{
//				Log.i("<* Request METHOD *>", "GET");
				String url = actionObj.getUrl();
				//初始化HttpGet
				for (int i=0; i<actionObj.getParams().size(); i++)
				{
					if (i==0)
						url += "?" + actionObj.getParams().get(i).getName() + "=" + actionObj.getParams().get(i).getValue();
					else
						url += "&" + actionObj.getParams().get(i).getName() + "=" + actionObj.getParams().get(i).getValue();
				}
//				Log.i("<* Request 'GET' URL *>", url);
				HttpGet req = new HttpGet(url);
				//执行请求
				resp = httpclient.execute(req);
			}
			else
			{
				o.setSuc(false);
				o.setErrMsg("请求失败：请正确填写 GET / POST");
//				Log.i("<* 请求失败：*>", "请正确填写 GET / POST");
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