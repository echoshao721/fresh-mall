package com.fresh.mall.service.impl;

import com.fresh.mall.common.Constant;
import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.fresh.mall.model.dao.CartMapper;
import com.fresh.mall.model.dao.ProductMapper;
import com.fresh.mall.model.pojo.Cart;
import com.fresh.mall.model.pojo.Product;
import com.fresh.mall.model.vo.CartVO;
import com.fresh.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count){
        validProduct(productId,count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if(cart == null){
            //product not in cart, insert one new cart
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        }else{
            //this product already in this cart, update qty
            count = cart.getQuantity()+count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return null;

    }

    private void validProduct(Integer productId, Integer count){
        Product product = productMapper.selectByPrimaryKey(productId);
        //product exist and product status 0/1
        if(product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)){
            throw new FreshMallException(FreshMallExceptionEnum.NOT_SALE);
        }
    }
}
