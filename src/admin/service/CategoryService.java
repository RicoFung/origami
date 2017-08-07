package admin.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import admin.dao.CategoryDao;
import admin.entity.Category;
import chok.devwork.BaseDao;
import chok.devwork.BaseService;
import chok.util.POIUtil;

@Service
public class CategoryService extends BaseService<Category,Long>
{
	@Autowired
	private CategoryDao dao;

	@Override
	public BaseDao<Category,Long> getEntityDao() 
	{
		return dao;
	}
	
	public void imp2(CommonsMultipartFile files[]) throws IOException
	{
		for(int i=0; i<files.length; i++)
		{
			List<String[]> list = POIUtil.readExcel(files[i]);
			for(int j=0; j<list.size(); j++)
			{
				String [] r = list.get(j);
				Category po = new Category();
				po.set("name", r[0]);
				po.set("sort", r[1]);
				dao.add(po);
			}
		}
	}
}
