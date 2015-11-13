package per.cz.util.http.per;

import java.util.HashMap;
import java.util.Map;

public class Test {
	public static void main(String[] args) {  
		// TODO Auto-generated method stub  
		try {       
			HttpRequester request = new HttpRequester();  
			request.setDefaultContentEncoding("utf-8");  
			//HttpRespons hr = request.sendGet("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22nome%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");       
			 java.net.URLEncoder.encode("我爱","utf-8");
			Map<String,String> params=new HashMap<String,String>();
			params.put("q", java.net.URLEncoder.encode("select * from weather.forecast where woeid=23424948","utf-8"));
			params.put("format", "json");
			params.put("env", java.net.URLEncoder.encode("store://datatables.org/alltableswithkeys","utf-8"));
			HttpRespons hr= request.sendGet("https://query.yahooapis.com/v1/public/yql", params);

			System.out.println(hr.getContent());       

		} catch (Exception e) {       
			e.printStackTrace();       
		}   
	} 
}
