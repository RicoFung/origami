package chok.util.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * 扩展Cookie
 */
public class MyCookie
{
	private HttpServletResponse response;
	private HttpServletRequest request;

	/**
	 * 初始化cookie
	 * @param context PageContext
	 */
	public MyCookie(PageContext context)
	{
		this.response = (HttpServletResponse) context.getResponse();
		this.request = (HttpServletRequest) context.getRequest();
	}

	/**
	 * 初始化cookie
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	public MyCookie(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
	}

	/**
	 * 往客户端写入Cookie，当页面关闭时删除cookie，当前应用所有页面有效
	 * @param name cookie参数名
	 * @param value cookie参数值
	 */
	public void addCookie(String name, String value)
	{
		addCookie(name, value, -1, "/", null, false, false);
	}

	/**
	 * 往客户端写入Cookie，当前应用所有页面有效
	 * @param name cookie参数名
	 * @param value cookie参数值
	 * @param maxAge 有效时间，int(单位秒)，0:删除Cookie，-1:页面关闭时删除cookie
	 */
	public void addCookie(String name, String value, int maxAge)
	{
		addCookie(name, value, maxAge, "/", null, false, false);
	}

	/**
	 * 往客户端写入Cookie
	 * @param name cookie参数名
	 * @param value cookie参数值
	 * @param maxAge 有效时间，int(单位秒)，0:删除Cookie，-1:页面关闭时删除cookie
	 * @param path 与cookie一起传输的虚拟路径，即有效范围
	 */
	public void addCookie(String name, String value, int maxAge, String path)
	{
		addCookie(name, value, maxAge, path, null, false, false);
	}

	/**
	 * 往客户端写入Cookie
	 * @param name cookie参数名
	 * @param value cookie参数值
	 * @param maxAge 有效时间，int(单位秒)，0:删除Cookie，-1:页面关闭时删除cookie
	 * @param path 与cookie一起传输的虚拟路径
	 * @param domain 与cookie关联的域
	 */
	public void addCookie(String name, String value, int maxAge, String path, String domain)
	{
		addCookie(name, value, maxAge, path, domain, false, false);
	}

	/**
	 * 往客户端写入Cookie
	 * @param name cookie参数名
	 * @param value cookie参数值
	 * @param maxAge 有效时间，int(单位秒)，0:删除Cookie，-1:页面关闭时删除cookie
	 * @param path 与cookie一起传输的虚拟路径
	 * @param domain 与cookie关联的域
	 * @param isSecure 是否在https请求时才进行传输
	 * @param isHttpOnly 是否只能通过http访问
	 */
	public void addCookie(String name, String value, int maxAge, String path, String domain, boolean isSecure, boolean isHttpOnly)
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		if(maxAge > 0 && domain != null)
		{
			cookie.setDomain(domain);
		}
		cookie.setSecure(isSecure);
		try
		{
			Cookie.class.getMethod("setHttpOnly", boolean.class);
			cookie.setHttpOnly(isHttpOnly);
		}
		catch(Exception e)
		{
			System.out.println("MyCookie ignore setHttpOnly Method");
		}
		response.addCookie(cookie);
	}

	/**
	 * 删除cookie
	 * @param name cookie参数名
	 */
	public void delCookie(String name)
	{
//		Cookie cookies[] = request.getCookies();
//		if(cookies != null)
//		{
//			Cookie cookie = null;
//			for(int i = 0; i < cookies.length; i++)
//			{
//				cookie = cookies[i];
//				if(cookie.getName().equals(name))
//				{
//					addCookie(name, "", 0, cookie.getPath(), cookie.getDomain());// 按原cookie属性删除
//				}
//			}
//		}
		addCookie(name, "", 0, "/", null);
	}

	/**
	 * 根据cookie名称取得参数值
	 * @param name cookie参数名
	 * @return 存在返回String，不存在返回null
	 */
	public String getValue(String name)
	{
		Cookie cookies[] = request.getCookies();
		String value = null;
		if(cookies != null)
		{
			Cookie cookie = null;
			for(int i = 0; i < cookies.length; i++)
			{
				cookie = cookies[i];
				if(cookie.getName().equals(name))
				{
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}

	/**
	 * 根据Cookie参数名判断Cookie是否存在该值
	 * @param name cookie参数名
	 * @return 存在返回true，不存在返回false
	 */
	public boolean isExist(String name)
	{
		Cookie cookies[] = request.getCookies();
		if(cookies == null)
		{
			return false;
		}
		boolean isExist = false;
		for(int i = 0; i < cookies.length; i++)
		{
			if(cookies[i].getName().equals(name))
			{
				isExist = true;
				break;
			}
		}
		return isExist;
	}
}
