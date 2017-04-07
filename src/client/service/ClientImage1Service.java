package client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import client.dao.ClientImage1Dao;
import client.entity.Image1;


@Service
public class ClientImage1Service extends BaseService<Image1,Long>
{
	@Autowired
	private ClientImage1Dao dao;

	@Override
	public BaseDao<Image1,Long> getEntityDao() 
	{
		return dao;
	}
	
	public Image1 getByPidAndMaxSort(Long pid)
	{
		return dao.getByPidAndMaxSort(pid);
	}
}
