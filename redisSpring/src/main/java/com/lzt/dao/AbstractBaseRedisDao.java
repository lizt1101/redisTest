package com.lzt.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.JSON;
@SuppressWarnings("unchecked")
public abstract class AbstractBaseRedisDao<K,V> {

	@Autowired
	protected RedisTemplate<K, V> redisTemplate;

	/**
	 * 设置redisTemplate
	 *
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取 RedisSerializer <br>
	 * ------------------------------<br>
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * 通过key获取json字符串，转化为对象 <br>
	 * ------------------------------<br>
	 *
	 * @param keyId
	 * @return
	 */
	public <T> T get(final String keyId, final Class<T> clazz) {
		@SuppressWarnings("unchecked")
		T result = redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(keyId);
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				String CString = serializer.deserialize(value);
				T c = JSON.parseObject(CString, clazz);
				return c;
			}
		});
		return result;
	}

	/**
	 * 添加一个key
	 */
	public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
		ValueOperations<String, T> operation = (ValueOperations<String, T>) redisTemplate.opsForValue();
		operation.set(key, value);
		return operation;
	}
	/**
	 * 获取这个key值
	 * @param key
	 * @return
	 */
	public <T> T getCacheObject(String key) {
		ValueOperations<String, T> operation = (ValueOperations<String, T>) redisTemplate.opsForValue();
		return operation.get(key);
	}

	/**
	 * 添加一个hash集合
	 */
	public <T> HashOperations<String, T, T> setHash(String key,Map<T,T> map){
		HashOperations<String, T, T>  hash =
				(HashOperations<String, T, T>)
						redisTemplate.opsForHash();
		hash.putAll(key,map);
		return hash;
	}
	/**
	 * 获取一个map
	 */
	public <T> Map<T,T> getHash(String key){
		HashOperations<String,T,T>  hash =
				(HashOperations<String,T,T>)
						redisTemplate.opsForHash();
		Map<T,T> map = hash.entries(key);
		return map;

	}
	/**
	 * 添加一个list
	 */
	public <T> ListOperations<String, T> setList(String key,List<T> list){
		ListOperations<String, T> List = (ListOperations<String, T>) redisTemplate.opsForList();
		for (T t : list) {
			List.rightPush(key,t);
		}
		return List;

	}

	/**
	 * 获取一个list
	 */
	public <T> List<T> getList(String key){
		ListOperations<String, T> List = (ListOperations<String, T>) redisTemplate.opsForList();
		return  List.range(key, 0, -1);
	}

	/**
	 * 添加一个set
	 */
	public <T> SetOperations<String, T> setSet(String key,Set<T> sets){
		SetOperations<String, T> set = (SetOperations<String, T>) redisTemplate.opsForSet();
		for (T t : sets) {
			set.add(key, t);
		}
		return set;
	}
	/**
	 * 获取一个get
	 * @param key
	 * @return
	 */
	public <T> Set<T> getSets(String key){
		SetOperations<String, T> set = (SetOperations<String, T>) redisTemplate.opsForSet();
		return set.members(key);
	}

	/**
	 * 添加有序集合,批量
	 */
	public <T> ZSetOperations<String, T> setZSet(String key,Set<T> sets){
		ZSetOperations<String, T> zset = (ZSetOperations<String, T>) redisTemplate.opsForZSet();
		for (T t : sets) {
			zset.add(key, t,1);
		}
		return zset;
	}
	//单个
	public <T> ZSetOperations<String, T> setZSetSing(String key,T t){
		ZSetOperations<String, T> zset = (ZSetOperations<String, T>) redisTemplate.opsForZSet();
		zset.add(key, t,1);
		return zset;
	}

	public <T> Set<T> getZSets(String key){
		ZSetOperations<String, T> zset = (ZSetOperations<String, T>) redisTemplate.opsForZSet();
		return zset.rangeByScore(key, 0, 2);
	}


}














