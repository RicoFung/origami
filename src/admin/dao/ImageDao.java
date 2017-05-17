package admin.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import admin.entity.Image;
import chok.devwork.BaseDao;

@Repository
public class ImageDao extends BaseDao<Image,Long>
{
	@Override
	public Class<Image> getEntityClass()
	{
		return Image.class;
	}
	
	public void delByModelIds(Map<String, Object> m)
	{
		this.getSqlSession().update(getStatementName("delByModelIds"), m);
	}
}
