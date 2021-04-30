package com.fresh.mall.service;


import com.fresh.mall.model.vo.CartVO;

import java.util.List;

public interface CartService {

    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId, Integer productId, Integer count);

    List<CartVO> update(Integer userId, Integer productId, Integer count);

    List<CartVO> delete(Integer userId, Integer productId);
}
