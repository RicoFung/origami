package chok.sso.filter;

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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutFilter implements Filter
{
	static Logger log = LoggerFactory.getLogger("sso.logout");

	@Override
	public void init(FilterConfig config) throws ServletException 
	{
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		System.out.println("登出 filter...");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String path = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
		try
		{
			HttpSession session = req.getSession();
			session.removeAttribute(LoginFilter.LOGINER);
			session.removeAttribute(LoginFilter.TICKET);
			session.invalidate();

			resp.setHeader("P3P", "CP=CAO PSA OUR");
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.sendRedirect(LoginFilter.logoutURL+"?service="+path);
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
