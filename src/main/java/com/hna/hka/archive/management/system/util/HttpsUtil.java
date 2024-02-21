package com.hna.hka.archive.management.system.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class HttpsUtil {

    /*请求url获取返回的内容*/
    public static String getReturn(HttpURLConnection connection) throws IOException {
        StringBuffer buffer = new StringBuffer();
        //将返回的输入流转换成字符串
        try(InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);){
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            String result = buffer.toString();
            return result;
        }
    }

    //post请求的方法重载
    public static String getReturn(HttpURLConnection connection,String jsr){
        try{
            StringBuffer buffer = new StringBuffer();
            byte[] bytes = jsr.getBytes();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();

            //将返回的输入流转换成字符串
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            String result = buffer.toString();
            return result;
        }catch (Exception e){
        	e.printStackTrace();
        }
        return null;
    }
    
    
    public static String HttpURL(Map<String, Object> modelMap) {
		String url = "https://dt-api.taiyuanguotou.cn/api/v1/income_statement/order_record/time_order?key=f682c242-90c4-4dc8-b9fa-1732ec4cc61c";
		String result = null;
        try {
			URL serverUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			conn.setDoOutput(Boolean.TRUE);
			conn.setDoInput(Boolean.TRUE);
			//请求方式是POST
			conn.setRequestMethod("POST");
			// Post 请求不能使用缓存
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-type", "application/json");
			//必须设置false，否则会自动redirect到重定向后的地址
			conn.setInstanceFollowRedirects(false);
			//建立连接
			conn.connect();
			//设置请求体
			//HashMap map=new HashMap();
			//key-value的形式设置请求参数
			//map.put("key","value");
			String s = JSONObject.toJSONString(modelMap);
			System.out.println("当前JSON对象数据"+s);
			//获取了返回值
			result = HttpsUtil.getReturn(conn,s);
			//如果返回值是标准的JSON字符串可以像我这样给他进行转换
			//JSONObject jsonObject = JSONObject.parseObject(result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result; 
		}
	}
}

