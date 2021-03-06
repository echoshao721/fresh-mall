package com.fresh.mall.service;

import com.fresh.mall.model.pojo.Category;
import com.fresh.mall.model.pojo.Product;
import com.fresh.mall.model.request.AddCategoryReq;
import com.fresh.mall.model.request.AddProductReq;
import com.fresh.mall.model.request.ProductListReq;
import com.fresh.mall.model.vo.CategoryVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);
}
