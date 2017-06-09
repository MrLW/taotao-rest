package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.rest.dao.impl.JedisClientCluster;
import com.taotao.rest.dao.impl.JedisClientPool;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
// 测试出现连接超时，原因是：防火墙没关
public class JedisTest {
	// 单个jedis
	@Test
	public void testJedisSingle(){
		//创建jedis对象
		Jedis jedis = new Jedis("192.168.154.129", 6379) ;
		String setResult = jedis.set("test", " i am jedis");
		System.out.println("set:" + setResult);
		String getResult = jedis.get("test");
		System.out.println("get:" + getResult);
		jedis.close(); 
	}
	
	// jedis连接池
	@Test
	public void testJedisPool(){
		//创建连接池对象
		JedisPool jedisPool = new JedisPool("192.168.154.129", 6379); 
		// 获取连接对象
		Jedis jedis = jedisPool.getResource() ;
		jedis.set("name", "李文") ;
		String result = jedis.get("name");
		System.out.println("name:" + result); 
		// jedis使用掉一定要关掉
		jedis.close(); 
	}
	
	// jedis 集群
	@Test
	public void testJedisCluster(){
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.154.129", 7001));
		nodes.add(new HostAndPort("192.168.154.129", 7002));
		nodes.add(new HostAndPort("192.168.154.129", 7003));
		nodes.add(new HostAndPort("192.168.154.129", 7004));
		nodes.add(new HostAndPort("192.168.154.129", 7005));
		nodes.add(new HostAndPort("192.168.154.129", 7006));
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("age", 21 + "");
		String age = cluster.get("age");
		System.out.println("age:" + age );
	}
	// spring 测试
	@Test
	public void testJedisPoolSpring(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		JedisClientPool jedisClientPool = ac.getBean(JedisClientPool.class);
		System.out.println("jedisClientPool:" + jedisClientPool); // success
	}
	
	@Test
	public void testJedisClusterSpring(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		JedisClientCluster jedisClientCluster = ac.getBean(JedisClientCluster.class);
		System.out.println("jedisClientCluster:" + jedisClientCluster);
	}
	
}
