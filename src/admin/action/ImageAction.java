package admin.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import admin.entity.Image;
import admin.service.ImageService;
import admin.service.ModelService;
import chok.devwork.BaseController;
import chok.util.CollectionUtil;

@Scope("prototype")
@Controller
@RequestMapping("/admin/image")
public class ImageAction extends BaseController<Image>
{
	@Autowired
	private ImageService service;
	@Autowired
	private ModelService modelService;
	
	@RequestMapping("/add")
	public String add() 
	{
		put("queryParams",req.getParameterValueMap(false, true));
		return "/admin/image/add.jsp";
	}
	@RequestMapping("/add2")
	public void add2(@RequestParam("myFile") CommonsMultipartFile files[])
	{
		try
		{
			service.addBatch(files, req.getLong("model_id"));
			result.setSuccess(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		printJson(result);
	}
	
	@RequestMapping("/upd2")
	public void upd2(Image po) 
	{
		try
		{
			service.upd(po);
			print("1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}
	
	@RequestMapping("/del")
	public void del() 
	{
		try
		{
			service.delBatch(CollectionUtil.toLongArray(req.getLongArray("id[]", 0l)));
			result.setSuccess(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		printJson(result);
	}

	@RequestMapping("/get")
	public String get() 
	{
		Image po = service.get(req.getLong("id"));
		put("po", po);
		put("modelName", modelService.get(po.getLong("model_id")).get("name"));
		put("queryParams",req.getParameterValueMap(false, true));
		return "/admin/image/get.jsp";
	}

	@RequestMapping("/query")
	public String query() 
	{
		put("queryParams",req.getParameterValueMap(false, true));
		return "/admin/image/query.jsp";
	}
	
	@RequestMapping("/query2")
	public void query2()
	{
		Map<String, Object> m = req.getParameterValueMap(false, true);
		result.put("total",service.getCount(m));
		result.put("rows",service.query(req.getDynamicSortParameterValueMap(m)));
		printJson(result.getData());
	}
}