package admin.dao;

import org.springframework.stereotype.Repository;

import admin.entity.Model;
import chok.devwork.BaseDao;

@Repository("paperModelDao")
public class ModelDao extends BaseDao<Model,Long>
{
	@Override
	public Class getEntityClass()
	{
		return Model.class;
	}
}
