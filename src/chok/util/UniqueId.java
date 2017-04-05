package chok.util;

import java.util.Calendar;
import java.util.UUID;

/**
 * ID生成类
 */
public class UniqueId
{
	private static long thisId = 0;

	/**
	 * 根据时间戳产生一个唯一ID，具有防止重复机制
	 * @return long
	 */
	public synchronized static long genId() throws Exception
	{
		long id = 0;
		do
		{
			Calendar c = Calendar.getInstance();
			id = c.getTimeInMillis();
		}
		while (id == thisId);
		thisId = id;
		return id;
	}

	/**
	 * 返回GUID，格式00000000-0000-0000-0000-000000000000
	 * @return String
	 */
	public static String genGuid()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
