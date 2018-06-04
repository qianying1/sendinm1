package com.gzsendi.sendinm.core.redis;

/**
 * Created by qianying on 2018/6/3.
 */

import com.gzsendi.sendinm.core.util.RedisServiceOpen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存工具
 */
@Component("redisUtil")
public class RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Resource(name = "redisTemplate")
    private RedisTemplate<Serializable, Object> redisTemplate;
    @Resource(name = "redisServiceOpen")
    private RedisServiceOpen redisServiceOpen;

    ValueOperations<Serializable, Object> operations;

    /**
     * 打开redis服务
     */
    /*private RedisUtil() {
//        openRedisService();
        operations = redisTemplate.opsForValue();
    }*/
    public void initOpertions() throws Exception {
        operations = redisTemplate.opsForValue();
    }

    /**
     * 插入数据
     *
     * @param key
     * @param val
     */
    private void insert(String key, Object val) {
        try {
            operations = redisTemplate.opsForValue();
            operations.set(key, val);
        } catch (RedisConnectionFailureException e) {
            redisServiceOpen.openRedisService();
            logger.warn("redis服务未开启，但已进行启动操作");
            operations.set(key, val);
        } catch (Exception e) {
            logger.error("redis服务器操作错误！>>>>>>>>>>>>>>>>>>>>", e);
        }
    }

    /**
     * 写入或更新缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        logger.info("正在设置键：>>>>>>>>>" + key + " 值为：>>>>>>>>>>>>>>>" + value);
        boolean result;
        operations = redisTemplate.opsForValue();
        insert(key, value);
        result = true;
        return result;
    }


    /**
     * 写入缓存
     * 设置失效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        operations = redisTemplate
                .opsForValue();
        insert(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        result = true;
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        operations = redisTemplate
                .opsForValue();
        try {
            result = operations.get(key);
        } catch (RedisConnectionFailureException e) {
            redisServiceOpen.openRedisService();
            logger.warn("redis服务未开启，但已进行启动操作");
            result = operations.get(key);
        } catch (Exception e) {
            logger.error("redis服务器操作错误！>>>>>>>>>>>>>>>>>>>>", e);
        }
        return result;
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) throws Exception {
        if (exists(key)) {
            try {
                redisTemplate.delete(key);
            } catch (RedisConnectionFailureException e) {
                redisServiceOpen.openRedisService();
                logger.warn("redis服务未开启，但已进行启动操作");
                redisTemplate.delete(key);
            } catch (Exception e) {
                logger.error("redis服务器操作错误！>>>>>>>>>>>>>>>>>>>>", e);
            }
        }
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            try {
                remove(key);
            } catch (RedisConnectionFailureException e) {
                redisServiceOpen.openRedisService();
                logger.warn("redis服务未开启，但已进行启动操作");
                try {
                    remove(key);
                } catch (Exception e2) {
                    logger.error("批量删除对应的value时出现错误，且经过启动redis服务后仍未能处理该问题!>>>>>>>>>>>>>>>>>>>>>>>", e2);
                }
            } catch (Exception e) {
                logger.error("批量删除对应的value时出现错误，未处理>>>>>>>>>>>>>>>>>>>>", e);
            }
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern 正则表达式
     */
    public void removePattern(final String pattern) throws Exception {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) throws Exception {
        return redisTemplate.hasKey(key);
    }

    public void setRedisTemplate(
            RedisTemplate<Serializable, Object> redisTemplate) throws Exception {
        this.redisTemplate = redisTemplate;
    }


}
