package com.hna.hka.archive.management.system.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
* @ClassName: AlipayInfo
* @Description: 获取支付宝yml配置路径
* @author 郭凯
* @date 2021年1月13日
* @version V1.0
 */
@Component
@Data
@ConfigurationProperties(prefix = "alipay")
public class AlipayInfo {
	
	private String APP_ID;//支付宝小程序标识
	
	private String MERCHANT_PRIVATE_KEY;//私钥
	
	private String ALIPAY_PUBLIC_KEY;//公钥
	
	private String AES_SECRET_KEY;//加密方式
	
	private String CHARSET;//编码格式
	
	private String SIGN_TYPE;//签名格式
	
	private String FORMAT;//签名格式
	
	private String PAY_URL;//支付宝接口地址

}
