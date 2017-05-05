package chok.sso.filter;

import java.io.IOException;

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

public class PasswordFilter implements Filter
{
	static Logger log = LoggerFactory.getLogger("sso.password");

	@Override
	public void init(FilterConfig config) throws ServletException 
	{
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		System.out.println("修改密码 filter...");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String path = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
		try
		{
			resp.setHeader("P3P", "CP=CAO PSA OUR");
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.sendRedirect(LoginFilter.getPasswordURL(path));
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
