package client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import client.dao.ClientModelDao;
import client.entity.Model;

@Service
public class ClientModelService extends BaseService<Model,Long>
{
	@Autowired
	private ClientModelDao dao;

	@Override
	public BaseDao<Model,Long> getEntityDao() 
	{
		return dao;
	}
}
