package client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import client.dao.CategoryDao;
import client.entity.Category;

@Service
public class CategoryService extends BaseService<Category,Long>
{
	@Autowired
	private CategoryDao dao;

	@Override
	public BaseDao<Category,Long> getEntityDao() {
		return dao;
	}
}
