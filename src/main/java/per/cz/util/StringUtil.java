package per.cz.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String composeMessage1(String template, String regexMatcher,String replacement)  
			throws Exception {  
		//String regex = "\\$\\{(.+?)\\}";  
		Pattern pattern = Pattern.compile(regexMatcher);  
		Matcher matcher = pattern.matcher(template);  
		/* 
		 * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 
		 * 存储起来。 
		 */  
		StringBuffer sb = new StringBuffer();  
		while (matcher.find()) {  
			System.out.println(matcher.group());
			System.out.println(matcher.groupCount());
			//String name = matcher.group(1);//键名  
			String value = replacement;//键值  
			if (value == null) {  
				value = "";  
			} else {  
				//System.out.println("value=" + value);  
				value = value.replaceAll("\\$", "\\\\\\$");  
//				value = value.replaceAll("\\$", ".$");
				//System.out.println("value=" + value);  
			}  
			matcher.appendReplacement(sb, value);  
			//System.out.println("sb = " + sb.toString());  
		} 
		matcher.appendTail(sb);  
		return sb.toString();  
	}
	public static boolean isEmail(String e)
	{
		return e.matches("[.[^@]]+@[.[^@]]+");
	}
	public static String format (String template, Map data){  
		String regex = "\\{(.+?)\\}";  
		Pattern pattern = Pattern.compile(regex);  
		Matcher matcher = pattern.matcher(template);  
		/* 
		 * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 
		 * 存储起来。 
		 */  
		StringBuffer sb = new StringBuffer();  
		while (matcher.find()) {  
			String name = matcher.group(1);//键名  
			String value = (String) data.get(name);//键值  
			if (value == null) {  
				value = "";  
			} else {  
				/* 
				 * 由于$出现在replacement中时，表示对捕获组的反向引用，所以要对上面替换内容 
				 * 中的 $ 进行替换，让它们变成 "\$1000.00" 或 "\$1000000000.00" ，这样 
				 * 在下面使用 matcher.appendReplacement(sb, value) 进行替换时就不会把 
				 * $1 看成是对组的反向引用了，否则会使用子匹配项值amount 或 balance替换 $1 
				 * ，最后会得到错误结果： 
				 * 
				 * 尊敬的客户刘明你好！本次消费金额amount000.00，您帐户888888888上的余额 
				 * 为balance000000.00，欢迎下次光临！ 
				 * 
				 * 要把 $ 替换成 \$ ，则要使用 \\\\\\& 来替换，因为一个 \ 要使用 \\\\ 来进 
				 * 行替换，而一个 $ 要使用 \\$ 来进行替换，因 \ 与  $ 在作为替换内容时都属于 
				 * 特殊字符：$ 字符表示反向引用组，而 \ 字符又是用来转义 $ 字符的。 
				 */  
				value = value.replaceAll("\\\\", "\\\\\\\\");
				value = value.replaceAll("\\$", "\\\\\\$");
			}  
			/* 
			 * 经过上面的替换操作，现在的 value 中含有 $ 特殊字符的内容被换成了"\$1000.00" 
			 * 或 "\$1000000000.00" 了，最后得到下正确的结果： 
			 * 
			 * 尊敬的客户刘明你好！本次消费金额$1000.00，您帐户888888888上的 
			 * 余额为$1000000.00，欢迎下次光临！ 
			 * 
			 * 另外，我们在这里使用Matcher对象的appendReplacement()方法来进行替换操作，而 
			 * 不是使用String对象的replaceAll()或replaceFirst()方法来进行替换操作，因为 
			 * 它们都能只能进行一次性简单的替换操作，而且只能替换成一样的内容，而这里则是要求每 
			 * 一个匹配式的替换值都不同，所以就只能在循环里使用appendReplacement方式来进行逐 
			 * 个替换了。 
			 */  
			matcher.appendReplacement(sb, value);  
		}  
		//最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”  
		matcher.appendTail(sb);  
		return sb.toString();  
	}  
	public static void main(String args[]) throws Exception {  
		StringBuffer sb=new StringBuffer();
		System.out.println("\\100".replaceAll("\\\\", "\\\\\\\\"));
		System.out.println("$xx".replaceAll("(\\$)", "\\$1"));
		System.out.println("xxa.xx".replaceAll("(xx)(a)", "$1.html$2"));
		String s="<a href=\" /front/into/idea   \">企业理念</a></div><a href=\"/front/into/idea?name=sss&age=12\">其他</a></div>";
		String reg="/front/into/idea?name=sss&age=12";
		               // String reg="/front/into/idea";
		reg=reg.replaceAll("\\?", "\\\\?");
		reg=reg.replaceAll("\\&", "\\\\&");
		//System.out.println(s);
		//System.out.println(reg);
		System.out.println(s.replaceAll("[\"|\']\\s*"+reg+"\\s*[\"|\']", "\""+reg+".html\""));
		System.out.println(composeMessage1(s,"[\"\']([^\"']*)[\"\']", "XXXX"));
		System.out.println(s.replaceAll("/front/into/idea?name=sss&age=12", "\""+reg+".html\""));
		HashMap data = new HashMap();  
		String template = "尊敬的客户{customerName}你好！本次消费金额{amount}，"  
				+ "您帐户{accountNumber}上的余额为{balance}，欢迎下次光临！";  
		data.put("customerName", "刘明");  
		data.put("accountNumber", "\\100888888888");  
		data.put("balance", "$1000000.00");  
		data.put("amount", "$1000.00");  
		try {  
			System.out.println(format(template, data));  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
	}  
}
