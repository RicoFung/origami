package common;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chok.devwork.BaseController;

@Scope("prototype")
@Controller
@RequestMapping("/dict")
public class DictAction extends BaseController<Object>
{
	@RequestMapping("/getCategorys")
	public void getCategorys()
	{
		List<Object> list = Dict.getCategorys(req.getParameterValueMap(false, true));
		printJson(list);
	}

	@RequestMapping("/getModels")
	public void getModels()
	{
		List<Object> list = Dict.getModels(req.getParameterValueMap(false, true));
		printJson(list);
	}
	
}
