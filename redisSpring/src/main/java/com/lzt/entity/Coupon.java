package com.lzt.entity;

import java.io.Serializable;

public class Coupon implements Serializable{
	
	private Integer id;
	private Integer couponId;
	private Integer count;
	
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", couponId=" + couponId + ", count=" + count + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	

}
