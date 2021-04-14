package com.fresh.mall.controller;

import com.fresh.mall.common.ApiRestResponse;
import com.fresh.mall.common.Constant;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.fresh.mall.model.pojo.User;
import com.fresh.mall.model.request.AddCategoryReq;
import com.fresh.mall.service.CategoryService;
import com.fresh.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class CategoryController {

    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq){

        User currentUser = (User)session.getAttribute(Constant.FRESH_MALL_USER);
        if(currentUser == null){
            return ApiRestResponse.error(FreshMallExceptionEnum.NEED_LOGIN);
        }
        //ALSO need to check if current user is admin or not
        boolean adminRole = userService.checkAdminRole(currentUser);
        if(adminRole){
            //is admin, add new category
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }else{
            return ApiRestResponse.error(FreshMallExceptionEnum.NOT_ADMIN);
        }
    }
}
