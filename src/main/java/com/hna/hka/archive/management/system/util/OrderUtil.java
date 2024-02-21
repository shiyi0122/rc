package com.hna.hka.archive.management.system.util;



import org.jdom.JDOMException;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class OrderUtil {
    public static SortedMap<Object, Object> closeOrder(String orderNumber, String appId, String mchId, String weChatSecret, String closeOrder) throws JDOMException, IOException {
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        //设置package订单参数
        packageParams.put("appid", appId);//微信小程序APPID
        packageParams.put("mch_id", mchId);//商户号
        packageParams.put("out_trade_no", orderNumber);//填入商家订单号
        packageParams.put("nonce_str", IdUtils.getSeqId());//随机字符串
        //获取sign
        String sign = PayCommonUtil.createSign("UTF-8", packageParams, weChatSecret);//最后这个是自己设置的32位密钥
        packageParams.put("sign", sign);//签名
        //转成XML
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        String resXml = HttpUtil.postData(closeOrder, requestXML);
        Map map = XMLUtils.doXMLParse(resXml);
        String return_code = (String) map.get("return_code");
        String result_code = (String) map.get("result_code");
        packageParams.put("return_code", return_code);
        packageParams.put("result_code", result_code);
        return packageParams;
    }
}
