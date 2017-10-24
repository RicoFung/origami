package chok.sso.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.alibaba.fastjson.TypeReference;

import chok.devwork.Result;
import chok.sso.AuthUser;
import chok.util.http.HttpAction;
import chok.util.http.HttpResult;
import chok.util.http.HttpUtil;

public class AccessFilter implements Filter 
{
	static Logger log = LoggerFactory.getLogger("AccessFilter.class");
	
//	private static String ssoURL = "";// SSO项目根地址
	private static String apiURL = "";// API接口地址
	private static String appId = "";// SSO client端ID
	private static Set<String> ignoreURLSet = new HashSet<String>();// 无需验证页面
	private static String isNeedChkAct = "";// 是否需要验证action权限
	
	private final static String menuJson = "menuJson";// 
	public final static String btnJson = "btnJson";// 
	public final static String actJson = "actJson";// 
	
	@Override
	public void init(FilterConfig config) throws ServletException 
	{
		try
		{
//			ssoURL = String.valueOf(config.getInitParameter("ssoURL")).trim();
			apiURL = String.valueOf(config.getInitParameter("apiURL")).trim();
			appId = String.valueOf(config.getInitParameter("appId")).trim();
			
			String ignoreURL = String.valueOf(config.getInitParameter("ignoreURL")).trim();
			ignoreURLSet.clear();
			splitToSet(ignoreURL, ignoreURLSet);

			isNeedChkAct = String.valueOf(config.getInitParameter("isNeedChkAct")).trim();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String relativeURI = req.getRequestURI().trim();// 相对地址
		if(req.getContextPath().length() > 0)
		{
			relativeURI = relativeURI.replaceFirst(req.getContextPath(), "");
		}
		if(log.isInfoEnabled()) log.info("[当前访问地址] = " + relativeURI);
		try
		{
			AuthUser u = (AuthUser)session.getAttribute(LoginFilter.LOGINER);
			u.set("appId", appId);
			if (!u.getM().containsKey(menuJson) || u.getString(menuJson)==null) u.set(menuJson, getMenuJson(u));
			if(log.isInfoEnabled()) log.info(u.getString("tc_code")+"'s menuJson：" + u.getString(menuJson));
			if (!u.getM().containsKey(btnJson) || u.getString(btnJson)==null) u.set(btnJson, getBtnJson(u));
			if(log.isInfoEnabled()) log.info(u.getString("tc_code")+"'s btnJson：" + u.getString(btnJson));
			if (!u.getM().containsKey(actJson) || u.getString(actJson)==null) u.set(actJson, getActJson(u));
			if(log.isInfoEnabled()) log.info(u.getString("tc_code")+"'s actJson：" + u.getString(actJson));
			
			if(isNeedChkAct.equals("1"))
			{
				if(isIgnoreURL(relativeURI))
				{
					chain.doFilter(request, response);
					return;
				}
				if(isAccess(relativeURI, u))
				{
					chain.doFilter(request, response);
					return;
				}
				// 没权限
				if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equals("XMLHttpRequest")) 
				{ // ajax请求
					resp.setContentType("text/html;charset=UTF-8"); 
					resp.setContentType("application/json");
					Result result = new Result();
					result.setSuccess(false);
					result.setMsg("操作失败，没有权限，请联系管理员！");
					resp.getWriter().print(JSON.toJSONString(result));
				}
				else
				{ // 非ajax请求
					resp.sendRedirect(req.getContextPath() + "/noaccess.jsp"); // 无权限访问，跳转页面
				}
				return;
			}
			chain.doFilter(request, response);
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
	
	public String getMenuJson(AuthUser u)
	{
		Map<String, String> param = new HashMap<String, String>();
		param.put("tc_user_id", u.getString("id"));
		param.put("tc_app_id", appId);
		HttpResult<String> r = HttpUtil.create(new HttpAction(apiURL+"/getMenuByUserId.action", param), String.class, "GET");
		if(log.isInfoEnabled()) log.info("已获取 menuJson：" + r.getData());
		return r.getData();
	}
	
	public String getBtnJson(AuthUser u)
	{
		Map<String, String> param = new HashMap<String, String>();
		param.put("tc_user_id", u.getString("id"));
		param.put("tc_app_id", appId);
		HttpResult<String> r = HttpUtil.create(new HttpAction(apiURL+"/getBtnByUserId.action", param), String.class, "GET");
		if(log.isInfoEnabled()) log.info("已获取 btnJson：" + r.getData());
		return r.getData();
	}
	
	public String getActJson(AuthUser u)
	{
		Map<String, String> param = new HashMap<String, String>();
		param.put("tc_user_id", u.getString("id"));
		param.put("tc_app_id", appId);
		HttpResult<String> r = HttpUtil.create(new HttpAction(apiURL+"/getActByUserId.action", param), String.class, "GET");
		if(log.isInfoEnabled()) log.info("已获取 actJson：" + r.getData());
		return r.getData();
	}
	
	/**
	 * 验证action访问权限
	 * @param actionURL
	 * @param u 当前会话用户对象
	 * @return true 有权限，false 无权限
	 */
	public boolean isAccess(String actionURL, AuthUser u)
	{
		boolean tag = false;
		List<Map<String, Object>> actList=JSON.parseObject(u.get(actJson).toString(), new TypeReference<List<Map<String,Object>>>(){});
		for(int i=0; i<actList.size(); i++)
		{
			if(actionURL.equals(actList.get(i).get("tc_url").toString()))
			{
				tag = true;
				break;
			}
		}
		return tag;
	}
	
	/**
	 * 判断是否为不处理的页面
	 * @param uri 本地相对地址
	 * @return true 不用进行权限判断，false 要进行权限判断
	 */
	private static boolean isIgnoreURL(String url)
	{
		if(ignoreURLSet.contains(url)) return true;
		return false;
	}
	
	private static void splitToSet(String str, Set<String> set)
	{
		if(str != null && str.length() > 0)
		{
			String[] values = str.trim().split(",");
			for(String value : values)
			{
				if(value.length() > 0)
				{
					set.add(value.trim());
				}
			}
		}
	}
}
