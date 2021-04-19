package com.fresh.mall.service.impl;

import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.fresh.mall.model.dao.ProductMapper;
import com.fresh.mall.model.pojo.Product;
import com.fresh.mall.model.request.AddProductReq;
import com.fresh.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Override
    public void add(AddProductReq addProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq,product);
        Product productOld = productMapper.selectByName(addProductReq.getName());
        if(productOld != null){
            throw new FreshMallException(FreshMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if(count == 0){
            throw new FreshMallException(FreshMallExceptionEnum.CREATE_FAILED);
        }

    }
    @Override
    public void update(Product updateProduct){
        Product productOld = productMapper.selectByName(updateProduct.getName());
        //same name but different id, stop update
        if(productOld != null && !productOld.getId().equals(updateProduct.getId())){
            throw new FreshMallException(FreshMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if(count == 0){
            throw new FreshMallException(FreshMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id){
        Product productOld = productMapper.selectByPrimaryKey(id);
        //same name but different id, stop update
        if(productOld == null ){
            throw new FreshMallException(FreshMallExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if(count == 0){
            throw new FreshMallException(FreshMallExceptionEnum.DELETE_FAILED);
        }
    }



}