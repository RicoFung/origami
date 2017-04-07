package admin.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.dao.ImageDao;
import admin.entity.Image;
import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import chok.util.PropertiesUtil;

@Service
public class ImageService extends BaseService<Image,Long>
{
	@Autowired
	private ImageDao dao;

	@Override
	public BaseDao<Image,Long> getEntityDao() 
	{
		return dao;
	}
	
	public void addBatch(List<Image> poList)
	{
		for(Image po : poList)
		{
			add(po);
		}
	}
	
	public void delBatch(Long[] ids)
	{
		try 
		{
			// 删除图片文件
			for(int i=0; i<ids.length; i++)
			{
				File f = new File(PropertiesUtil.getImageUploadPath()+dao.getById(ids[i]).getString("url"));
				if(f.exists()) FileUtils.forceDelete(f);
			}
			// 删除表记录
			super.del(ids);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void updSortById(Image po)
	{
		dao.updSortById(po);
	}
	
	public void updSortBatch(Long[] ids, Integer[] sorts)
	{
		for(int i=0; i<ids.length; i++)
		{
			Image po = new Image();
			po.set("id",ids[i]);
			po.set("sort",sorts[i]);
			dao.updSortById(po);
		}
	}
}
