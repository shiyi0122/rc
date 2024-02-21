package com.hna.hka.archive.management.system.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
* @ClassName: WeChatInfo
* @Description: 获取微信yml配置路径
* @author 郭凯
* @date 2021年1月14日
* @version V1.0
 */
@Component
@Data
@ConfigurationProperties(prefix = "wxpay")
public class WeChatInfo {
	
	private String appid;//获取APPID
	
	private String mchid;//获取默认商户ID
	
	private String apiSecretkey;//获取微信小程序唯一密钥32位
	
	private String depositCallbackInterface;//获取回调接口
	
	private String wechatRefundRequest;//获取微信支付链接
	
	private String fileCertPath;//获取默认文件路径
	
	private String certPath;//获取文件路径
	
	private String deposit_fileCertPath;//押金独立账户证书地址
	
	private String deposit_weChat_mch_id;//押金独立账户商户ID

}
