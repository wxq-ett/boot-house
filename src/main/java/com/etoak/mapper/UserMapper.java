package com.etoak.mapper;

import com.etoak.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    //添加用户
    int addUser(User user);

    User queryByName(@Param("name")String name);

}
