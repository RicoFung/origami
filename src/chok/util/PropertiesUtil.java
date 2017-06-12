package chok.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil
{
	private static Properties prop;
	
	public static String getValue(String key)
	{
		if (prop == null) prop = new Properties();
		try 
		{  
		  prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("config/config.properties"));  
		  return prop.getProperty(key);
		} 
		catch(IOException e) 
		{  
		  e.printStackTrace();
		  return "";
		}  
	}
}
