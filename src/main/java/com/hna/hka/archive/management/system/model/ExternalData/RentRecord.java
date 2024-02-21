package com.hna.hka.archive.management.system.model.ExternalData;

public class RentRecord {

	
	private String orderId;          //订单id
	private String useMinute;        //时长
	private String openTime;         //计时开始时间
	private String closeTime;        //计时结束时间
	private String rentRecordId="1"; //租赁id
	private String userId;           //用户id
	private String rentGoodsName="伴游机器人";    //商品名称
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUseMinute() {
		return useMinute;
	}
	public void setUseMinute(String useMinute) {
		this.useMinute = useMinute;
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
	public String getRentRecordId() {
		return rentRecordId;
	}
	public void setRentRecordId(String rentRecordId) {
		this.rentRecordId = rentRecordId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRentGoodsName() {
		return rentGoodsName;
	}
	public void setRentGoodsName(String rentGoodsName) {
		this.rentGoodsName = rentGoodsName;
	}
	
	
}
