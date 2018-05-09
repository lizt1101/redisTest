package redisSpring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.lzt.dao.CouponDao;
import com.lzt.dao.EmpDao;
import com.lzt.entity.Coupon;
import com.lzt.entity.Emp;
import com.lzt.service.EmpService;

import redis.clients.jedis.Jedis;

public class Test01 {

	private ApplicationContext ac;
	private EmpDao dao;
	private EmpService service;
	private SimpleCacheManager bean;
	private JedisConnectionFactory j;

	@Before
	public void init() {
		ac = new ClassPathXmlApplicationContext("SpringMVC.xml");
		dao = ac.getBean("empDao", EmpDao.class);
		service = ac.getBean("empService", EmpService.class);
		// bean = ac.getBean("cacheManager",SimpleCacheManager.class);
		//j = ac.getBean("JedisConnectionFactory", JedisConnectionFactory.class);
	}

	@Test
	public void testMYsql() {

	}

	// ≤‚ ‘¡¨Ω”
	@Test
	public void test() {
		System.out.println(j.getConnection());
	}

	@Test
	public void test1() {
		Emp emp = dao.getEmpList(7369);
		System.out.println(emp);
	}

	@Test
	public void test02() {
		Emp emp = service.getEmplist(7369);
		System.out.println(emp);
	}
	
	@Test
	public void test10() {
		List<Emp> empList = service.getList();
		for (Emp emp2 : empList) {
			System.out.println(emp2);
		}
		
	}
	
	@Test
	public void test11() {
		service.updateById(7369);
	}
	
	// string
	@Test
	public void test03() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set("name", "bar");
		String name = jedis.get("name");
		System.out.println(name);
		jedis.close();
	}

	// list
	@Test
	public void test04() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		/*jedis.lpush("myScore", "java");
		jedis.lpush("myScore", "redis");
		jedis.lpush("myScore", ".net");
		jedis.lpush("myScore", "php");*/
		List<String> list = jedis.lrange("CouponIdList", 0, 10);
		for (String str : list) {
			System.out.println(str);
		}
	}

	// set
	@Test
	public void test05() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.sadd("lzt_score", "java");
		jedis.sadd("lzt_score", "java");
		jedis.sadd("lzt_score", "redis");
		Set<String> set = jedis.smembers("lzt_score");
		for (String str : set) {
			System.out.println(str);
		}
	}

	// hash
	@Test
	public void test06() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("1", "red");
		hash.put("2", "yellow");
		jedis.hmset("color", hash);
		Map<String, String> resultMap = jedis.hgetAll("color");
		Iterator<Map.Entry<String, String>> it = resultMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
	}

	// sorted set
	@Test
	public void test07() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.zadd("animal", 2, "dog");
		jedis.zadd("animal", 1, "cat");
		Set<String> set = jedis.zrange("animal", 0, 10);
		for (String str : set) {
			System.out.println(str);
		}
	}

	// HyperLogLog
	@Test
	public void test08() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.pfadd("key", "hello");
		jedis.pfadd("key", "hi");
		Long i = jedis.pfcount("key");
		System.out.println(i);
	}

	
}
