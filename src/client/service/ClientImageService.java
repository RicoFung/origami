package client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import client.dao.ClientImageDao;
import client.entity.Image;


@Service
public class ClientImageService extends BaseService<Image,Long>
{
	@Autowired
	private ClientImageDao dao;

	@Override
	public BaseDao<Image,Long> getEntityDao() {
		return dao;
	}
}
