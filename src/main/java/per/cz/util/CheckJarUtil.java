package per.cz.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 */
public class CheckJarUtil {

	/**
	 * 检查path所在路径下的所有jar时候有重复的类。既：为防止出现web项目中包或者类冲突的问题
	 * @param path
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public static Map<String,List<String>> checkJarConflict(String  path) throws ZipException, IOException
	{
		Map<String,List<String>> res=new HashMap<String,List<String>>();
		Map<String,List<String>> tmp=new HashMap<String,List<String>>();
		File f=new File(path);
		if(f.isDirectory())
		{
			File[] list = f.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(".jar"))
						return true;
					return false;
				}
			});
			for (File z : list) {
				ZipFile zip=new ZipFile(z);
				Enumeration<? extends ZipEntry> entries = zip.entries();
				while(entries.hasMoreElements())
				{
					ZipEntry zipEntry = entries.nextElement();
					if(zipEntry!=null&&zipEntry.getName().endsWith(".class"))
					{
						String key = zipEntry.getName().replaceAll("/", ".").replaceAll("\\.class$", "");
						List<String>  v = tmp.get(key);
						if(v==null)
						{
							List<String> l =new ArrayList<String>();
							l.add(z.getName());
							tmp.put(key, l);
						}
						else
						{
							v.add(z.getName());
							res.put(key,v);
						}
					}
				}
			}
		}
		return res;
	}
	/**
	 * 检查 war包中 WEB-INF/lib/文件下的所有jar是否冲突
	 * @param war
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public static Map<String,List<String>> checkJarConflictInWar(String war) throws ZipException, IOException
	{
		return checkJarConflictInZip(war,"WEB-INF/lib/");
	}
	/**
	 * 检查 zip压缩包中,在 path路径下的 所有jar,是否冲突
	 * @param zip
	 * @param path
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public static Map<String,List<String>> checkJarConflictInZip(String zip,String path) throws ZipException, IOException
	{
		Map<String,List<String>> res=new HashMap<String,List<String>>();
		Map<String,List<String>> tmp=new HashMap<String,List<String>>();
		ZipFile zipf=new ZipFile(new File(zip));
		Enumeration<? extends ZipEntry> entries = zipf.entries();
		path=path.replace('\\', '/').replaceAll("^/", "").replaceAll("/$", "")+"/";
		while(entries.hasMoreElements())
		{
			ZipEntry pZipEntry = entries.nextElement();
			if(pZipEntry.getName().startsWith(path))
			{
				InputStream inputStream = zipf.getInputStream(pZipEntry);
				if(inputStream==null)
					continue;
				ZipInputStream zpIn=new ZipInputStream(inputStream);
				if(zpIn==null)
					continue;
				ZipEntry zipEntry=null;
				String jarName=pZipEntry.getName().replaceAll(path, "");
				while((zipEntry = zpIn.getNextEntry())!=null)
				{
					if(zipEntry!=null&&zipEntry.getName().endsWith(".class"))
					{
						String key = zipEntry.getName().replaceAll("/", ".").replaceAll("\\.class$", "");
						List<String>  v = tmp.get(key);
						if(v==null)
						{
							List<String> l =new ArrayList<String>();
							l.add(jarName);
							tmp.put(key, l);
						}
						else
						{
							v.add(jarName);
							res.put(key,v);
						}
					}
				}
			}
		}
		return res;
	}
	public static void main(String[] args) {
		try {
			//		Map<String, List<String>> res = checkJarConflictInWar("F:\\项目\\ticketsBooking.war");
			//		Map<String, List<String>> res = checkJarConflict("F:\\test\\lib");
//			Map<String, List<String>> res = checkJarConflict("D:\\_git_repository\\xuegao\\xue_gao\\target\\xue-gao\\WEB-INF\\lib");
			Map<String, List<String>> res = checkJarConflict("D:\\Learning_related\\MyWorkSpace\\Projects\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\MPM\\WEB-INF\\lib");
			//		Map<String, List<String>> res = checkJarConflictInWar("D:\\_git_repository\\xuegao\\xue_gao\\target\\xue-gao.war");
			Set<String> keySet = res.keySet();
			Map<String,Integer> map=new HashMap<String,Integer>();
			for (String key : keySet) {
				String trim = res.get(key).toString().trim();
				map.put(trim,map.get(trim) ==null?1:map.get(trim).intValue()+1);
			}
			Set<String> keySet2 = map.keySet();
			if(keySet2.size()<=0)
			{
				System.out.println("恭喜你！！没有冲突");
				return ;
			}
			for(String key : keySet2)
			{
				System.out.println(key+"\t冲突文件数：\t"+map.get(key));
			}
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
