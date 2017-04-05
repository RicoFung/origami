package client.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import chok.devwork.BaseController;
import client.entity.Category;
import client.service.NavService;

@Scope("prototype")
@Controller
@RequestMapping("/client/nav")
public class NavAction extends BaseController<Category>
{
	@Autowired
	private NavService service;

	@RequestMapping("/getNavDataJson")
	public void getNavDataJson()
	{
		response.setContentType("application/json");
		Map<String, Object> m = req.getParameterValueMap(false, true);
		String result = service.getNavDataJson(m);
		print(result);
	}
	
//	public static void main(String[] args)
//	{
//		String jsonStr = "[{\"id\":3,\"name\":\"动物\",\"sort\":1,\"paperModelList\":[{\"id\":12,\"pid\":3,\"name\":\"牛\",\"sort\":2}]},{\"id\":2,\"name\":\"军事\",\"sort\":2,\"paperModelList\":[{\"id\":7,\"pid\":2,\"name\":\"飞机\",\"sort\":1},{\"id\":8,\"pid\":2,\"name\":\"船\",\"sort\":2}]},{\"id\":4,\"name\":\"工具\",\"sort\":3,\"paperModelList\":[]}]";
//		PaperCategory[] mGroups = null;
//		PaperModel[][] mChilds = null;
//		
//		Gson gson = new Gson();
//		List<PaperCategory> groupList = (List<PaperCategory>)gson.fromJson(jsonStr,  new TypeToken<List<PaperCategory>>(){}.getType());
//		mGroups = new PaperCategory[groupList.size()];
//		mChilds = new PaperModel[groupList.size()][];
//		for(int i=0; i<mGroups.length; i++)
//		{
//			mGroups[i] = groupList.get(i);
////			mChilds[i] = new PaperModel[];
//			System.out.println(mGroups[i].getName());
//			for(int j=0; j<mGroups[i].getPaperModelList().size(); j++)
//			{
//				System.out.println("-"+mGroups[i].getPaperModelList().get(j).getName());
//			}
//		}
//	}
	
	public static void main(String[] args) {
		String sJson ="[{\"id\":3,\"name\":\"动物\",\"sort\":1},{\"id\":2,\"name\":\"军事\",\"sort\":2},{\"id\":4,\"name\":\"工具\",\"sort\":3}]";
		Gson gson = new Gson();
        List<Map<String,String>> list = (List<Map<String,String>>)gson.fromJson(sJson,  new TypeToken<List<Map<String,String>>>(){}.getType());
        for(int i=0; i<list.size(); i++) {
        	Map<String,String> m = list.get(i);
        	System.out.println("*************");
        	for (String key : m.keySet()) {  
                System.out.println("key:" + key + ", values:" + m.get(key));  
            }  
        }
	}
	
}