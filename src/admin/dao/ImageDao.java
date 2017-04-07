package admin.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import admin.entity.Image;
import chok.devwork.BaseDao;

@Repository
public class ImageDao extends BaseDao<Image,Long>
{
	@Override
	public Class getEntityClass()
	{
		return Image.class;
	}
	
	public void updSortById(Image po)
	{
		this.getSqlSession().update(getStatementName("updSortById"), po);
	}
	
	public void delByPids(Map<String, Object> m)
	{
		this.getSqlSession().update(getStatementName("delByPids"), m);
	}
}
