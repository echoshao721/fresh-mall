package com.fresh.mall.controller;

import com.fresh.mall.common.ApiRestResponse;
import com.fresh.mall.common.Constant;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.fresh.mall.model.pojo.Category;
import com.fresh.mall.model.pojo.User;
import com.fresh.mall.model.request.AddCategoryReq;
import com.fresh.mall.model.request.UpdateCategoryReq;
import com.fresh.mall.model.vo.CategoryVO;
import com.fresh.mall.service.CategoryService;
import com.fresh.mall.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.service.ApiInfo;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @ApiOperation("back end add product category")
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
    @ApiOperation("Backend Update Category")
    @PostMapping("admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(@Valid @RequestBody UpdateCategoryReq updateCategoryReq,HttpSession session){
        User currentUser = (User)session.getAttribute(Constant.FRESH_MALL_USER);
        if(currentUser == null){
            return ApiRestResponse.error(FreshMallExceptionEnum.NEED_LOGIN);
        }
        //ALSO need to check if current user is admin or not
        boolean adminRole = userService.checkAdminRole(currentUser);
        if(adminRole){
            //is admin, add new category
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq,category);
            categoryService.update(category);
            return ApiRestResponse.success();
        }else{
            return ApiRestResponse.error(FreshMallExceptionEnum.NOT_ADMIN);
        }
    }
    @ApiOperation("Backend Delete Category")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id){
        categoryService.delete(id);
        return ApiRestResponse.success();
    }
    @ApiOperation("Backend(admin) Category list")
    @PostMapping("admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum,
                                                @RequestParam Integer pageSize){
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("Frontend Category list")
    @PostMapping("category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer(){
        List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(0);
        return ApiRestResponse.success(categoryVOList);
    }
}
