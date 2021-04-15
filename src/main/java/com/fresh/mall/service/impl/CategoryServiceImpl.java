package com.fresh.mall.service.impl;

import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.fresh.mall.model.dao.CategoryMapper;
import com.fresh.mall.model.pojo.Category;
import com.fresh.mall.model.request.AddCategoryReq;
import com.fresh.mall.model.vo.CategoryVO;
import com.fresh.mall.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq){
        Category category = new Category();
//        category.setName(addCategoryReq.getName());
        BeanUtils.copyProperties(addCategoryReq,category);
        //check duplicate name (category to add)
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if(categoryOld != null){
            throw new FreshMallException(FreshMallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if(count == 0){
            throw new FreshMallException(FreshMallExceptionEnum.CREATE_FAILED);
        }
    }
    @Override
    public void update(Category updateCategory){
        if (updateCategory.getName() != null) {
           Category categoryOld= categoryMapper.selectByName(updateCategory.getName());
           if(categoryOld !=null && !categoryOld.getId().equals(updateCategory.getId())){
               throw new FreshMallException(FreshMallExceptionEnum.NAME_EXISTED);
           }
        }
        int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if(count == 0){
            throw new FreshMallException(FreshMallExceptionEnum.UPDATE_FAILED);
        }
    }
    @Override
    public void delete(Integer id){
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        //no such category found
        if(categoryOld == null){
            throw new FreshMallException(FreshMallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0){
            throw new FreshMallException(FreshMallExceptionEnum.DELETE_FAILED);
        }

    }
    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize,"type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }
}
