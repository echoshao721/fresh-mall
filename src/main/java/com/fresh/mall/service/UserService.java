package com.fresh.mall.service;

import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.model.pojo.User;

public interface UserService {
    User getUser();

    void register(String userName, String password) throws FreshMallException;


    User login(String userName, String password) throws FreshMallException;

    void updateInformation(User user) throws FreshMallException;

    boolean checkAdminRole(User user);
}
