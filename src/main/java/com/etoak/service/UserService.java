package com.etoak.service;

import com.etoak.bean.User;

public interface UserService {
    //添加用户
    int addUser(User user);
    //根据用户名查询用户信息
    User queryByName(String name);

}
