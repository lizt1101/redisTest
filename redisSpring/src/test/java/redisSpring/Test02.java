package redisSpring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.lzt.dao.impl.CouponDaoImpl;
import com.lzt.entity.Coupon;

@ContextConfiguration(locations = { "classpath*:SpringMVC.xml" })
public class Test02 extends AbstractJUnit4SpringContextTests {

	@Resource
	private CouponDaoImpl cDao;

	@Test
	public void test09() {
		Coupon coupon = new Coupon();
		coupon.setId(3);
		coupon.setCouponId(124);
		coupon.setCount(3);
		boolean result = cDao.add(coupon);
		System.out.println(result);
	}
	@Test
	public void test02(){
		Coupon coupon = cDao.get("3",Coupon.class);
		System.out.println(coupon.toString());
	}
	//≤‚ ‘–¥»Î◊÷∑˚¥ÆµΩredis
	@Test
	public void test03(){
		cDao.setCacheObject("mykey", "testString1");
	}
	//≤‚ ‘∂¡»°
	@Test
	public void test04(){
		Integer value = cDao.getCacheObject("Coupon_123");
		System.out.println(value);
	}

	//≤‚ ‘ÃÌº”map
	@Test
	public void test05(){
		Map<Object,Object> map = new HashMap<Object, Object>();
		map.put("test01", 01);
		map.put("test02", 02);
		cDao.setHash("myMap1",map);
	}

	@Test
	public void test06(){
		Map<Object,Object> map= cDao.getHash("myMap1");
		for (Entry<Object, Object> entry : map.entrySet()) {
			String key = (String) entry.getKey();
			String value = entry.getValue().toString();
			System.out.println("key =" + key + " value = " + value);
		}
	}

	@Test
	public void test07(){
		List<Object> list = new ArrayList<Object>();
		list.add(1);
		list.add(2);
		cDao.setList("myList", list);
	}

	@Test
	public void test08(){
		List<Object> List = cDao.getList("myList");
		for (Object object : List) {
			System.out.println(object);
		}
	}

	@Test
	public void test10(){
		Set<Object> set= new HashSet<Object>();
		set.add(1);
		set.add(2);
		cDao.setSet("mySet", set);
	}

	@Test
	public void test11(){
		Set<Object> set= cDao.getSets("mySet");
		for (Object object : set) {
			System.out.println(object);
		}
	}

	@Test
	public void test12(){
		Set<Object> set= new HashSet<Object>();
		set.add(11);
		set.add(12);
		cDao.setZSet("myZSet", set);
	}

	@Test
	public void test13(){
		Set<Object> set = cDao.getZSets("myZSet");
		for (Object object : set) {
			System.out.println(object);
		}
	}





}




