package cn.clate.kezhan.utils.factories;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisFactory {
    public static Jedis get(){
        Jedis jedis = new Jedis("http://95.163.194.157", 6379);
        jedis.auth("kezhan");
        return jedis;
    }

    public static void main(String[] args) {
        Jedis jedis = get();
        jedis.set("xxx", "yyy");
    }
}
