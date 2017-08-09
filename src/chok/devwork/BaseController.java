package chok.devwork;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;

import chok.util.POIUtil;
import chok.util.TimeUtil;

public class BaseController<T>
{
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected MyReq req;
	protected Result result;
	private PrintWriter out;
	protected static Logger log = LoggerFactory.getLogger(BaseController.class.getName());
	
	@ModelAttribute
	public void BaseInitialization(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
		this.response.setCharacterEncoding("UTF-8");
		this.session = request.getSession();
		this.req = new MyReq(request);
		this.result = new Result();
	}
	
	public void put(String key, Object value)
	{
		request.setAttribute(key, value);
	}
	
	public void print(Object o)
	{
		try
		{
			if(out == null)
			{
				out = this.response.getWriter();
			}
			out.print(o);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void printJson(Object o)
	{
		response.setContentType("application/json");
		try
		{
			if(out == null)
			{
				out = this.response.getWriter();
			}
			out.print(JSON.toJSONString(o));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void exp(List<T> list, String exportType)
	{
		ByteArrayOutputStream ba = null;
		ServletOutputStream out = null;
		try
		{
			try 
			{
				ba = new ByteArrayOutputStream();
				ba = (ByteArrayOutputStream) POIUtil.writeExcel(ba, 
																req.getParameter("fileName"), 
																req.getParameter("title"), 
																req.getParameter("headerNames"), 
																req.getParameter("dataColumns"), 
																list);
				
				response.reset();// 清空输出流
				response.setHeader("Content-disposition", "attachment; filename="
														+ req.getParameter("fileName")
														+ "_"
														+ TimeUtil.formatDate(new Date(), "yyyyMMdd_HHmmss") + "." +"xlsx");
			if(exportType.equals("xlsx"))
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");// 定义输出类型:xlsx
			else
				response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型:xls
				response.setContentLength(ba.size());
				out = response.getOutputStream();
				ba.writeTo(out);
				out.flush();
			}
			finally 
			{
				if (out != null) out.close();
				if (ba != null) ba.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
