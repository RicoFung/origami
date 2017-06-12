package admin.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import admin.dao.ImageDao;
import admin.entity.Image;
import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import chok.util.FileUtil;
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
	
	public void addBatch(CommonsMultipartFile files[], Long modelId) throws IOException
	{
		List<Image> poList = new ArrayList<Image>();
		for(int i=0; i<files.length; i++)
		{
//			String __imgName = UniqueId.genGuid();
			String __imgName = modelId+"_"+files[i].getOriginalFilename();
			String __sort = files[i].getOriginalFilename().split("\\.")[0];
			//保存到db
			Image po = new Image();
			po.set("model_id", modelId);
			po.set("name", __imgName);
			po.set("sort", __sort);
			poList.add(po);
			//保存到硬盘
//			File srcFile = ((DiskFileItem)files[i].getFileItem()).getStoreLocation(); //报临时目录not exist错
			File srcFile = FileUtil.multipartFileToFile(files[i]);
			File destFile = new File(PropertiesUtil.getValue("image.upload.path"), __imgName);
			FileUtils.copyFile(srcFile, destFile);
		}
		for(Image po : poList)
		{
			add(po);
		}
	}
	
	public void delBatch(Long[] ids) throws IOException
	{
		// 删除图片文件
		for(int i=0; i<ids.length; i++)
		{
			File f = new File(PropertiesUtil.getValue("image.upload.path")+dao.get(ids[i]).getString("name"));
			if(f.exists()) FileUtils.forceDelete(f);
		}
		// 删除表记录
		super.del(ids);
	}
}
