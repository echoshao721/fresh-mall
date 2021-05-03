package com.fresh.mall.service;

import com.fresh.mall.model.request.CreateOrderReq;

/**
 * order service
 */


public interface OrderService {


    String create(CreateOrderReq createOrderReq);
}
