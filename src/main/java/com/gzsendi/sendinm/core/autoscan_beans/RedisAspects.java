package com.gzsendi.sendinm.core.autoscan_beans;

import com.gzsendi.sendinm.core.redis.RedisUtil;
import com.gzsendi.sendinm.core.util.RedisServiceOpen;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;

import javax.annotation.Resource;

/**
 * Created by qianying on 2018/6/3.
 */
@Aspect
@Configuration
public class RedisAspects {
    private Logger logger = LoggerFactory.getLogger(RedisAspects.class);

    @Resource(name = "redisServiceOpen")
    private RedisServiceOpen redisServiceOpen;
    @Resource(name = "redisUtil")
    private RedisUtil redisUtil;
    //从application.properties中获得以下参数
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${custom.test-key}")
    private String redisTestKey;
    @Value("${custom.test-val}")
    private String redisTestVal;

    /*
    * 定义一个切入点
    */
    // @Pointcut("execution (* findById*(..))")
    /*@Pointcut("execution(* com.gzsendi.sendinm.core.redis.RedisUtil.*(..))")
    public void excudeService(){

    }*/
    /*
     * 通过连接点切入
     */
    @Before("execution(* com.gzsendi.sendinm.core.redis.RedisUtil.set*(..))")
    public void testRedisIsInUsed() {
        logger.info("redis插入数据前置操作>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        try {
            redisUtil.initOpertions();
        } catch (Exception ex) {
            boolean isProblemDeal = false;
            if (ex instanceof RedisConnectionFailureException) {
                redisServiceOpen.openRedisService();
                isProblemDeal = true;
            }
            if (isProblemDeal)
                logger.warn("进行redis缓存插入数据操作时出现错误，但已经过处理>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            else
                logger.error("进行redis缓存插入数据操作时出现错误，还没有进行处理>>>>>>>>>>>>>>>>>>>>>>>>>", ex);
        }
    }

    /*@Around("excudeService()")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint){
        System.err.println ("切面执行了。。。。");
        try {
            Thing thing = (Thing) thisJoinPoint.proceed ();
            thing.setName (thing.getName () + "=========");
            return thing;
        } catch (Throwable e) {
            e.printStackTrace ();
        }
        return null;
    }*/

    @AfterThrowing(throwing = "ex"
            , pointcut = "execution(* com.gzsendi.sendinm.core.redis.RedisUtil.*(..))")
    public void openRedisService(Throwable ex) {
        logger.info("redis操作抛出异常>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        boolean isProblemDeal = false;
        if (ex instanceof RedisConnectionFailureException) {
            logger.warn("redis服务未启动，现正启动redis服务>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            redisServiceOpen.openRedisService();
            isProblemDeal = true;
        }
        if (isProblemDeal)
            logger.warn("进行redis缓存操作时出现错误，但已经过处理>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        else
            logger.error("进行redis缓存操作时出现错误，还没有进行处理>>>>>>>>>>>>>>>>>>>>>>>>>", ex);
    }
}
