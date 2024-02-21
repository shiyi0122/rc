package com.hna.hka.archive.management.system.model.ExternalData;


public class PayOrder {

	private String orderId;               //订单ID
	private String orderNo;               //订单编号
	private String openTime;              //计时开始时间
	private String closeTime;             //计时结束时间
	private String deductTime;            //扣款时间
	private String orderStatus;           //订单状态
	private String amountUse;             //应付金额
	private String amountPay;             //实付金额
	private String userId;                //用户ID
	private String deviceId;              //机器人ID
	private String payStatus;             //支付状态
	private String payType;               //支付方式
	private String openSiteName;          //起点名称
	private String revertSiteName;        //归还点名称
	private String couponAmount;          //优惠金额
	private String shopId = "1";          //店铺ID
	private String shopName = "伴游机器人"; //店铺名称
	private String relationOrderId;       //关联订单Id
	private String latestPayAmount;       //本次支付金额
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getDeductTime() {
		return deductTime;
	}
	public void setDeductTime(String deductTime) {
		this.deductTime = deductTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getAmountUse() {
		return amountUse;
	}
	public void setAmountUse(String amountUse) {
		this.amountUse = amountUse;
	}
	public String getAmountPay() {
		return amountPay;
	}
	public void setAmountPay(String amountPay) {
		this.amountPay = amountPay;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOpenSiteName() {
		return openSiteName;
	}
	public void setOpenSiteName(String openSiteName) {
		this.openSiteName = openSiteName;
	}
	public String getRevertSiteName() {
		return revertSiteName;
	}
	public void setRevertSiteName(String revertSiteName) {
		this.revertSiteName = revertSiteName;
	}
	public String getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getRelationOrderId() {
		return relationOrderId;
	}
	public void setRelationOrderId(String relationOrderId) {
		this.relationOrderId = relationOrderId;
	}
	public String getLatestPayAmount() {
		return latestPayAmount;
	}
	public void setLatestPayAmount(String latestPayAmount) {
		this.latestPayAmount = latestPayAmount;
	}
}
