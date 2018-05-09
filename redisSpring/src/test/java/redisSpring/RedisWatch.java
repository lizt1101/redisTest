package redisSpring;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;

public class RedisWatch {
	
	  public static void main(String[] args) {
	        final String watchkeys = "watchkeys";
	        ExecutorService executor = Executors.newFixedThreadPool(20);

	        final Jedis jedis = new Jedis("127.0.0.1", 6379);
	        jedis.set(watchkeys, "0");// ����watchkeysΪ0
	        jedis.del("setsucc", "setfail");// ������ɹ��ģ���û�гɹ���
	        jedis.close();

	        for (int i = 0; i < 10000; i++) {// ����һ����ͬʱ����
	            executor.execute(new MyRunnable());
	        }
	        executor.shutdown();
	    }

}
