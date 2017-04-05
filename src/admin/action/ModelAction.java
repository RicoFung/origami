package admin.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import admin.entity.Model;
import admin.service.CategoryService;
import admin.service.ModelService;
import chok.devwork.BaseController;
import chok.util.CollectionUtil;


@Scope("prototype")
@Controller
@RequestMapping("/admin/model")
public class ModelAction extends BaseController<Model>
{
	@Autowired
	private ModelService service;
	@Autowired
	private CategoryService catService;
	
	@RequestMapping("/add1")
	public String add1() 
	{
		put("catList", catService.get(null));
		put("queryParams",req.getParameterValueMap(false, true));
		return "/admin/model/add.jsp";
	}
	@RequestMapping("/add2")
	public void add2(Model po) 
	{
		try
		{
			service.add(po);
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
		try{
			service.delBatch(CollectionUtil.toLongArray(req.getLongArray("id[]", 0l)));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		printJson(result);
	}
	
	@RequestMapping("/upd1")
	public String upd1() 
	{
		put("po", service.getById(req.getLong("id")));
		put("catList", catService.get(null));
		put("queryParams", req.getParameterValueMap(false, true));
		return "/admin/model/upd.jsp";
	}
	@RequestMapping("/upd2")
	public void upd2(Model po) 
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

	@RequestMapping("/getById")
	public String getById() 
	{
		put("po", service.getById(req.getLong("id")));
		put("catList", catService.get(null));
		put("queryParams", req.getParameterValueMap(false, true));
		return "/admin/model/getById.jsp";
	}

	@RequestMapping("/get")
	public String get() 
	{
		put("catList", catService.get(null));
		put("queryParams",req.getParameterValueMap(false, true));
		return "/admin/model/get.jsp";
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