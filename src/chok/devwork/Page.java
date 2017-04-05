package chok.devwork;

import java.util.ArrayList;
import java.util.List;

 /**
  * 
  * @author fzj
  *
  * @param <T>
  */
public class Page<T>
{
	private int curPage;
	private int countPage;
	private int countPageEach;
	private int pageSize = 10;
	private List<T> result;
	
	/**
	 * 
	 * @param curPage
	 * 					当前页
	 * @param countPage
	 * 					总页数
	 * @param countPageEach
	 * 					可点击页码个数	
	 * @param pageSize
	 * 					每页显示记录数	
	 */
	public Page(int curPage, int countPage, int countPageEach, int pageSize)
	{
		this(curPage, countPage, countPageEach, pageSize, new ArrayList<T>(0));
	}
	
	/**
	 * 
	 * @param curPage
	 * 					当前页
	 * @param countPage
	 * 					总页数
	 * @param countPageEach
	 * 					可点击页码个数	
	 * @param pageSize
	 * 					每页显示记录数	
	 * @param result
	 * 					返回结果集
	 */
	public Page(int curPage, int countPage, int countPageEach, int pageSize, List<T> result)
	{
		super();
		this.curPage = curPage;
		this.countPage = countPage;
		this.countPageEach = countPageEach;
		this.pageSize = pageSize;
		this.result = result;
	}
	/**
	 * 
	 * @param curPage
	 * 					当前页
	 * @param countPage
	 * 					总页数
	 * @param countPageEach
	 * 					可点击页码个数	
	 */
	public Page(int curPage, int countPage, int countPageEach)
	{
		this(curPage, countPage, countPageEach, new ArrayList<T>(0));
	}
	
	/**
	 * 
	 * @param curPage
	 * 					当前页
	 * @param countPage
	 * 					总页数
	 * @param countPageEach
	 * 					可点击页码个数	
	 * @param result
	 * 					返回结果集
	 */
	public Page(int curPage, int countPage, int countPageEach, List<T> result)
	{
		super();
		this.curPage = curPage;
		this.countPage = countPage;
		this.countPageEach = countPageEach;
		this.result = result;
	}
	
	public int getCurPage()
	{
		return curPage;
	}
	public void setCurPage(int curPage)
	{
		this.curPage = curPage;
	}
	public int getCountPage()
	{
		return countPage;
	}
	public void setCountPage(int countPage)
	{
		this.countPage = countPage;
	}
	public int getCountPageEach()
	{
		return countPageEach;
	}
	public void setCountPageEach(int countPageEach)
	{
		this.countPageEach = countPageEach;
	}
	public int getPageSize() 
	{
		return pageSize;
	}
	public void setPageSize(int pageSize) 
	{
		this.pageSize = pageSize;
	}
	public List<T> getResult()
	{
		return result;
	}
	public void setResult(List<T> result)
	{
		this.result = result;
	}
	
	
}
