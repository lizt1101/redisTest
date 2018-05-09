package com.lzt.dao;

import java.util.concurrent.ExecutionException;

import com.lzt.dto.CouponDto;
import com.lzt.entity.Coupon;

public interface CouponDao {

	boolean add(Coupon coupon);

	/*public <T> T get(final String keyId, Class<T> clazz);

	public <T> ValueOperations<String, T> setCacheObject(String key, T value);
	
	public <T> T getCacheObject(String key);*/
	
	public void addCoupon( CouponDto couponDto)throws InterruptedException, ExecutionException ;

}
