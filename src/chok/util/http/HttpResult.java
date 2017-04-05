package chok.util.http;

public class HttpResult<T>
{
	private boolean suc;
	private String sucMsg;
	private String errMsg;
	private T data;
	private long contentLength;
//	private Map<String,String> dataMap = new HashMap<String, String>();
	
	public boolean isSuc()
	{
		return suc;
	}
	public void setSuc(boolean suc)
	{
		this.suc = suc;
	}
	public String getSucMsg()
	{
		return sucMsg;
	}
	public void setSucMsg(String sucMsg)
	{
		this.sucMsg = sucMsg;
	}
	public String getErrMsg()
	{
		return errMsg;
	}
	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}
	public T getData()
	{
		return data;
	}
	public void setData(T data)
	{
		this.data = data;
	}
	public long getContentLength() {return contentLength;}
	public void setContentLength(long contentLength) {this.contentLength = contentLength;}
	//	public Map<String, String> getDataMap()
//	{
//		return dataMap;
//	}
//	public void setDataMap(String k, String v)
//	{
//		this.dataMap.put(k, v);
//	}
	
}