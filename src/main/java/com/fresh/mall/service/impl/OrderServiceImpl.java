package com.fresh.mall.service.impl;

import com.fresh.mall.common.Constant;
import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.fresh.mall.filter.UserFilter;
import com.fresh.mall.model.dao.CartMapper;
import com.fresh.mall.model.dao.OrderItemMapper;
import com.fresh.mall.model.dao.OrderMapper;
import com.fresh.mall.model.dao.ProductMapper;
import com.fresh.mall.model.pojo.Order;
import com.fresh.mall.model.pojo.OrderItem;
import com.fresh.mall.model.pojo.Product;
import com.fresh.mall.model.request.CreateOrderReq;
import com.fresh.mall.model.vo.CartVO;
import com.fresh.mall.service.CartService;
import com.fresh.mall.service.OrderService;
import com.fresh.mall.util.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    CartService cartService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public String create(CreateOrderReq createOrderReq){
        //get userId
        Integer userId = UserFilter.currentUser.getId();

        //search selected products in this customer's cart
        List<CartVO> cartVOList = cartService.list(userId);
        ArrayList<CartVO> cartVOListTemp = new ArrayList<>();
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            if(cartVO.getSelected().equals(Constant.Cart.CHECKED)){
                cartVOListTemp.add(cartVO);
            }
        }
        cartVOList = cartVOListTemp;
        //if the selected product in this cart is null, error
        if (CollectionUtils.isEmpty(cartVOList)){
            throw new FreshMallException(FreshMallExceptionEnum.CART_EMPTY);
        }
        //then check if the product exit, status of this product, stock
        validSaleStatusAndStock(cartVOList);
        //cart object -> order item object
        List<OrderItem> orderItemList = cartVOListToOrderItemList(cartVOList);
        //update stock
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            int stock = product.getStock() - orderItem.getQuantity();
            if(stock < 0){
                throw new FreshMallException(FreshMallExceptionEnum.NOT_ENOUGH);
            }
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        }
        //delete selected product(because order will be created)
        cleanCart(cartVOList);

        //create order, generate order id
        Order order = new Order();
        //save to order table
        String orderNo = OrderCodeFactory.getOrderCode(Long.valueOf(userId));
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalAmount(totalAmount(orderItemList));
        order.setReceiverName(createOrderReq.getReceiverName());
        order.setReceiverMobile(createOrderReq.getReceiverMobile());
        order.setReceiverAddress(createOrderReq.getReceiverAddress());
        order.setOrderStatus(Constant.OrderStatusEnum.NOT_PAID.getCode());
        order.setDelivery(0);
        order.setPaymentType(1);
        orderMapper.insertSelective(order);
        //save all products to order item table
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insertSelective(orderItem);
        }
        //return order id
        return orderNo;
    }

    private Integer totalAmount(List<OrderItem> orderItemList) {
        Integer totalAmount = 0;
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            totalAmount+=orderItem.getTotalAmount();
        }
        return totalAmount;
    }

    private void cleanCart(List<CartVO> cartVOList) {
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    private List<OrderItem> cartVOListToOrderItemList(List<CartVO> cartVOList) {
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            //product snapshot (change later but the order detail should be old
            orderItem.setProductName(cartVO.getProductName());
            orderItem.setProductImg(cartVO.getProductImage());
            orderItem.setUnitPrice(cartVO.getQuantity());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setTotalAmount(cartVO.getTotalPrice());
            orderItemList.add(orderItem);
        }
        return orderItemList;

    }

    private void validSaleStatusAndStock(List<CartVO> cartVOList) {
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            //product exist and product status 0/1
            if(product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)){
                throw new FreshMallException(FreshMallExceptionEnum.NOT_SALE);
            }
            if(cartVO.getQuantity() > product.getStock()){
                throw new FreshMallException(FreshMallExceptionEnum.NOT_ENOUGH);
            }

        }

    }
}
