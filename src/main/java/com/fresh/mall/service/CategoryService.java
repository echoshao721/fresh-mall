package com.fresh.mall.service;

import com.fresh.mall.model.pojo.Category;
import com.fresh.mall.model.request.AddCategoryReq;
import com.github.pagehelper.PageInfo;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);
}
