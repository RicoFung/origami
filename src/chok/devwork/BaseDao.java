package chok.devwork;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;


public abstract class BaseDao<T,PK> extends SqlSessionDaoSupport
{
	/**
	 * 用于返回命名空间的全路径Class.getName()
	 * @return Class
	 */
	protected abstract Class<T> getEntityClass();
	private String _statement = null;
	private static int DEFAULT_OFFSET = 1;
	private static int DEFAULT_LIMIT = 5;
	
	/**
	 * 返回命名空间的值
	 * @return String
	 */
	private String getSqlNamespace()
	{
		return getEntityClass().getName();
	}

	/**
	 * 获取需要操作sql的id，当getEntityClass().getName()无法满足时，可以重载此方法
	 * @param statementName SQL的ID(不包含namespace)
	 * @return String
	 */
	protected String getStatementName(String statementName)
	{
		if(_statement == null)
		{
			_statement = getSqlNamespace() + ".";
		}
		return _statement + statementName;
	}
	
	public void add(T po)
	{
		this.getSqlSession().insert(getStatementName("add"), po);
	}
	
	public void upd(T po)
	{
		this.getSqlSession().update(getStatementName("upd"), po);
	}

	public void del(PK id)
	{
		this.getSqlSession().delete(getStatementName("del"), id);
	}

	@SuppressWarnings("unchecked")
	public T get(PK id)
	{
		return (T) this.getSqlSession().selectOne(getStatementName("get"), id);
	}
	
	public List<T> query(Map<String, Object> m)
	{
		return this.getSqlSession().selectList(getStatementName("query"), m);
	}
	
	public List queryMap(Map<String, Object> m)
	{
		return this.getSqlSession().selectList(getStatementName("queryMap"), m);
	}
	
	public int getCount(Map<String, Object> m)
	{
		return (Integer) this.getSqlSession().selectOne(getStatementName("getCount"), m);
	}

	/**
	 * 分页查询
	 * @param countPageEach 可点击页码个数 
	 * @param m 表单查询参数
	 * @return Page对象
	 */
	public Page<T> getPage(int countPageEach, Map<String, Object> m)
	{
		int curPage = !m.containsKey("offset")?DEFAULT_OFFSET:Integer.parseInt(m.get("offset").toString());
		int limit = !m.containsKey("limit")?DEFAULT_LIMIT:Integer.parseInt(m.get("limit").toString());
		int offset = curPage*limit-(limit-1);
		//总记录数
		int totalCount = getCount(m);
		//总页码
		int countPage = totalCount%limit>0?totalCount/limit+1:totalCount/limit;
		//mysql index 从0开始，所以要减一；oracle index 从1开始
		offset--;
		
		m.put("offset", String.valueOf(offset));
		m.put("limit", String.valueOf(limit));
		List<T> result = query(m);
		return new Page<T>(curPage, countPage, countPageEach, limit, result);
	}
	
	public List queryMapPage(Map<String, Object> m)
	{
		List result = queryMap(m);
		return result;
	}
}
