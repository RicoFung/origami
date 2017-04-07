package admin.dao;

import org.springframework.stereotype.Repository;

import admin.entity.Category;
import chok.devwork.BaseDao;

@Repository
public class CategoryDao extends BaseDao<Category,Long>
{
	@Override
	public Class getEntityClass()
	{
		return Category.class;
	}
}
