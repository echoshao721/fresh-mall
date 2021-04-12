package com.fresh.mall.service.impl;

import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.fresh.mall.model.dao.UserMapper;
import com.fresh.mall.model.pojo.User;
import com.fresh.mall.service.UserService;
import com.fresh.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

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
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);//only username password so insertSelective
        if(count == 0){
            throw new FreshMallException(FreshMallExceptionEnum.INSERT_FAILED);
        }
    }
    @Override
    public User login(String userName, String password) throws FreshMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName,md5Password);
        if (user == null){
            throw new FreshMallException(FreshMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }
}
