package client.dao;

import org.springframework.stereotype.Repository;

import chok.devwork.BaseDao;
import client.entity.Image;


@Repository
public class ClientImageDao extends BaseDao<Image,Long>
{
	@Override
	public Class getEntityClass()
	{
		return Image.class;
	}
}
