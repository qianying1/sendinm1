package com.gzsendi.sendinm.service;

import com.gzsendi.sendinm.model.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
