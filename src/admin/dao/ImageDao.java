package admin.dao;

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
	
	public void delByModelIds(Long[] ids)
	{
		this.getSqlSession().update(getStatementName("delByModelIds"), ids);
	}
}
