package client.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

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

	@RequestMapping("/getPageByPid")
	public void getPageByPid() 
	{
		Map<String, Object> m = req.getParameterValueMap(false, true);
		List<Map<String,String>> resultList = service.getMapJoinCategoryPage(m);
		print(new Gson().toJson(resultList));
		System.out.println(new Gson().toJson(resultList));
	}
}