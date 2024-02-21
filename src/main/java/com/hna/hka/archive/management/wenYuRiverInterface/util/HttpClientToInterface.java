package com.hna.hka.archive.management.wenYuRiverInterface.util;

import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.wenYuRiverInterface.model.WenYuRiver;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.wenYuRiverInterface.util
 * @ClassName: HttpClientToInterface
 * @Author: 郭凯
 * @Description: post请求并发送请求参数
 * @Date: 2021/5/18 17:19
 * @Version: 1.0
 */
public class HttpClientToInterface {

    /**
     * post请求
     * @param url
     * @param json
     * @return
     */
    public static WenYuRiver doPost(String url, JSONObject json){
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        postMethod.addRequestHeader("accept", "*/*");
        postMethod.addRequestHeader("connection", "Keep-Alive");
        //设置json格式传送
        postMethod.addRequestHeader("Content-Type", "application/json;charset=utf-8");
        //必须设置下面这个Header
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
        //添加请求参数
        postMethod.addParameter("access_token", json.getString("access_token"));

        String res = "";
        try {
            int code = httpClient.executeMethod(postMethod);
            if (code == 200){
                res = postMethod.getResponseBodyAsString();
                System.out.println(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(res,WenYuRiver.class);
    }

    public static void main(String[] args) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", "e32ab99d4ddba178fcb19e394f741b759284b8");
        WenYuRiver a = doPost("https://omp.wenyuriverpark.com/adminapi/login/getUserInfo?access_token=e32ab99d4ddba178f19e394f744a1b759284b8", jsonObject);
        System.out.println(a);
    }
}
