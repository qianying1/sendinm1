package com.gzsendi.sendinm.controller;

import com.gzsendi.sendinm.core.redis.RedisUtil;
import com.gzsendi.sendinm.model.User;
import com.gzsendi.sendinm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource(name = "userServiceImpl")
    private UserService userService;
    @Resource(name = "redisUtil")
    private RedisUtil redisUtil;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user) {
        try {
            for (int i = 0; i < 10; i++) {
                redisUtil.set("hello" + i, "hello world" + i);
            }
            for (int i = 0; i < 10; i++) {
                String test = (String) redisUtil.get("hello" + i);
                logger.info("控制层设置hello" + i + "的值为：>>>>>>>>>>>>>>>>" + test);
            }
        } catch (Exception e) {
            logger.error("fail to write/read data in redis client!", e);
        }
        return userService.addUser(user);
    }

    /*@ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {

        return userService.findAllUser(pageNum, pageSize);
    }*/
}
