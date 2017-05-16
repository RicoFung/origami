package common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import chok.devwork.BaseModel;
import chok.devwork.BaseService;

public class Dict 
{
	/**
	 * 获取Category集合
	 * @param params
	 * @return List<Object>
	 */
	public static List<Object> getCategorys(Map<String, Object> params)
	{
		return getCategorys(params, null);
	}
	public static List<Object> getCategorys(Map<String, Object> params, String type)
	{
		if("json".equals(type))
			return getJsons(params, Factory.getCategoryService());
		else
			return getList(params, Factory.getCategoryService());
	}
	
	/**
	 * 获取Model集合
	 * @param params
	 * @return List<Object>
	 */
	public static List<Object> getModels(Map<String, Object> params)
	{
		return getModels(params, null);
	}
	public static List<Object> getModels(Map<String, Object> params, String type)
	{
		if("json".equals(type))
			return getJsons(params, Factory.getModelService());
		else
			return getList(params, Factory.getModelService());
	}
	
	/**
	 * 获取集合(通用)
	 * @param params
	 * @param service
	 * @return List<Object>
	 */
	private static List<Object> getList(Map<String, Object> params, BaseService service)
	{
		List<Object> list = new ArrayList<Object>();
		List<BaseModel> resultData = service.get(params);
		for(BaseModel o : resultData)
		{
			list.add(o.getM());
		}
		return list;
	}
	/**
	 * 获取JSON集合(通用)
	 * @param params
	 * @param service
	 * @return List<Object>
	 */
	private static List<Object> getJsons(Map<String, Object> params, BaseService service)
	{
		List<Object> list = new ArrayList<Object>();
		List<BaseModel> resultData = service.get(params);
		for(BaseModel o : resultData)
		{
			list.add(JSON.toJSON(o.getM()));
		}
		return list;
	}
}
