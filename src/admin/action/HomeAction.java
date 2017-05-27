package admin.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chok.devwork.BaseController;
import chok.util.PropertiesUtil;
import chok.util.http.HttpAction;
import chok.util.http.HttpResult;
import chok.util.http.HttpUtil;

@Scope("prototype")
@Controller
@RequestMapping("/admin/home")
public class HomeAction extends BaseController<Object>
{
	@RequestMapping("/query")
	public String query() 
	{
		return "/admin/home/query.jsp";
	}
	
	@RequestMapping("/searchMenu")
	public void searchMenu() 
	{
		Map<String, String> param = new HashMap<String, String>();
		param.put("tc_user_id", req.getString("tc_user_id"));
		param.put("tc_app_id", req.getString("tc_app_id"));
		param.put("tc_name", req.getString("tc_name"));
		HttpResult<String> r = HttpUtil.create(new HttpAction(PropertiesUtil.getValue("nav.menu.search.api"), param), String.class, "GET");
		if(log.isInfoEnabled()) log.info("搜索后 menuJson：" + r.getData());
		printJson(r.getData());
	}
}