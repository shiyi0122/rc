package com.hna.hka.archive.management.system.model.ExternalData;

public class RefundOrderList {

	
	private String orderId;          //订单id
	private String refundTime;       //退款时间
	private String orderStatus;      //订单状态
	private String refundStatus;     //退款状态 1退款成功；0退款失败
	private String relateOrderNo;    //关联订单号
	private String amountRefund;     //退款金额
	private String userId;           //用户id
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getRelateOrderNo() {
		return relateOrderNo;
	}
	public void setRelateOrderNo(String relateOrderNo) {
		this.relateOrderNo = relateOrderNo;
	}
	public String getAmountRefund() {
		return amountRefund;
	}
	public void setAmountRefund(String amountRefund) {
		this.amountRefund = amountRefund;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
