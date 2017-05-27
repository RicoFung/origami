package chok.devwork;

import java.util.List;
import java.util.Map;

public abstract class BaseService<T,PK> 
{
	public abstract BaseDao<T, PK> getEntityDao();
	
	public void add(T po)
	{
		getEntityDao().add(po);
	}

	public void upd(T po) 
	{
		getEntityDao().upd(po);
	}

	public void del(PK[] ids) 
	{
		for(PK id:ids)
		{
			getEntityDao().del(id);
		}
	}

	public T get(PK id)
	{
		return (T) getEntityDao().get(id);
	}

	public List<T> query(Map<String, Object> m) 
	{
		return getEntityDao().query(m);
	}
	
	public List queryMap(Map<String, Object> m)
	{
		return getEntityDao().queryMap(m);
	}
	
	public int getCount(Map<String, Object> m) 
	{
		return getEntityDao().getCount(m);
	}

	/**
	 * 分页查询
	 * @param countPageEach 可点击页码个数 
	 * @param m 表单查询参数
	 * @return Page对象
	 */
	public Page<T> getPage(int countPageEach, Map<String, Object> m)
	{
		return getEntityDao().getPage(countPageEach, m);
	}
	
	public List queryMapPage(Map<String, Object> m)
	{
		return getEntityDao().queryMapPage(m);
	}
}
