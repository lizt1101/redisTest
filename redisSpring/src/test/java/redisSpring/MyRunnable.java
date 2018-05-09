package redisSpring;

import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class MyRunnable implements Runnable {

	String watchkeys = "watchkeys";// ����keys
    Jedis jedis = new Jedis("127.0.0.1", 6379);

    public MyRunnable() {
    
    }

    public void run() {
        try {
            jedis.watch(watchkeys);// watchkeys

            String val = jedis.get(watchkeys);
            int valint = Integer.valueOf(val);
            String userifo = UUID.randomUUID().toString();
            if (valint < 3) {
            	Thread.sleep(10000);
                Transaction tx = jedis.multi();// ��������
                
                tx.incr("watchkeys");
                
                List<Object> list = tx.exec();// �ύ���������ʱwatchkeys���Ķ��ˣ��򷵻�null
                if (list != null) {
                    System.out.println("�û���" + userifo + "�����ɹ�����ǰ�����ɹ�����:"
                            + (valint + 1));
                    /* �����ɹ�ҵ���߼� */
                    jedis.sadd("setsucc", userifo);
                } else {
                    System.out.println("��**����" + userifo + "����ʧ��");
                    /* ����ʧ��ҵ���߼� */
                    jedis.sadd("setfail", userifo);
                }

            } else {
                System.out.println("�û���" + userifo + "����ʧ��");
                jedis.sadd("setfail", userifo);
                // Thread.sleep(500);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }

}
