package per.cz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author HJL
 *
 */
public class DateUtil {
	   public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	    /**
		  * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
		  * @param str 字符串
		  * @param format 日期格式
		  * @return 日期
		  * @throws java.text.ParseException
		  */
		public static Date str2Date(String str){
			if (null == str || "".equals(str)) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
			Date date = null;
			try {
				date = sdf.parse(str);
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
		 * @param str 字符串
		 * @param format 日期格式
		 * @return 日期
		 * @throws java.text.ParseException
		 */
		public static Date str2Date(String str,String format){
			if (null == str || "".equals(str)) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = null;
			try {
				date = sdf.parse(str);
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}

		 /** 日期转换为字符串
		  * @param date 日期
		  * @param format 日期格式 "yyyy-MM-dd HH:mm:ss"
		  * @return 字符串
		  */
		public static String date2Str(Date date) {
			if (null == date) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
			return sdf.format(date);
		}
		public static String date2Str(Date date,String format) {
			if (null == date||format==null||"".equals(format.trim())) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		
		/**
		 * 获取 start 到 end 中有多少天
		 * @param start
		 * @param end
		 * @return
		 */
		public static int getDays(Date start,Date end)
		{
			return (int) ((start.getTime()-end.getTime())/1000/60/60/24);
		}
		/**
		 * 获取日期所在日期的0时0秒时的 毫秒数
		 * @param d
		 * @return
		 */
		public static long getDate4long(Date d)
		{
			Calendar ca = Calendar.getInstance();
			ca.setTime(d);
			ca.set(Calendar.HOUR_OF_DAY,0);
			ca.set(Calendar.MINUTE,0);
			ca.set(Calendar.SECOND,0);
			ca.set(Calendar.MILLISECOND,0);
			return ca.getTimeInMillis();
		}
		/**
		 * 获取 start 到 end 中有多少天
		 * @param start
		 * @param end
		 * @return
		 */
		public static boolean  isToday(Date date)
		{
			return isSameDay(date,new Date());
		}
		/**
		 * 获取 start 到 end 中有多少天
		 * @param start
		 * @param end
		 * @return
		 */
		public static boolean  isSameDay(Date d1,Date d2)
		{
			if (d1 == null || d2 == null) {
	            throw new IllegalArgumentException("The date must not be null");
	        }
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(d1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(d2);
			return isSameDay(cal1, cal2);
		}
		 public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		        if (cal1 == null || cal2 == null) {
		            throw new IllegalArgumentException("The date must not be null");
		        }
		        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
		                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
		    }
		/**
		 * 获取 start 到 end中有多少 工作日
		 * @param start
		 * @param includeS
		 * @param end
		 * @param includesE
		 * @return
		 */
		public static int getWorkingDays(Date start,boolean includeS,Date end,boolean includesE)
		{
			int tag=0;
			start.setHours(0);
			start.setMinutes(0);
			start.setSeconds(0);
			if(!includeS)
				start.setDate(start.getDate()+1);
			if(!includesE)
				end.setDate(end.getDate()-1);
			while(start.before(end))
			{
				if(0<start.getDay()&&start.getDay()<6)
				{
					tag++;
				}
				start.setDate(start.getDate()+1);
			}
			return tag;
		}
	    public static Date add(Date date, int calendarField, int amount) {
	        if (date == null) {
	            throw new IllegalArgumentException("The date must not be null");
	        }
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(calendarField, amount);
	        return c.getTime();
	    }
	    
		/**
		 * data 的addDays天后 是哪天
		 * @param date
		 * @param addDays
		 * @return
		 */
		public static Date addDate(Date date, int addDays) {
			
			return add(date,Calendar.DATE,addDays);
		}
		public static void main(String[] args) {
			Date date = new Date();
			//date.setDate(date.getDate());
			System.out.println(add(date,Calendar.DATE,-1));
			System.out.println(addDate(date, -1));
			Calendar ca = Calendar.getInstance();
			System.out.println(DateUtil.isToday(date));
			System.out.println(date2Str(new Date(getDate4long(new Date()))));
			System.out.println(getDate4long(new Date()));
			System.out.println(str2Date("20150914000000", "yyyyMMddHHmmss").getTime());
//			Date start=DateUtil.str2Date("2014-11-15", "yyyy-MM-dd");
//			Date end=DateUtil.str2Date("2014-12-15", "yyyy-MM-dd");
//			while(start.before(end))
//			{
//				//System.out.println(DateUtil.date2Str(start));
//				start.setDate(start.getDate()+1);
//			}
			//System.out.println(getWorkingDays(DateUtil.str2Date("2014-11-30 23:59:59"), true, DateUtil.str2Date("2014-12-7 23:59:59"), true));
		}
}
