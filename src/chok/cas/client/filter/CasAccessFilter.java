package chok.cas.client.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import chok.cas.client.CasLoginUser;
import chok.devwork.Result;
import chok.util.http.HttpAction;
import chok.util.http.HttpResult;
import chok.util.http.HttpUtil;

public class CasAccessFilter implements Filter 
{
	static Logger log = LoggerFactory.getLogger("CasAccessFilter.class");
	
	private static String apiURL = "";// 接口地址
	private static String appId = "";// SSO client端ID
	private static Set<String> ignoreURLSet = new HashSet<String>();// 无需验证页面
	private static String isNeedChkAct = "";// 是否需要验证action权限
	
	private final static String MENU_JSON = "menuJson";// 
	public final static String BTN_JSON = "btnJson";// 
	public final static String ACT_JSON = "actJson";// 
	public final static String LOGINER = "loginer";// sessionUser在session中的key
	
	@Override
	public void init(FilterConfig config) throws ServletException 
	{
		try
		{
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
			CasLoginUser u = checkUserSession(req, session);
			if(u == null) return;
			if (!u.getM().containsKey(MENU_JSON) || u.getString(MENU_JSON)==null) u.set(MENU_JSON, getMenuJson(u));
			if(log.isInfoEnabled()) log.info("["+u.getString("tc_code")+"'s menuJson] = " + u.getString(MENU_JSON));
			if (!u.getM().containsKey(BTN_JSON) || u.getString(BTN_JSON)==null) u.set(BTN_JSON, getBtnJson(u));
			if(log.isInfoEnabled()) log.info("["+u.getString("tc_code")+"'s btnJson] = " + u.getString(BTN_JSON));
			if (!u.getM().containsKey(ACT_JSON) || u.getString(ACT_JSON)==null) u.set(ACT_JSON, getActJson(u));
			if(log.isInfoEnabled()) log.info("["+u.getString("tc_code")+"'s actJson] = " + u.getString(ACT_JSON));
			
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
	
	public String getMenuJson(CasLoginUser u)
	{
		Map<String, String> param = new HashMap<String, String>();
		param.put("tc_user_id", u.getString("id"));
		param.put("tc_app_id", appId);
		HttpResult<String> r = HttpUtil.create(new HttpAction(apiURL+"/getMenuByUserId.action", param), String.class, "GET");
		if(log.isInfoEnabled()) log.info("[MENU_JSON] <<< " + r.getData());
		return r.getData();
	}
	
	public String getBtnJson(CasLoginUser u)
	{
		Map<String, String> param = new HashMap<String, String>();
		param.put("tc_user_id", u.getString("id"));
		param.put("tc_app_id", appId);
		HttpResult<String> r = HttpUtil.create(new HttpAction(apiURL+"/getBtnByUserId.action", param), String.class, "GET");
		if(log.isInfoEnabled()) log.info("[BTN_JSON] <<< " + r.getData());
		return r.getData();
	}
	
	public String getActJson(CasLoginUser u)
	{
		Map<String, String> param = new HashMap<String, String>();
		param.put("tc_user_id", u.getString("id"));
		param.put("tc_app_id", appId);
		HttpResult<String> r = HttpUtil.create(new HttpAction(apiURL+"/getActByUserId.action", param), String.class, "GET");
		if(log.isInfoEnabled()) log.info("[ACT_JSON] <<< " + r.getData());
		return r.getData();
	}
	
	/**
	 * 验证action访问权限
	 * @param actionURL
	 * @param u 当前会话用户对象
	 * @return true 有权限，false 无权限
	 */
	public boolean isAccess(String actionURL, CasLoginUser u)
	{
		boolean tag = false;
		List<Map<String, Object>> actList=JSON.parseObject(u.get(ACT_JSON).toString(), new TypeReference<List<Map<String,Object>>>(){});
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
	
	/**
	 * 验证登录用户session
	 * @param request
	 * @param session
	 * @return CasLoginUser
	 * @throws Exception
	 */
	private static CasLoginUser checkUserSession (HttpServletRequest request, HttpSession session)
	{
		CasLoginUser u = (CasLoginUser)session.getAttribute(LOGINER);
		if (u == null)
		{
			if (request.getUserPrincipal() != null)
			{
		        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		        final Map<String, Object> attributes = principal.getAttributes();
		        if (attributes != null)
		        {
		            Iterator<String> attributeNames = attributes.keySet().iterator();
		            if (attributeNames.hasNext())
		            {
		            	u = new CasLoginUser();
		            	u.setId(Long.valueOf(attributes.get("id").toString()));
		            	u.setM((HashMap<String, Object>) attributes);
		    			u.set("appId", appId);
		    			session.setAttribute(LOGINER, u);
		            } 
		            else
		            {
		            	try
		            	{
							throw new Exception("No attributes are supplied by the CAS server.");
						} 
		            	catch (Exception e) 
		            	{
							e.printStackTrace();
						}
		            }
		        } 
		        else
		        {
		        	try 
		        	{
						throw new Exception("The attribute map is empty. Review your CAS filter configurations.");
					}
		        	catch (Exception e) 
		        	{
						e.printStackTrace();
					}
		        }
		    } 
			else 
			{
				try 
				{
					throw new Exception("The user principal is empty from the request object. Review the wrapper filter configuration.");
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
		    }
		}
		return u;
	}
}
