package com.jimo.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.jimo.entity.KillOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by root on 17-5-30.
 */
public class RedisDao {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        this.jedisPool = new JedisPool(ip, port);
    }

    private RuntimeSchema<KillOne> schema = RuntimeSchema.createFrom(KillOne.class);

    //查找缓存，返回对象
    public KillOne getKillOne(long killId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = "killId:" + killId;
            //get --> byte[] --> 反序列化 --> Object(KillOne)
            byte[] bytes = jedis.get(key.getBytes());
            //不为空则获取
            if (bytes != null) {
                //先new个空对象
                KillOne killOne = schema.newMessage();
                //反序列化填充数据
                ProtostuffIOUtil.mergeFrom(bytes, killOne, schema);
                return killOne;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    //存入缓存
    public String putKillOne(KillOne killOne) {
        //Object-->序列化-->bytes
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = "killId:" + killOne.getKillId();
            byte[] bytes = ProtostuffIOUtil.toByteArray(killOne, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            //超时缓存
            int exp = 60 * 60;//1 hours
            return jedis.setex(key.getBytes(), exp, bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return "no";
    }
}
