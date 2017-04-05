package chok.devwork;

import java.util.HashMap;
import java.util.Map;

public class Result 
{
	private boolean success;
	private String msg;
	private Map<Object,Object> data = new HashMap<Object,Object>();
	
	public boolean isSuccess()
	{
		return success;
	}
	public void setSuccess(boolean success) 
	{
		this.success = success;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public Map<Object, Object> getData()
	{
		return data;
	}
	public void setData(Map<Object, Object> data) 
	{
		this.data = data;
	}
	public void put(Object k, Object v)
	{
		this.data.put(k, v);
	}
}
