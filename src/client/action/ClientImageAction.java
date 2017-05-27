package client.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

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
	
	@RequestMapping("/getPageByPid")
	public void getPageByPid() 
	{
		Map<String, Object> m = req.getParameterValueMap(false, true);
		List<Map<String,String>> resultList = service.queryMapPage(m);
		print(new Gson().toJson(resultList));
		System.out.println(new Gson().toJson(resultList));
	}
	
	@RequestMapping("/getListByPid")
	public void getListByPid() 
	{
		Map<String, Object> m = req.getParameterValueMap(false, true);
		List<Map<String,String>> resultList = service.queryMap(m);
		print(new Gson().toJson(resultList));
		System.out.println(new Gson().toJson(resultList));
	}
}