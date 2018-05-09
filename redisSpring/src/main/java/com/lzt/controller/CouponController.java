package com.lzt.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lzt.dao.impl.CouponDaoImpl;
import com.lzt.dto.CouponDto;


@Controller
@RequestMapping("/Coupon")
public class CouponController {
	
	@Resource
	private CouponDaoImpl couponDaoImpl;
	
	@RequestMapping(value="/test")
	public void test(){
		System.out.println(13);
		
	}
	
	@RequestMapping(value="/redis")
	@ResponseBody
	public String addWatch(@RequestBody String param,HttpServletRequest request,
			HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		map = JSON.parseObject(param);
		Integer count = (Integer) map.get("count");
		String watchKey = "Coupon_"+String.valueOf(map.get("couponId"));
		couponDaoImpl.setCacheObject(watchKey, count);
		Map<String,Object> resultmap = new HashMap<String,Object>();
		resultmap.put("msg", "success");
		return JSON.toJSONString(resultmap);
	}
	
	@RequestMapping(value="/addCoupon")
	public String addCoupon(@RequestBody String param,HttpServletRequest request,
			HttpServletResponse response) throws InterruptedException, ExecutionException{
		System.out.println("��ʼ��");
		ExecutorService executor = Executors.newFixedThreadPool(100);
		final CouponDto couponDto = JSON.parseObject(param,CouponDto.class);
		couponDaoImpl.addCoupon(couponDto);
		return param;
	}

}















