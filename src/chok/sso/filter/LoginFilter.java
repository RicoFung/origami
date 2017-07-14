package chok.sso.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import chok.devwork.Result;
import chok.sso.AuthUser;
import chok.util.http.HttpAction;
import chok.util.http.HttpResult;
import chok.util.http.HttpUtil;

public class LoginFilter implements Filter
{
	static Logger log = LoggerFactory.getLogger("sso.login");
	
	public static String ssoURL = "";// SSO项目根地址
	public static String ssoAuthURL = "";// SSO认证地址
	public static String loginURL = "";// 登录页面
	public static String logoutURL = "";// 登出页面
	public static String passwordURL = "";// 修改密码页面
	public final static String TICKET = "ticket";// url中传来的sessionKey的变量名
	public final static String LOGINER = "sso.loginer";// sessionUser在session中的key
	
	@Override
	public void init(FilterConfig config) throws ServletException 
	{
		try
		{
			ssoURL = String.valueOf(config.getInitParameter("ssoURL")).trim();
			ssoAuthURL = String.valueOf(config.getInitParameter("ssoAuthURL")).trim();
			loginURL = ssoAuthURL + "/login.action";
			logoutURL = ssoAuthURL + "/logout.action";
			passwordURL = ssoAuthURL + "/password.action";
		}
		catch(Exception e)
		{
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String relativeURI = req.getServletPath();// 相对地址
//		String relativeURI = request.getRequestURI();// request.getRequestURI()=request.getContextPath()+request.getServletPath()
		try
		{
			AuthUser u = (AuthUser)session.getAttribute(LOGINER);

			if(log.isInfoEnabled())
			{
				log.info("当前访问地址：" + req.getContextPath() + relativeURI);
				log.info(u != null ? "当前登录用户是：" + u.getString("tc_code") : "当前没有登录用户");
			}
			// 判断是否有ticket
			String ticket = request.getParameter(TICKET);
			if(ticket == null)// 如果不经由ticket的链接过来，则进此处判断
			{
				if(log.isInfoEnabled())
				{
					log.info("request中的ticket为空");
				}
				if(u != null)// 已登录
				{
					chain.doFilter(request, response);
					return;
				}
			}
			else
			// ticket非空,由链接传来ticket
			{
				if(ticket.equals(String.valueOf(session.getAttribute(TICKET))))// 一样的ticket
				{
					if(session.getAttribute(LOGINER) != null)// 已登录
					{
						chain.doFilter(request, response);
						return;
					}
				}
				else
				{
					if(log.isInfoEnabled())
					{
						log.info("session和request中的ticket不相等");
						if("null".equals(String.valueOf(session.getAttribute(TICKET))))
						{
							log.info("session中的ticket为空");
						}
					}
				}
				if(doValidate(session, ticket))
				{
					chain.doFilter(request, response);
					return;// 验证成功
				}
			}

			if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equals("XMLHttpRequest")) 
			{ // ajax请求
				resp.setContentType("text/html;charset=UTF-8");
				resp.setContentType("application/json");
				Result result = new Result();
				result.setSuccess(false);
				result.setMsg("操作失败，会话超时，请重新登录！");
				resp.getWriter().print(JSON.toJSONString(result));
			}
			else
			{ // 非ajax请求
				resp.setHeader("P3P", "CP=CAO PSA OUR");
				resp.setHeader("Access-Control-Allow-Origin", "*");
				resp.sendRedirect(getLoginURL(req));
			}
			return;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() 
	{
	}
	
	private boolean doValidate(HttpSession session, String ticket)
	{
		try
		{
			// 通过 http 带上ticket 获取用户对象
			Map<String, String> m = new HashMap<String, String>();
			m.put("ticket", ticket);
			HttpResult<String> r = HttpUtil.create(new HttpAction(ssoAuthURL+"/getLoginer.action", m), String.class, "GET");
			AuthUser u = JSON.parseObject(r.getData(), AuthUser.class);
			
			// 验证用户对象
			if(u != null && u.getString("tc_code").length() > 0 && !"null".equals(u.getString("tc_code")))
			{
				session.setAttribute(LOGINER, u);
				session.setAttribute(TICKET, ticket);
				if(log.isDebugEnabled())
				{
					log.debug("account=" + u.getString("tc_code") + ", ticket=" + ticket);
				}
				return true;
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		session.removeAttribute(LOGINER);
		session.removeAttribute(TICKET);
		return false;
	}
	
	/**
	 * 获取统一登录URL
	 * @param req HttpServletRequest
	 * @return URL String
	 */
	private String getLoginURL(HttpServletRequest req)
	{
		String url = "";
		String queryString = "";
		queryString = req.getQueryString()==null?"":"?"+req.getQueryString();
		if(log.isInfoEnabled()) log.info("step1 : "+queryString);
		queryString = queryString.indexOf("ticket=")==-1?queryString:queryString.substring(0,queryString.indexOf("ticket=")-1);
		if(log.isInfoEnabled()) log.info("step2 : "+queryString);
		try {
			url = loginURL+"?service="+URLEncoder.encode(req.getRequestURL().toString()+queryString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			url = "";
		}
		if(log.isInfoEnabled()) log.info("step3 : "+url);
		return url;
	}
	
	public static String getPasswordURL(String url)
	{
		if(url != null && url.length() > 0)
		{
			try
			{
				return passwordURL + "?service=" + URLEncoder.encode(url, "UTF-8");
			}
			catch(Exception e)
			{
			}
		}
		return passwordURL;
	}
}
