package com.gzsendi.qhb.service.impl;

import com.github.pagehelper.PageHelper;
import com.gzsendi.qhb.mapper.UserMapper;
import com.gzsendi.qhb.model.User;
import com.gzsendi.qhb.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
@Resource(name = "userMapper")
    private UserMapper userMapper;
    public int addUser(User user){
        return userMapper.insert(user);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectAllUser();
    }
}
