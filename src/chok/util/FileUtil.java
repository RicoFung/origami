package chok.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 文件对象操作类
 * @author skey
 */
public class FileUtil
{
	/**
	 * 复制，覆盖目标，复制文件或文件夹（包括子文件夹）到目标位置
	 * @param fromFilePath 源文件或文件夹路径(全路径)
	 * @param toFilePath 目标文件或文件夹路径(全路径)
	 */
	public static void copy(String fromFilePath, String toFilePath)
	{
		copy(fromFilePath, toFilePath, true, true);
	}

	/**
	 * 复制
	 * @param fromFilePath 源文件或文件夹路径(全路径)
	 * @param toFilePath 目标文件或文件夹路径(全路径)
	 * @param overwrite 复制过程中遇到已存在的文件夹或文件是否覆盖改写
	 * @param copySubdir 复制文件夹时，是否复制子文件夹
	 */
	public static void copy(String fromFilePath, String toFilePath, boolean overwrite, boolean copySubdir)
	{
		File from = new File(fromFilePath);
		File to = new File(toFilePath);
		try
		{
			if(!from.exists())// 不存在来源（不是文件，也不是文件夹）
			{
				return;
			}
			if(to.exists() && !overwrite)// 当目标已存在，并且不能覆盖时
			{
				return;
			}
			if(from.isDirectory())
			{
				if(to.isFile())// 当目标已存在，保留文件夹，删除文件
				{
					FileUtil.delete(toFilePath);// 删除文件，让它变成文件夹
				}
				to.mkdirs();
				if(!to.exists())// 上面某一级是文件的情况，否则肯定创建成功
				{
					int i = 100;// 防止死循环
					File s = to.getParentFile();
					while(!s.exists() && i > 0)
					{
						s = s.getParentFile();
						i--;
					}
					if(s.isFile())// 找到那个文件了
					{
						delete(s.getPath());// 干掉它
						to.mkdirs();// 重新创建目录结构
					}
				}
				String[] fileList = from.list();
				if(fileList == null)
				{
					return;
				}
				for(int i = 0; i < fileList.length; i++)
				{
					File f = new File(from.getAbsoluteFile() + "/" + fileList[i]);
					if(f.isDirectory() && !copySubdir)
					{
						continue;// 不复制子文件夹
					}
					copy(f.getPath(), to.getAbsoluteFile() + "/" + fileList[i], overwrite, copySubdir);
				}
			}
			else if(from.isFile())
			{
				File parentFile = to.getParentFile();
				if(!parentFile.exists())
				{
					parentFile.mkdirs();// 创建所属目录
				}
				if(to.exists())// 当目标已存在，删除文件或文件夹
				{
					FileUtil.delete(toFilePath);// 递归删除文件或子文件夹，让它变成文件
				}
				writeFile(to.getPath(), (new FileInputStream(from)), true);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 根据文件夹路径创建文件夹
	 * @param filePath 文件夹全路径
	 */
	public static void createFolder(String filePath)
	{
		File f = new File(filePath);
		if(!f.exists())
		{
			f.mkdirs();
		}
	}

	/**
	 * 删除
	 * @param filePath 删除的文件夹或文件名称(全路径)
	 * @return boolean
	 */
	public static boolean delete(String filePath)
	{
		return delete(filePath, true, true, false);
	}

	/**
	 * 删除
	 * @param filePath 删除的文件夹或文件名称(全路径)
	 * @param isDeleteDir 是否删除自己（删除对象为文件夹时有效），值为false时isDeleteSubDir和isKeepStructure参数有效
	 * @param isDeleteSubDir 是否删除子文件夹，false时仅删除当前目录下的文件，值为true是isKeepStructure参数有效
	 * @param isKeepStructure 是否保留子文件夹的目录结构
	 * @return boolean
	 */
	public static boolean delete(String filePath, boolean isDeleteDir, boolean isDeleteSubDir, boolean isKeepStructure)
	{
		try
		{
			boolean success = true;
			File file = new File(filePath);
			if(file.isDirectory())
			{
				String[] children = file.list();
				for(int i = 0; i < children.length; i++)
				{
					File subFile = new File(file, children[i]);
					if(isDeleteDir || subFile.isFile())
					{
						success = delete(subFile.getPath(), true, true, false) && success;
					}
					else if(isDeleteSubDir)// 是否删除子目录
					{
						success = delete(subFile.getPath(), !isKeepStructure, true, isKeepStructure) && success;
					}
				}
				if(isDeleteDir)
				{
					success = file.delete() && success;// 删除该空目录
				}
			}
			else if(file.isFile())
			{
				success = file.delete() && success;// 删除该文件
			}
			return success;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	/**
	 * 取得文件后缀名
	 * @param filePath 文件名称(全路径)
	 * @return 返回String，文件不存在则返回null，没后缀名则返回""
	 */
	public static String getFileExt(String filePath)
	{
		try
		{
			File file = new File(filePath);
			if(file.isFile())
			{
				String name = file.getName();
				int len = name.lastIndexOf(".");
				return (len != -1) ? name.substring(len + 1) : "";
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回不重名的名称，失败返回空字符串
	 * @param fileName 不包括路径的文件夹或文件名称
	 * @param realPath 需要存放的文件夹的路径(不包括文件夹或文件名称)
	 * @return String，返回fileName或新名称："原名称(数字)"+".原扩展名"(有扩展名则加扩展名，无则不加)
	 */
	public static String getRefrainFileName(String fileName, String realPath)
	{
		if(fileName == null || fileName.trim().length() == 0)
		{
			return "";
		}
		fileName = fileName.trim();
		realPath = realPath + File.separator;
		int len = fileName.lastIndexOf(".");
		String f_ext = (len != -1) ? ("." + fileName.substring(len + 1)) : "";// .*或空
		String f_name = (len != -1) ? fileName.substring(0, len) : "";
		int count = 1;// 记数器
		File file = new File(realPath + fileName);// 读入文件对象
		StringBuilder sb = new StringBuilder();
		while(file.exists())// 判断文件是否存在
		{
			sb.setLength(0);
			sb.append(f_name).append("(").append(count).append(")").append(f_ext);
			count++;
			fileName = sb.toString();
			file = new File(realPath + fileName);// 重新读入文件对象
		}
		sb = null;
		return fileName;
	}

	/**
	 * 返回指定文件夹或文件的大小，单位bit
	 * @param filePath 文件名称(全路径)
	 * @return long
	 */
	public static long getSize(String filePath)
	{
		long size = 0;
		File file = new File(filePath);
		if(file.exists())
		{
			if(file.isFile())
			{
				size += file.length();
			}
			else
			{
				String[] tempList = file.list();
				for(int i = 0; i < tempList.length; i++)
				{
					size += getSize(filePath + "/" + tempList[i]);
				}
			}
		}
		return size;
	}

	/**
	 * InputStream转化为byte[]
	 * @param inStream 输入流
	 * @return byte[]
	 */
	public static byte[] getToByte(InputStream inStream)
	{
		try
		{
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] data = new byte[4096];
			int count = -1;
			while((count = inStream.read(data, 0, 4096)) != -1)
			{
				outStream.write(data, 0, count);
			}
			data = null;
			inStream.close();
			return outStream.toByteArray();
		}
		catch(Exception e)
		{
			return null;
		}
	}

	/**
	 * File转化为byte[](注意内存是否足够)
	 * @param filePath 文件名称(全路径)
	 * @return 成功返回byte[]，失败返回null
	 */
	public static byte[] getToByte(String filePath)
	{
		try
		{
			File file = new File(filePath);
			if(file.isFile())// 存在且为文件
			{
				FileInputStream fin = new FileInputStream(file);
				ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
				byte[] buffer = new byte[4096];
				int bytes_read;
				while((bytes_read = fin.read(buffer)) != -1)// Read until EOF
				{
					bout.write(buffer, 0, bytes_read);
				}
				fin.close();
				bout.close();
				return bout.toByteArray();
			}
		}
		catch(Exception ex)
		{
		}
		return null;
	}
	
	/**
	 * File转化为byte[](注意内存是否足够)
	 * @param file 文件
	 * @return 成功返回byte[]，失败返回null
	 */
	public static byte[] getToByte(File file)
	{
		try
		{
			if(file.isFile())// 存在且为文件
			{
				FileInputStream fin = new FileInputStream(file);
				ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
				byte[] buffer = new byte[4096];
				int bytes_read;
				while((bytes_read = fin.read(buffer)) != -1)// Read until EOF
				{
					bout.write(buffer, 0, bytes_read);
				}
				fin.close();
				bout.close();
				return bout.toByteArray();
			}
		}
		catch(Exception ex)
		{
		}
		return null;
	}

	/**
	 * byte[]转化为InputStream
	 * @param bytes byte[]
	 * @return InputStream
	 */
	public static InputStream getToInputStream(byte[] bytes)
	{
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * 将InputStream转换为字符串
	 * @param inStream 输入流
	 * @param charsetName 字符集，如：utf-8，gbk
	 * @return 成功返回String，失败返回null
	 */
	public static String getToString(InputStream inStream, String charsetName)
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(inStream, charsetName));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while((line = in.readLine()) != null)
			{
				buffer.append(line + "\n");
			}
			in.close();
			inStream.close();
			return buffer.toString();
		}
		catch(Exception e)
		{
			return null;
		}
	}

	/**
	 * 读取文件
	 * @param filePath 文件名称(全路径)
	 * @param charsetName 字符集，如：utf-8，gbk
	 * @return 成功返回String，失败返回null
	 */
	public static String readFile(String filePath, String charsetName)
	{
		File file = new File(filePath);
		try
		{
			if(file.isFile())
			{
				FileInputStream fin = new FileInputStream(file);
				return getToString(fin, charsetName);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将指定文件流写入指定的file
	 * @param filePath 文件名称(全路径)
	 * @param inStream 输入流
	 * @param overwrite 是否覆盖，目标只能是文件
	 * @return boolean 成功返回true，失败返回false
	 */
	public static boolean writeFile(String filePath, InputStream inStream, boolean overwrite)
	{
		try
		{
			File file = new File(filePath);
			if(!file.exists())
			{
				File parentFile = file.getParentFile();
				if(!parentFile.exists())
				{
					parentFile.mkdirs();// 创建所属目录
				}
			}
			else if(!overwrite || file.isDirectory())// 不可覆盖或是目录时
			{
				return false;
			}
			// 将该数据流写入到指定文件中
			FileOutputStream outStream = new FileOutputStream(file);
			byte[] buffer = new byte[8192];
			int bytesRead;
			while((bytesRead = inStream.read(buffer, 0, 8192)) != -1)// Read until EOF
			{
				outStream.write(buffer, 0, bytesRead);
			}
			outStream.close();
			inStream.close();
			return true;
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 写入文件，存在则覆盖（目标只能是文件）
	 * @param filePath 文件名称(全路径)
	 * @param content 文件内容
	 * @param charsetName 字符集，如：utf-8，gbk
	 * @return boolean 成功返回true，失败返回false
	 */
	public static boolean writeFile(String filePath, String content, String charsetName)
	{
		return writeFile(filePath, content, charsetName, true);
	}

	/**
	 * 写入文件
	 * @param filePath 文件名称(全路径)
	 * @param content 文件内容
	 * @param charsetName 字符集，如：utf-8，gbk
	 * @param overwrite 是否覆盖，目标只能是文件
	 * @return boolean 成功返回true，失败返回false
	 */
	public static boolean writeFile(String filePath, String content, String charsetName, boolean overwrite)
	{
		try
		{
			if(filePath == null || filePath.trim().length() == 0)
			{
				return false;
			}
			return writeFile(filePath, getToInputStream(content.getBytes(charsetName)), overwrite);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 序列化对象到文件
	 * @param obj
	 * @param filePath 文件名称(全路径)
	 */
	@Deprecated
	public static void serializeToFile(Object obj, String filePath)
	{
		try
		{
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(filePath));
			out.writeObject(obj);
			out.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 从文件反序列化出对象
	 * @param filePath 文件名称(全路径)
	 * @return Object
	 */
	@Deprecated
	public static Object deserializeFromFile(String filePath)
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(filePath)));
			Object obj = in.readObject();
			in.close();
			return obj;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MultipartFile 转换成File 
	 * @param multipartFile
	 * @return java.io.File
	 * @throws IOException
	 */
	public static File multipartFileToFile (MultipartFile multipartFile) throws IOException
	{
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartFile;
		FileItem fileItem = commonsMultipartFile.getFileItem();
		DiskFileItem diskFileItem = (DiskFileItem) fileItem;
		String absPath = diskFileItem.getStoreLocation().getAbsolutePath();
		File file = new File(absPath);

		// trick to implicitly save on disk small files (<10240 bytes by
		// default)

		if (!file.exists()) {
			file.createNewFile();
			multipartFile.transferTo(file);
		}

		return file;
	}
}
