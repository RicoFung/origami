package client.dao;

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
}
