package client.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chok.devwork.BaseController;
import client.entity.Image;
import client.service.ClientImageService;

@Scope("prototype")
@Controller
@RequestMapping("/client/image")
public class ClientImageAction extends BaseController<Image>
{
	@Autowired
	private ClientImageService service;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryPage")
	public void queryPage() 
	{
		Map<String, Object> m = req.getParameterValueMap(false, true);
		List<Map<String,String>> list = service.queryMapPage(m);
		printJson(list);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/query")
	public void query() 
	{
		Map<String, Object> m = req.getParameterValueMap(false, true);
		List<Map<String,String>> list = service.queryMap(m);
		printJson(list);
	}
}