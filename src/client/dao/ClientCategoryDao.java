package client.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import chok.devwork.BaseDao;
import client.entity.Category;

@Repository
public class ClientCategoryDao extends BaseDao<Category,Long>
{
	@Override
	public Class<Category> getEntityClass()
	{
		return Category.class;
	}
	
	public List<Map<String, String>> getNavDataJson(Map m) 
	{
		return this.getSqlSession().selectList(getStatementName("getNavMap"), m);
	}
}
