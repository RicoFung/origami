package client.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import client.dao.ModelDao;
import client.entity.Model;

@Service
public class ModelService extends BaseService<Model,Long>
{
	@Autowired
	private ModelDao dao;

	@Override
	public BaseDao<Model,Long> getEntityDao() 
	{
		return dao;
	}
	
	public List getMapJoinCategoryPage(Map m)
	{
		return dao.getMapJoinCategoryPage(m);
	}
}
