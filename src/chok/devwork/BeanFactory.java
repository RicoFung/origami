package chok.devwork;

import org.springframework.context.ApplicationContext;
/**
 * 从ApplicationContext取得spring管理的类<br />
 * 默认情况下spring容器从org.springframework.web.context.ContextLoaderListener.getCurrentWebApplicationContext()中获取<br />
 * 如果在非web应用中使用，则必须调用setApplicationContext方法进行设置
 */
@SuppressWarnings("all")
public class BeanFactory
{
	private static ApplicationContext context;
	
	private static ApplicationContext getApplicationContext()
	{
		if(context == null)
		{
			context = org.springframework.web.context.ContextLoaderListener.getCurrentWebApplicationContext();
		}
		return context;
	}
	
	public static void setApplicationContext(ApplicationContext context)
	{
		BeanFactory.context = context;
	}
	
	/**
	 * 取得Spring托管的类
	 * @param name 托管类的Class
	 * @return Object
	 */
	public static Object getBean(Class name)
	{
		return getApplicationContext().getBean(name);
	}
	
	/**
	 * 取得Spring托管的类
	 * @param name 托管类的ID
	 * @return Object
	 */
	public static Object getBean(String name)
	{
		return getApplicationContext().getBean(name);
	}
}