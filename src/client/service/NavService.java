package client.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import client.dao.CategoryDao;
import client.entity.Category;

@Service
public class NavService extends BaseService<Category,Long>
{
	@Autowired
	private CategoryDao dao;

	@Override
	public BaseDao<Category,Long> getEntityDao() {
		return dao;
	}
	
	public String getNavDataJson(Map m) 
	{
		List<Map<String,String>> list = dao.getNavDataJson(m);
		Gson gson = new Gson();  
		return gson.toJson(list);
	}
}
