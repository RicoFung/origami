package chok.cas.client.filter;

import java.io.IOException;

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

public class CasLogoutFilter implements Filter
{
	static Logger log = LoggerFactory.getLogger("cas.logout");
	public static String logoutURL = "";// 登出页面
	
	@Override
	public void init(FilterConfig config) throws ServletException 
	{
		logoutURL = String.valueOf(config.getInitParameter("logoutURL")).trim();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try
		{
			HttpSession session = req.getSession();
			session.removeAttribute(CasAccessFilter.LOGINER);
			session.invalidate();
			resp.sendRedirect(logoutURL);
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
