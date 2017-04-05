package chok.devwork;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PageNav<T>
{
	private Page<T> page;
	private String pageSizeListStr;
	private boolean isOutForm = false;
	private static String LABEL_PAGE_LAST = "<";
	private static String LABEL_PAGE_NEXT = ">";
	private static String PAGEFORMID = "chokPageForm";
	private String formId = PAGEFORMID;
	private String formString = "";
	
	public PageNav(HttpServletRequest request, Page<T> page)
	{
		this(request, page, "10,20,50,100");
	}
	public PageNav(HttpServletRequest request, Page<T> page, String pageSizeListStr)
	{
		super();
		this.page = page;
		this.pageSizeListStr = pageSizeListStr;
		formId = formId + System.currentTimeMillis();
		try
		{
			StringBuilder sb = new StringBuilder();
			sb.append("<script type=\"text/javascript\">"
					+ "if(typeof($jschok)!=\"object\"){$jschok={};}"
					+ "$jschok.page={"
					+ "		go:function(formID,page){"
					+ "			page=parseInt(page)||1;page=(page<1)?1:page;"
					+ "			$(\"#\"+formID+\"_page\").val(page);"
					+ "			$(\"#\"+formID).submit();"
					+ "		}"
					+ "};"
					+ "\n"
					+ "$(function(){"
					+ "		$(\"#pageSizeSelector\").change(function(){"
					+ "			$(\"#"+formId+"_page\").val(1);"
					+ "			$(\"#"+formId+"_pageSize\").val($(this).val());"
					+ "			$(\"#"+formId+"\").submit();"
					+ "		});"
					+ "		$(\"#pageGo\").click(function(){"
					+ "			$(\"#"+formId+"_page\").val($(\"#pageTo\").val());"
					+ "			$(\"#"+formId+"\").submit();"
					+ "		});"
					+ "});"
					+ "</script>\n");
			sb.append("<form id=\"").append(formId).append("\" method=\"post\" style=\"display:none;\" action=\"").append(request.getRequestURI().toString()).append("\">");
			sb.append("<input id=\"").append(formId).append("_page\" name=\"").append("offset").append("\" type=\"hidden\" value=\"1\"/>\n");
			sb.append("<input id=\"").append(formId).append("_pageSize\" name=\"").append("limit").append("\" type=\"hidden\" value=\"").append(page.getPageSize()).append("\"/>\n");
			
			@SuppressWarnings("all")
			Enumeration e = request.getParameterNames();
			String key = "";
			String value[];
			while(e.hasMoreElements())
			{
				key = (String) e.nextElement();
				if(!key.equals("offset") && !key.equals("limit"))
				{
					value = request.getParameterValues(key);
					if(value == null || value.length == 0)
					{
						sb.append("<input name=\"").append(key).append("\" type=\"hidden\" value=\"\"/>\n");
					}
					else
					{
						for(int i = 0; i < value.length; i++)
						{
							if(value[i] == null)
							{
								value[i] = "";
							}
							sb.append("<input name=\"").append(key).append("\" type=\"hidden\" value=\"").append(value[i].replace("\"", "&quot;")).append("\"/>\n");
						}
					}
				}
			}
			sb.append("</form>");
			formString = sb.toString();
		}
		catch(Exception e)
		{
		}
	}

	/**
	 * 输出表单
	 */
	public String getForm()
	{
		if(!isOutForm)
		{
			isOutForm = true;
			return formString;
		}
		return "";
	}
	
	//获取翻页导航html（jsp通过jstl调用${pageNav.pageNav}生成翻页html）
	public String getPageHtml()
	{
		return createPageHtml();
	}
	
	//获取结果集
	public List<T> getResult()
	{
		return page.getResult();
	}
	
	//创建翻页导航html
	private String createPageHtml()
	{
		int curPage=this.page.getCurPage();
		int countPage=this.page.getCountPage();
		int countPageEach=this.page.getCountPageEach();
		
		StringBuffer strHtml=new StringBuffer();
		
		int startPage = 0,endPage = 0;
		strHtml.append(getForm());
		
		//组件1->页码按钮------------------------------------
		strHtml.append("<div class=\"pull-right\">");
		strHtml.append("<table>");
		strHtml.append("<tr>");
		strHtml.append("<td>");
		strHtml.append("<ul class=\"pagination\" style=\"display:inline\">");
		if(countPageEach % 2 == 0)
		{ //每次显示页数为偶数			
			even(curPage,countPage,countPageEach,strHtml,startPage,endPage);
		}
		else
		{ //每次显示页数为奇数
			odd(curPage,countPage,countPageEach,strHtml,startPage,endPage);
		}
		strHtml.append("</ul>");
		strHtml.append("</td>");
		strHtml.append("</tr>");
		strHtml.append("</table>");
		strHtml.append("</div>");
		//------------------------------------
		
		//组件2->每页N条，输入页码跳转------------------------------------
		strHtml.append("<div class=\"pull-left\">");
		strHtml.append("<table>");
		strHtml.append("<tr>");
		//每页N条------------------------------------
		strHtml.append("<td>");
	 	strHtml.append("<select class=\"form-control\" id=\"pageSizeSelector\">");
		String[] pageSizeArr = pageSizeListStr.split(",");
	    for(int i=0; i<pageSizeArr.length; i++)
	    {
	    	int pageSize = Integer.valueOf(pageSizeArr[i]);
			if(pageSize == page.getPageSize())
				strHtml.append("<option selected=\"selected\" value=\""+pageSize+"\">"+pageSize+"</option>");
			else
				strHtml.append("<option value=\""+pageSize+"\">"+pageSize+"</option>");
		}
	    strHtml.append("</select>");
		strHtml.append("</td>");
		//输入页码跳转------------------------------------
		strHtml.append(""
				+ "<td>"
				+ "<input type=\"text\" class=\"form-control\" id=\"pageTo\" name=\"pageTo\" style=\"width:35px;text-align:center\" value=\""+curPage+"\"/>"
				+ "</td>"
				+ "");
		strHtml.append(""
				+ "<td>"
				+ "<button type=\"button\" class=\"btn  btn-primary\" id=\"pageGo\">GO</button>"
				+ "</td>");
		strHtml.append("</tr>");
		strHtml.append("</table>");
		strHtml.append("</div>");
		//------------------------------------
		return strHtml.toString();
		
	}
	
	/*
	 * 奇数页码样式
	 */
	private void odd(int curPage,int countPage,int countPageEach,StringBuffer strHtml,int startPage,int endPage)
	{
		/*样式① 【1】 2 3 4 5 下一页*/
		if(countPage<=countPageEach)
		{
			if(curPage>1)
			{
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
			}
			for(int i=1;i<=countPage;i++){
				if(curPage==i)
				{
					strHtml.append("<li class=\"active\"><a href=\"#\">"+i+"</a></li>");
					continue;
				}
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
			}
			if(curPage<countPage)
			{
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">"+LABEL_PAGE_NEXT+"</a></li>");
			}
		}
		/*样式②*/
		else if(curPage<=(countPageEach/2)*2){

			if(curPage==1)
			{//【1】 2 3 4 5 ... 16下一页
				strHtml.append("<li class=\"active\"><a href=\"#\">1</a></li>");
				startPage=2;
				endPage=countPageEach;
				for(int i=startPage;i<=endPage;i++)
				{
					strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
				}
			}
			else if(curPage>1 && curPage<countPageEach)
			{//上一页 1 2 3 【4】 5  ... 16 下一页
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
				startPage=1;
				endPage=countPageEach;
				for(int i=startPage;i<=endPage;i++)
				{
					if(curPage==i)
					{
						strHtml.append("<li class=\"active\"><a href=\"#\">"+i+"</a></li>");
						continue;
					}
					strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
				}
			}
			strHtml.append("<li>...</li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+countPage+"');return false;\" href=\"#\">"+countPage+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">"+LABEL_PAGE_NEXT+"</a></li>");
		}
		/*样式③ 上一页 1 ... 3 4 【5】 6 7  ... 16 下一页*/
		else if(curPage>(countPageEach/2)+2 && curPage <= (countPage - countPageEach+1))
		{
			startPage = curPage - countPageEach / 2;
			endPage = curPage + countPageEach / 2;
			if(endPage >= countPage)
			{
				endPage=countPage;
			}

			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></li>");
			strHtml.append("<li>...</li>");
			for(int i=startPage;i<=endPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<li class=\"active\"><a href=\"#\">"+i+"</a></li>");
					continue;
				}
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
			}
			strHtml.append("<li>...</li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+countPage+"');return false;\" href=\"#\">"+countPage+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">"+LABEL_PAGE_NEXT+"</a></li>");
			
		}
		/*样式④ 上一页 1 ... 12 13 14 【15】 16 下一页*/
		else if(curPage > (countPage - countPageEach)+1 && curPage < countPage)
		{
			startPage=countPage-countPageEach+1;
			endPage=countPage;
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></li>");
			strHtml.append("<li>...</li>");
			for(int i=startPage;i<=endPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<li class=\"active\"><a href=\"#\">"+i+"</a></li>");
					continue;
				}
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
			}
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">"+LABEL_PAGE_NEXT+"</a></li>");
		}
		/*样式⑤ 上一页 1 ...12 13 14 15 【16】 */
		else if(curPage==countPage)
		{
			startPage=countPage-countPageEach+1;
			endPage=countPage-1;
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></li>");
			strHtml.append("<li>...</li>");
			for(int i=startPage;i<=endPage;i++)
			{
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
			}
			strHtml.append("<li class=\"active\"><a href=\"#\">"+countPage+"</a></li>");
		}
	}
	
	/*
	 * 偶数页码样式
	 */
	private void even(int curPage,int countPage,int countPageEach,StringBuffer strHtml,int startPage,int endPage)
	{
		/*样式① 【1】 2 3 4 5 下一页*/
		if(countPage<=countPageEach)
		{
			if(curPage>1)
			{
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
			}
			for(int i=1;i<=countPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<li class=\"active\"><a href=\"#\">"+i+"</a></li>");
					continue;
				}
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
			}
			if(curPage<countPage)
			{
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">"+LABEL_PAGE_NEXT+"</a></li>");
			}
		}
		/*样式②*/
		else if(curPage<(countPageEach/2)*2){
			if(curPage==1)
			{//【1】 2 3 4 5 ... 16下一页
				strHtml.append("<li class=\"active\"><a href=\"#\">1</a></li>");
				startPage=2;
				endPage=countPageEach;
				for(int i=startPage;i<=endPage;i++)
				{
					strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
				}
			}
			else if(curPage>1 && curPage<countPageEach)
			{//上一页 1 2 3 【4】 5  ... 16 下一页
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
				startPage=1;
				endPage=countPageEach;
				for(int i=startPage;i<=endPage;i++)
				{
					if(curPage==i)
					{
						strHtml.append("<li class=\"active\"><a href=\"#\">"+i+"</a></li>");
						continue;
					}
					strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
				}
			}
			strHtml.append("<li>...</li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+countPage+"');return false;\" href=\"#\">"+countPage+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">"+LABEL_PAGE_NEXT+"</a></li>");
		}
		/*样式③ 上一页 1 ... 3 4 【5】 6 7  ... 16 下一页*/
		else if(curPage>(countPageEach/2)+1 && curPage <= (countPage - countPageEach+1))
		{
			startPage = (curPage - countPageEach / 2) + 1;
			endPage = curPage + countPageEach / 2 ;

			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></li>");
			strHtml.append("<li>...</li>");
			for(int i=startPage;i<=endPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<li class=\"active\"><a href=\"#\">"+i+"</a></li>");
					continue;
				}
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
			}
			strHtml.append("<li>...</li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+countPage+"');return false;\" href=\"#\">"+countPage+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">"+LABEL_PAGE_NEXT+"</a></li>");
			
		}
		/*样式④ 上一页 1 ... 12 13 14 【15】 16 下一页*/
		else if(curPage > (countPage - countPageEach)+1 && curPage < countPage)
		{
			startPage=countPage-countPageEach+1;
			endPage=countPage;
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></li>");
			strHtml.append("<li>...</li>");
			for(int i=startPage;i<=endPage;i++)
			{
				if(curPage==i)
				{
					strHtml.append("<li class=\"active\"><a href=\"#\">"+i+"</a href=\"#\"></li>");
					continue;
				}
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
			}
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage+1)+"');return false;\" href=\"#\">"+LABEL_PAGE_NEXT+"</a></li>");
		}
		/*样式⑤ 上一页 1 ...12 13 14 15 【16】 */
		else if(curPage==countPage)
		{
			startPage=countPage-countPageEach+1;
			endPage=countPage-1;
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+(curPage-1)+"');return false;\" href=\"#\">"+LABEL_PAGE_LAST+"</a></li>");
			strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+1+"');return false;\" href=\"#\">1</a></li>");
			strHtml.append("<li>...</li>");
			for(int i=startPage;i<=endPage;i++)
			{
				strHtml.append("<li><a onclick=\"$jschok.page.go('" + formId+ "', '"+i+"');return false;\" href=\"#\">"+i+"</a></li>");
			}
			strHtml.append("<li class=\"active\"><a href=\"#\">"+countPage+"</a></li>");
		}		
	}
}
