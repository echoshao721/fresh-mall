package com.fresh.mall.controller;


import com.fresh.mall.common.ApiRestResponse;
import com.fresh.mall.common.Constant;
import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.fresh.mall.model.pojo.User;
import com.fresh.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/test")
    @ResponseBody
    public User personalPage(){
        return userService.getUser();

    }
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName,
                                    @RequestParam("password") String password) throws FreshMallException {
        //isEmpty null | ""
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(FreshMallExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(FreshMallExceptionEnum.NEED_PASSWORD);
        }
        //length of password should be longer than 8
        if(password.length()<8){
            return ApiRestResponse.error(FreshMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName,password);
        return ApiRestResponse.success();
    }
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password, HttpSession session) throws FreshMallException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(FreshMallExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(FreshMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName,password);
        //do not save password
        user.setPassword(null);
        session.setAttribute(Constant.FRESH_MALL_USER,user);
        return ApiRestResponse.success(user);
    }
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session,@RequestParam String signature) throws FreshMallException {
        User currentUser= (User)session.getAttribute(Constant.FRESH_MALL_USER);
        if(currentUser==null){
            return ApiRestResponse.error(FreshMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.FRESH_MALL_USER);
        return ApiRestResponse.success();
    }

    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password, HttpSession session) throws FreshMallException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(FreshMallExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(FreshMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName,password);
        //check if user is admin
        if (userService.checkAdminRole(user)) {
            //admin user
            user.setPassword(null);
            session.setAttribute(Constant.FRESH_MALL_USER,user);
            return ApiRestResponse.success(user);
        }else{
            return ApiRestResponse.error(FreshMallExceptionEnum.NOT_ADMIN);
        }


    }

}
