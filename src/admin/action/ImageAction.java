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
import common.Dict;

@Scope("prototype")
@Controller
@RequestMapping("/admin/image")
public class ImageAction extends BaseController<Image>
{
	@Autowired
	private ImageService service;
	@Autowired
	private ModelService modelService;
	
	@RequestMapping("/add1")
	public String add1() 
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

	@RequestMapping("/getById")
	public String getById() 
	{
		Image po = service.getById(req.getLong("id"));
		put("po", po);
		put("modelName", modelService.getById(po.getLong("model_id")).get("name"));
		put("queryParams",req.getParameterValueMap(false, true));
		return "/admin/image/getById.jsp";
	}

	@RequestMapping("/get")
	public String get() 
	{
		put("queryParams",req.getParameterValueMap(false, true));
		return "/admin/image/get.jsp";
	}
	
	@RequestMapping("/getJson")
	public void getJson()
	{
		Map<String, Object> m = req.getParameterValueMap(false, true);
		result.put("total",service.getCount(m));
		result.put("rows",service.get(m));
		printJson(result.getData());
	}
}