package com.fresh.mall.service.impl;

import com.fresh.mall.model.dao.UserMapper;
import com.fresh.mall.model.pojo.User;
import com.fresh.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser(){
        return userMapper.selectByPrimaryKey(2);
    }
}
