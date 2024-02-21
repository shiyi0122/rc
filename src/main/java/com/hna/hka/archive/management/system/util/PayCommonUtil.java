package com.hna.hka.archive.management.system.util;

import org.apache.commons.lang.RandomStringUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.hna.hka.archive.management.system.model.AlipayInfo;
import com.hna.hka.archive.management.system.model.WeChatInfo;

import java.io.IOException;
import java.util.*;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: PayCommonUtil
 * @Author: 郭凯
 * @Description: 微信支付退款接口
 * @Date: 2020/8/18 11:28
 * @Version: 1.0
 */
public class PayCommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(PayCommonUtil.class);


    /**
     * @Author 郭凯
     * @Description sign签名
     * @Date 11:31 2020/8/18
     * @Param 
     * @return 
    **/
    public static String createSign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = entry.getKey().toString();
            String v = entry.getValue().toString();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + API_KEY);
        String sign = MD5.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    /**
     * @Author 郭凯
     * @Description 将请求参数转换为xml格式的string
     * @Date 11:32 2020/8/18
     * @Param
     * @return
    **/
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = entry.getKey().toString();
            String v = entry.getValue().toString();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * @Author 郭凯
     * @Description 取出一个指定长度大小的随机正整数.
     * @Date 11:32 2020/8/18
     * @Param
     * @return
    **/
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
    * @Author 郭凯
    * @Description: 微信退款
    * @Title: sendWechatPayBackOrder
    * @date  2021年1月18日 上午10:15:57 
    * @param @param weChat_app_id
    * @param @param mchid
    * @param @param fileCertPath
    * @param @param secret
    * @param @param nonce_str
    * @param @param body
    * @param @param out_trade_no
    * @param @param total_fee
    * @param @param payback_fee
    * @param @param openid
    * @param @param spbill_create_ip
    * @param @param refund
    * @param @return
    * @param @throws IOException
    * @param @throws JDOMException
    * @return SortedMap<Object,Object>
    * @throws
     */
    public static SortedMap<Object, Object> sendWechatPayBackOrder(String weChat_app_id, String mchid, String fileCertPath, String secret,String nonce_str, String body, String out_trade_no, int total_fee, int payback_fee,
                                                                   String openid, String spbill_create_ip,String refund) throws IOException, JDOMException {
        String depositCallbackInterface = "";// 获取微信小程序APPID
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", weChat_app_id);
        packageParams.put("mch_id", mchid);
        packageParams.put("nonce_str", nonce_str);//时间戳
        //packageParams.put("body", body);//支付主体
        packageParams.put("out_trade_no", out_trade_no);//编号
        packageParams.put("out_refund_no", IdUtils.getSeqId() + "");//编号
        packageParams.put("total_fee", total_fee);// 订单金额
        packageParams.put("refund_fee", payback_fee);// 退款金额
        packageParams.put("notify_url", depositCallbackInterface);
        //获取sign
        String sign = PayCommonUtil.createSign("UTF-8", packageParams, secret);//最后这个是自己设置的32位密钥 API密钥商户号设置一般会用小程序的密钥串来设置
        packageParams.put("sign", sign);
        //转成XML
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        logger.info("*****************************微信接口传入原始xml--调用微信端退款接口**********************************");
        logger.info(requestXML);
        //得到含有prepay_id的XML
        String resXml = null;
        try {
            resXml = ClientCustomSSL.doRefund(refund, requestXML,fileCertPath,mchid);
            logger.info("*****************************微信接口返回原始xml--调用微信端退款接口**********************************");
            logger.info(resXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析XML存入Map
        Map map = XMLUtil.doXMLParse(resXml);
        SortedMap<Object, Object> packageP = new TreeMap<Object, Object>();
        String return_code = (String) map.get("return_code");
        String result_code = (String) map.get("result_code");
        String err_code_des = (String) map.get("err_code_des");
        String RANK = resXml;
        packageP.put("return_code", return_code);
        packageP.put("result_code", result_code);
        packageP.put("RANK", RANK);
        packageP.put("err_code_des", err_code_des);
        return packageP;
    }
    

    /**
    * @Author 郭凯
    * @Description: 支付宝押金退款
    * @Title: alipayRefund
    * @date  2021年1月13日 下午3:29:23 
    * @param @param alipayInfo
    * @param @param outTradeNo
    * @param @param depositPayAmount
    * @param @return
    * @return SortedMap<Object,Object>
    * @throws
     */
	public static SortedMap<String, String> alipayRefund(AlipayInfo alipayInfo, String tradeNo, String depositPay) {
		System.out.println("开始调用支付宝加密******************************************************");
	  	SortedMap<String, String> packageParams = new TreeMap<String, String>();
	  	//实例化客户端
    	AlipayClient alipayClient = new DefaultAlipayClient(alipayInfo.getPAY_URL(),alipayInfo.getAPP_ID(), alipayInfo.getMERCHANT_PRIVATE_KEY(), alipayInfo.getFORMAT(), alipayInfo.getCHARSET(), alipayInfo.getALIPAY_PUBLIC_KEY(), alipayInfo.getSIGN_TYPE());
    	//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
    	AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
    	refundModel.setTradeNo(tradeNo);
    	refundModel.setRefundAmount(depositPay);
    	refundModel.setOutRequestNo(RandomStringUtils.randomAlphanumeric(13));
    	refundModel.setRefundReason("商品退款");
    	//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
    	AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
    	request.setBizModel(refundModel);
    	try{
    		AlipayTradeRefundResponse response = alipayClient.execute(request);
    		if ("Y".equals(response.getFundChange()) && "10000".equals(response.getCode())) {
    			// 退款成功
    			packageParams.put("subMsg", response.getSubMsg());
    			packageParams.put("msg", response.getMsg());
    			packageParams.put("FundChange", response.getFundChange());
    			packageParams.put("code", response.getCode());
            } else {
                // 退款失败
    			packageParams.put("FundChange", response.getFundChange());
    			packageParams.put("subMsg", response.getSubMsg());
    			packageParams.put("code", response.getCode());
            }
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("支付宝退款错误！",e);
    	}    
    	return packageParams;    	
	}

	/**
	* @Author 郭凯
	* @Description: 微信独立账户退款
	* @Title: weChatRefund
	* @date  2021年1月14日 下午4:32:14 
	* @param @param weChatInfo
	* @param @param openid
	* @param @param outTradeNo
	* @param @param total_fee
	* @param @param payback_fee
	* @param @return
	* @param @throws IOException
	* @param @throws JDOMException
	* @return SortedMap<Object,Object>
	* @throws
	 */
	public static SortedMap<Object, Object> weChatRefund(WeChatInfo weChatInfo, String openid, String outTradeNo, int total_fee, int payback_fee) throws IOException, JDOMException{
		// TODO Auto-generated method stub
		String depositCallbackInterface = "";// 获取微信小程序APPID
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", weChatInfo.getAppid());
        packageParams.put("mch_id", weChatInfo.getDeposit_weChat_mch_id());
        packageParams.put("nonce_str", System.currentTimeMillis() + "");//时间戳
        packageParams.put("out_trade_no", outTradeNo);//编号
        packageParams.put("out_refund_no", IdUtils.getSeqId() + "");//编号
        packageParams.put("total_fee", total_fee);// 订单金额
        packageParams.put("refund_fee", payback_fee);// 退款金额
        packageParams.put("notify_url", depositCallbackInterface);// 退款金额-----
        //获取sign
        String sign = PayCommonUtil.createSign("UTF-8", packageParams, weChatInfo.getApiSecretkey());//最后这个是自己设置的32位密钥 API密钥商户号设置一般会用小程序的密钥串来设置
        packageParams.put("sign", sign);
        //转成XML
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        logger.info("*****************************微信接口传入原始xml--调用微信端退款接口**********************************");
        logger.info(requestXML);
        //得到含有prepay_id的XML
        String resXml = null;
        try {
            resXml = ClientCustomSSL.doRefund(weChatInfo.getWechatRefundRequest(), requestXML,weChatInfo.getDeposit_fileCertPath(),weChatInfo.getDeposit_weChat_mch_id());
            logger.info("*****************************微信接口返回原始xml--调用微信端退款接口**********************************");
            logger.info(resXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析XML存入Map
        Map map = XMLUtil.doXMLParse(resXml);
        SortedMap<Object, Object> packageP = new TreeMap<Object, Object>();
        String return_code = (String) map.get("return_code");
        String result_code = (String) map.get("result_code");
        String err_code_des = (String) map.get("err_code_des");
        String RANK = resXml;
        packageP.put("return_code", return_code);
        packageP.put("result_code", result_code);
        packageP.put("RANK", RANK);
        packageP.put("err_code_des", err_code_des);
        return packageP;
	}

	/**
	* @Author 郭凯
	* @Description: 支付宝订单退款
	* @Title: alipayOrderRefund
	* @date  2021年1月21日 下午12:44:00 
	* @param @param alipayInfo
	* @param @param orderNumber
	* @param @param payMoney
	* @param @return
	* @return SortedMap<String,String>
	* @throws
	 */
	public static SortedMap<String, String> alipayOrderRefund(AlipayInfo alipayInfo, String orderNumber, String payMoney) {
		// TODO Auto-generated method stub
		System.out.println("开始调用支付宝加密******************************************************");
	  	SortedMap<String, String> packageParams = new TreeMap<String, String>();
	  	//实例化客户端
    	AlipayClient alipayClient = new DefaultAlipayClient(alipayInfo.getPAY_URL(),alipayInfo.getAPP_ID(), alipayInfo.getMERCHANT_PRIVATE_KEY(), alipayInfo.getFORMAT(), alipayInfo.getCHARSET(), alipayInfo.getALIPAY_PUBLIC_KEY(), alipayInfo.getSIGN_TYPE());
    	//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
    	AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
    	refundModel.setOutTradeNo(orderNumber);
    	refundModel.setRefundAmount(payMoney);
    	refundModel.setOutRequestNo(RandomStringUtils.randomAlphanumeric(13));
    	refundModel.setRefundReason("商品退款");
    	//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
    	AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
    	request.setBizModel(refundModel);
    	try{
    		AlipayTradeRefundResponse response = alipayClient.execute(request);
    		if ("Y".equals(response.getFundChange()) && "10000".equals(response.getCode())) {
    			// 退款成功
    			packageParams.put("subMsg", response.getSubMsg());
    			packageParams.put("msg", response.getMsg());
    			packageParams.put("FundChange", response.getFundChange());
    			packageParams.put("code", response.getCode());
            } else {
                // 退款失败
    			packageParams.put("FundChange", response.getFundChange());
    			packageParams.put("subMsg", response.getSubMsg());
    			packageParams.put("code", response.getCode());
            }
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("支付宝退款错误！",e);
    	}    
    	return packageParams;    	
	}


    /**
     * @Author zhang
     * @Description: 微信查询订单
     * @Title: alipayOrderRefund
     * @date
     * @param @param alipayInfo
     * @param @param orderNumber
     * @param @param payMoney
     * @param @return
     * @return SortedMap<String,String>
     * @throws
     */

//    public static SortedMap<Object, Object> sendWechatPayBackOrder(String weChat_app_id, String mchid, String fileCertPath, String secret,String nonce_str, String body, String out_trade_no, int total_fee, int payback_fee,
//                                                                   String openid, String spbill_create_ip,String refund) throws IOException, JDOMException {
//
//
//
    }
