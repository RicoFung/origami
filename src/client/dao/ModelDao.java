package client.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import chok.devwork.BaseDao;
import client.entity.Model;

@Repository
public class ModelDao extends BaseDao<Model,Long>
{
	@Override
	public Class getEntityClass()
	{
		return Model.class;
	}
	
	public List getMapJoinCategoryPage(Map m)
	{
		List result = this.getSqlSession().selectList(getStatementName("getMapJoinCategory"), m);
		return result;
	}
}
