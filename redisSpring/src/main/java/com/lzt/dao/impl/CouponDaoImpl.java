package com.lzt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.lzt.dao.AbstractBaseRedisDao;
import com.lzt.dao.CouponDao;
import com.lzt.dto.CouponDto;
import com.lzt.entity.Coupon;


@Repository("couponDaoImpl")
@SuppressWarnings("unchecked")
public class CouponDaoImpl extends AbstractBaseRedisDao<String, Coupon> implements CouponDao {

	public boolean add(final Coupon coupon) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(String.valueOf(coupon.getId()));
				byte[] name = serializer.serialize(JSON.toJSONString(coupon));
				return connection.setNX(key, name);
			}
		});
		return result;
	}

	@SuppressWarnings("rawtypes")
	public void addCoupon(final CouponDto couponDto) throws InterruptedException, ExecutionException {
		Integer valint = getCacheObject("Coupon_" + couponDto.getCouponId());
		System.out.println("valint:"+valint);
		Integer userInfo = couponDto.getUserId();
		ExecutorService pool  = Executors.newCachedThreadPool();
		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		if (valint > 0) {
			final Integer val = valint-1;
			tasks.add(new Callable() {
				public Object call() throws Exception {
					return redisTemplate.execute(new SessionCallback() {
						public Object execute(RedisOperations operations) throws DataAccessException {
							System.out.println("进入");
							operations.watch("Coupon_" + couponDto.getCouponId());
							operations.multi();// 开启事务
							operations.opsForValue().set("Coupon_" + couponDto.getCouponId(),val);
							Object rs = operations.exec();
	                        System.out.println("set:"+val+" rs:"+rs);
	                        return rs;
						}
					});
				}
			});
		} else {
			System.out.println("失败");
		}
		List<Future<Object>> futures = pool.invokeAll(tasks);
        for(Future<Object> f:futures){
        	if(f.get()!= null){
        		System.out.println("成功!");
        		setZSetSing("successInfo",couponDto.getUserId());
        	}else{
        		System.out.println("失败!");
        	}
            System.out.println(f.get());
        }
        pool.shutdown();
        pool.awaitTermination(1000, TimeUnit.MILLISECONDS);

	}

}
