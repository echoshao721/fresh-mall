package com.fresh.mall.service.impl;

import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.exception.FreshMallExceptionEnum;
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

    @Override
    public void register(String userName, String password) throws FreshMallException {
        //check if username already exists
        User result = userMapper.selectByName(userName);
        if(result!=null){
            throw new FreshMallException(FreshMallExceptionEnum.NAME_EXISTED);
        }

        //write in database
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        int count = userMapper.insertSelective(user);//only username password so insertSelective
        if(count == 0){
            throw new FreshMallException(FreshMallExceptionEnum.INSERT_FAILED);
        }
    }
}
