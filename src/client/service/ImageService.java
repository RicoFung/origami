package client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import client.dao.ImageDao;
import client.entity.Image;


@Service
public class ImageService extends BaseService<Image,Long>
{
	@Autowired
	private ImageDao dao;

	@Override
	public BaseDao<Image,Long> getEntityDao() {
		return dao;
	}
}
