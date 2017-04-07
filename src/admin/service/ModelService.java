package admin.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.dao.ImageDao;
import admin.dao.ModelDao;
import admin.entity.Image;
import admin.entity.Model;
import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import chok.util.PropertiesUtil;

@Service
public class ModelService extends BaseService<Model,Long>
{
	@Autowired
	private ModelDao paperModelDao;
	@Autowired
	private ImageDao imgDao;

	@Override
	public BaseDao<Model,Long> getEntityDao() 
	{
		return paperModelDao;
	}
	
	public void delBatch(Long[] ids)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pids", ids);
		List<Image> paperImages = imgDao.get(m);
		if (paperImages.size() == 0) return;
		try 
		{
			// 批量物理删除图片文件
			for(Image po : paperImages)
			{
				File f = new File(PropertiesUtil.getImageUploadPath()+po.getString("url"));
				if(f.exists()) FileUtils.forceDelete(f);
			}
			// 删除图片数据库记录
			imgDao.delByPids(m);
			// 删除模型
			super.del(ids);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
