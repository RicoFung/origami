package client.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chok.devwork.BaseController;
import client.entity.Model;
import client.service.ClientModelService;

@Scope("prototype")
@Controller
@RequestMapping("/client/model")
public class ClientModelAction extends BaseController<Model>
{
	@Autowired
	private ClientModelService service;

	@SuppressWarnings("unchecked")
	@RequestMapping("/queryPage")
	public void queryPage() 
	{
		Map<String, Object> m = req.getParameterValueMap(false, true);
		List<Map<String,String>> list = service.queryMapPage(m);
		printJson(list);
	}
}