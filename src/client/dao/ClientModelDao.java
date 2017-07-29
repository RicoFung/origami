package client.dao;

import org.springframework.stereotype.Repository;

import chok.devwork.BaseDao;
import client.entity.Model;

@Repository
public class ClientModelDao extends BaseDao<Model,Long>
{
	@Override
	public Class<Model> getEntityClass()
	{
		return Model.class;
	}
}
