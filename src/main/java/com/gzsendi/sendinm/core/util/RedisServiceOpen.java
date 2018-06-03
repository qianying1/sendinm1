package com.gzsendi.sendinm.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by qianying on 2018/6/3.
 */
@Component("redisServiceOpen")
@Configuration
public class RedisServiceOpen {
    private static Logger logger = LoggerFactory.getLogger(RedisServiceOpen.class);
    @Resource(name = "redisTemplate")
    private RedisTemplate<Serializable, Object> redisTemplate;
    /**
     * redis服务位置
     */
    @Value("${custom.redis-service-open-command}")
    private String redisServiceOpenCommand;

    /**
     * 启动redis服务
     */
    public void openRedisService() {
        String command = "d:\\redis\\redis-server d:\\redis\\redis.windows.conf";
//        String command = getOpenRedisServiceCommand();
        if (redisServiceOpenCommand == null || redisServiceOpenCommand.trim().equals(""))
            redisServiceOpenCommand = command;
        logger.info("已经安装的redis服务命令为>>>>>>>>>>>>>>>: " + redisServiceOpenCommand);
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(redisServiceOpenCommand);
        } catch (IOException e) {
            logger.error("打开redis服务失败>>>>>>>>>>>>>>>>>>>>>>>", e);
            return;
        }
    }

    /**
     * 写入或更新缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) throws Exception {
        boolean result = false;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        result = true;
        return result;
    }

    public String getRedisServiceOpenCommand() {
        return redisServiceOpenCommand;
    }

    //    @Value("#{redisConfig['redis.serviceCommand']}")
    public void setRedisServiceOpenCommand(String redisServiceOpenCommand) {
        this.redisServiceOpenCommand = redisServiceOpenCommand;
    }

    public static void main(String[] args) {
//        openRedisService();

        Runtime runtime = Runtime.getRuntime();

        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");

        System.out.println("连接成功");
        //查看服务是否运行
//        System.out.println("服务正在运行: "+jedis.ping());


        /*String command="ipconfig -all";
        String s = "IPv4";
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            Process process = runtime.exec(command);
            BufferedReader bufferedReader = new BufferedReader
                    (new InputStreamReader(process.getInputStream()));

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
                if (line.contains(s)) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
