package com.fresh.mall.controller;

import com.fresh.mall.common.ApiRestResponse;
import com.fresh.mall.model.pojo.Product;
import com.fresh.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * front-end product controller
 */
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("product detail")
    @GetMapping("product/detail")
    public ApiRestResponse detail(@RequestParam Integer id){
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }
}
