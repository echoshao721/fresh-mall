package com.fresh.mall.service;

import com.fresh.mall.model.pojo.Category;
import com.fresh.mall.model.request.AddCategoryReq;
import com.fresh.mall.model.request.AddProductReq;
import com.fresh.mall.model.vo.CategoryVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {

    void add(AddProductReq addProductReq);
}
