
import com.alibaba.fastjson.JSON;
import com.lzt.dao.EmpDao;
import com.lzt.domain.Emp;
import com.lzt.service.EmpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title
 * @Description
 * @Author:lizitao
 * @Create 2018/5/7
 * @Version 1.0
 * @Copyright:2016 www.jointem.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:SpringMVC.xml"})
public class Test01 {

    @Resource
    private JedisConnectionFactory jedisConnectionFactory;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /*private ApplicationContext ac;
     private JedisConnectionFactory j;
    @Before
    public void init() {
        ac = new ClassPathXmlApplicationContext("SpringMVC.xml");
        // bean = ac.getBean("cacheManager",SimpleCacheManager.class);
        //j = ac.getBean("JedisConnectionFactory", JedisConnectionFactory.class);
        j = ac.getBean("jedisConnectionFactory", JedisConnectionFactory.class);
    }*/

    /**
     * 测试连接
     */
    @Test
    public void test01(){
        System.out.println(jedisConnectionFactory.getConnection());
    }


    @Test
    public void testredisTemplate(){
       // redisTemplate.opsForValue().set("test", "lizitao");
        System.out.println(redisTemplate.opsForValue().get("test"));
        System.out.println("---------------");
        System.out.println(redisTemplate.opsForValue().size("test")); //14
    }

    @Test
    public void testStringTemplate(){
        //stringRedisTemplate.opsForValue().set("test01","lizitao");
        System.out.println(stringRedisTemplate.opsForValue().get("test01"));
        System.out.println("---------------");
        System.out.println(stringRedisTemplate.opsForValue().size("test01")); //7
    }


    @Test
    public void testList1(){
        List<String> list = new ArrayList<>();
        list.add("小明");
        list.add("小红");
        list.add("小军");
        redisTemplate.opsForList().leftPushAll("list",list);
        System.out.println(redisTemplate.opsForList().range("list",0,-1));
    }

    @Test
    public void testList2(){
       /* List<String> list = new ArrayList<>();
        list.add("小明");
        list.add("小红");
        list.add("小军");
        stringRedisTemplate.opsForList().leftPushAll("stringList",list);*/
        System.out.println(stringRedisTemplate.opsForList().range("stringList",0,-1));
        System.out.println("---------------");
        //插入数据后返回列表的长度
        System.out.println(stringRedisTemplate.opsForList().leftPush("stringList","呵呵"));
    }

    //添加hash
    @Test
    public void testStringMap(){
      /*  stringRedisTemplate.opsForHash().put("redisHash","name","lizitao");
        stringRedisTemplate.opsForHash().put("redisHash","age","23");
        stringRedisTemplate.opsForHash().put("redisHash","class","6");*/
        System.out.println(stringRedisTemplate.opsForHash().entries("redisHash"));
        System.out.println("-------获取指定的hash值--------");
        System.out.println(stringRedisTemplate.opsForHash().get("redisHash","name"));
        System.out.println("-------获取多个指定的hash值--------");
        List<Object> kes = new ArrayList<>();
        kes.add("name");
        kes.add("age");
        System.out.println(stringRedisTemplate.opsForHash().multiGet("redisHash",kes));
        System.out.println("-------获取key--------");
        System.out.println(stringRedisTemplate.opsForHash().keys("redisHash"));
        System.out.println("-------获取hash大小个数--------");
        System.out.println(stringRedisTemplate.opsForHash().size("redisHash"));
    }

    @Test
    public void testObject(){
        Emp emp = new Emp();
        emp.setEmpno(1234);
        emp.setEname("lizitao");
        emp.setJob("kaifa");
        emp.setMgr(7782);
        emp.setHiredate(new Date());
        emp.setSal(888.0);
        emp.setComm(200.0);
        emp.setDeptno(10);
        stringRedisTemplate.opsForValue().set("1234", JSON.toJSONString(emp));
    }

    @Autowired
    private EmpDao empDao;

    @Test
    public void testData(){
        System.out.println(empDao.getList());
    }

    @Autowired
    private EmpService empService;

    @Test
    public void save(){
        Emp emp = new Emp();
        emp.setEmpno(1236);
        emp.setEname("lizitao");
        emp.setJob("kaifa");
        emp.setMgr(7782);
        emp.setHiredate(new Date());
        emp.setSal(888.0);
        emp.setComm(200.0);
        emp.setDeptno(10);
        empService.saveEmp(emp);
    }

    @Test
    public void testService(){
        Emp emp = empService.getEmp(1236);
        System.out.println(emp.toString());
    }

    @Test
    public void testDelete(){
        empService.deleteEmp(1236);
    }




}
