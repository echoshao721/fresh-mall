package com.fresh.mall.service;

import com.fresh.mall.model.pojo.Category;
import com.fresh.mall.model.request.AddCategoryReq;
import com.fresh.mall.model.vo.CategoryVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer superId);
}
