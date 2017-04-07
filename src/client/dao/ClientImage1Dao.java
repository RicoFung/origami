package client.dao;

import org.springframework.stereotype.Repository;

import chok.devwork.BaseDao;
import client.entity.Image1;


@Repository
public class ClientImage1Dao extends BaseDao<Image1,Long>
{
	@Override
	public Class getEntityClass()
	{
		return Image1.class;
	}
	
	public Image1 getByPidAndMaxSort(Long pid)
	{
		return (Image1) this.getSqlSession().selectOne(getStatementName("getByPidAndMaxSort"), pid);
	}
}
