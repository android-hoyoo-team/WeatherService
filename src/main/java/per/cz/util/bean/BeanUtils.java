package per.cz.util.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {
	public static void transMap2Bean(Map<String, Object> map, Object obj) {  

		try {  
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  

			for (PropertyDescriptor property : propertyDescriptors) {  
				String key = property.getName();  

				if (map.containsKey(key)) {  
					Object value = map.get(key);  
					// 得到property对应的setter方法  
					Method setter = property.getWriteMethod();  
					setter.invoke(obj, value);  
				}  

			}  

		} catch (Exception e) {  
			System.out.println("transMap2Bean Error " + e);  
		}  

		return;  

	}  
	public static <T> T transMap2Bean(Map<String, Object> map,Class<T> cls) {  
		
		try { 
			T t=cls.newInstance();
			Field[] fs = cls.getDeclaredFields();
			for (Field f : fs) {
				String f_n = f.getName();
				if (map.containsKey(f_n)) {  
					Object value = map.get(f_n);  
					Field field=null;
					field = cls.getDeclaredField(f_n);
					field.setAccessible(true);
					field.set(t, value);
				}  
			}
			
			
			return t;  
		} catch (Exception e) {  
			System.out.println("transMap2Bean Error " + e);  
		}  
		return null;
		
	}  
	public static void main(String[] args) {
		Map m=new HashMap<String, Object>();
		m.put("res", "nmae");
//		Awards transMap2Bean = transMap2Bean(m,Awards.class);
//		System.out.println(transMap2Bean.getRes());
	}
}
