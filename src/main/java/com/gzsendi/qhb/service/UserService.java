package com.gzsendi.qhb.service;

import com.gzsendi.qhb.model.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
