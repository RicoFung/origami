package chok.cas.client.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chok.cas.client.CasLoginUser;

public class CasPasswordFilter implements Filter
{
	static Logger log = LoggerFactory.getLogger("cas.password");
	public static String passwordURL = "";// 登出页面
	
	@Override
	public void init(FilterConfig config) throws ServletException 
	{
		passwordURL = String.valueOf(config.getInitParameter("passwordURL")).trim();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String relativeURI = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
		CasLoginUser user = (CasLoginUser)req.getSession().getAttribute(CasAccessFilter.LOGINER);
		String account = user.getString("tc_code");
		try
		{
			resp.sendRedirect(passwordURL+"?account="+account+"&service="+URLEncoder.encode(relativeURI,"utf-8"));
			return;
		}
		catch(Exception ex)
		{
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Override
	public void destroy() 
	{
	}
}
