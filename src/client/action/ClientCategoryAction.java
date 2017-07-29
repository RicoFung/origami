package client.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chok.devwork.BaseController;
import client.entity.Category;
import client.service.ClientCategoryService;

@Scope("prototype")
@Controller
@RequestMapping("/client/category")
public class ClientCategoryAction extends BaseController<Category>
{
	@Autowired
	private ClientCategoryService service;

	@SuppressWarnings("unchecked")
	@RequestMapping("/query")
	public void query()
	{
		Map<String, Object> m = req.getParameterValueMap(false, true);
		List<Map<String,String>> list = service.queryMap(m);
		printJson(list);
	}
}