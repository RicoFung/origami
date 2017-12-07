package chok.devwork;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Ognl
{
	/**
	 * 可以用于判断 Map,Collection,String,Array是否为空
	 * 
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Object o) throws IllegalArgumentException
	{
		if (o == null)
			return true;
		if (o instanceof String)
		{
			return StringUtils.isEmpty((String) o);
		}
		else if (o instanceof Collection)
		{
			return CollectionUtils.isEmpty((Collection) o);
		}
		else if (o.getClass().isArray())
		{
			return ArrayUtils.isEmpty((Object[]) o);
		}
		else if (o instanceof Map)
		{
			MapUtils.isEmpty((Map) o);
		}
		else if (o instanceof Date)
		{
			return o == null;
		}
		else if (o instanceof Number)
		{
			return o == null;
		}
		else if (o instanceof Boolean)
		{
			return o == null;
		}
		else
		{
			throw new IllegalArgumentException(
					"Illegal argument type,must be : Map,Collection,Array,String. but was:" + o.getClass());
		}

		return false;
	}

	/**
	 * 可以用于判断 Map,Collection,String,Array是否不为空
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Object o)
	{
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(Object... objects)
	{
		if (objects == null)
			return false;
		for (Object obj : objects)
		{
			if (isEmpty(obj))
			{
				return false;
			}
			;
		}
		return true;
	}

	public static boolean isNotBlank(Object o)
	{
		return !isBlank(o);
	}

	public static boolean isNumber(Object o)
	{
		if (o instanceof Number)
		{
			return true;
		}
		else if (o instanceof String)
		{
			return NumberUtils.isDigits((String) o);
		}
		else
		{
			return false;
		}
	}

	public static boolean isBlank(Object o)
	{
		return StringUtils.isBlank((String) o);
	}

	public static boolean isBlank(String str)
	{
		return StringUtils.isBlank(str);
	}
}
