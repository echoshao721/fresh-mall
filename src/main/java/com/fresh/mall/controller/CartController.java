package com.fresh.mall.controller;

import com.fresh.mall.common.ApiRestResponse;
import com.fresh.mall.filter.UserFilter;
import com.fresh.mall.model.vo.CartVO;
import com.fresh.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @ApiOperation("cart list")
    @GetMapping("/list")
    public ApiRestResponse list(){

        List<CartVO> cartList = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(cartList);
    }


    @ApiOperation("add product to cart")
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count){

       List<CartVO> cartVOList = cartService.add(UserFilter.currentUser.getId(),productId,count);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("update cart")
    @PostMapping("/update")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count){

        List<CartVO> cartVOList = cartService.update(UserFilter.currentUser.getId(),productId,count);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("delete cart")
    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam Integer productId){

        List<CartVO> cartVOList = cartService.delete(UserFilter.currentUser.getId(),productId);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("select / unselect product in cart")
    @PostMapping("/select")
    public ApiRestResponse select(@RequestParam Integer productId,
                                  @RequestParam Integer selected){

        List<CartVO> cartVOList = cartService.selectOrNot(UserFilter.currentUser.getId(),
                productId, selected);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("select / unselect product in cart")
    @PostMapping("/selectAll")
    public ApiRestResponse selectAll(@RequestParam Integer selected){

        List<CartVO> cartVOList = cartService.selectAllOrNot(UserFilter.currentUser.getId(),
                selected);
        return ApiRestResponse.success(cartVOList);
    }


}
