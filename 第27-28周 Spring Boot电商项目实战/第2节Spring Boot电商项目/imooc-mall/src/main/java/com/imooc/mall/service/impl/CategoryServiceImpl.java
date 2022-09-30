package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CategoryMapper;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.UpdateCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 目录分类Service实现类
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
//        category.setName(addCategoryReq.getName());
        //copyProperties它就会把两个类里面如果字段类型一样,字段名一样的话,他就会拷贝过去,省得我们一个一个去写
        //copyProperties(source,target)  第一个参数source从哪里拷贝 第一个参数target拷贝到哪里
        BeanUtils.copyProperties(addCategoryReq, category);

        //如果有重名分类名不允许再添加
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        System.out.println("categoryOld:  "+categoryOld.toString());
        if (categoryOld != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if ((count == 0)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }
    }

//我写的没有考虑到重名的问题
//    @Override
//    public void update(UpdateCategoryReq updateCategoryReq) {
//        Category category = categoryMapper.selectByPrimaryKey(updateCategoryReq.getId());
//        BeanUtils.copyProperties(updateCategoryReq, category);
//        int count = categoryMapper.updateByPrimaryKeySelective(category);
//        if ((count == 0)) {
//            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
//        }
//    }

    @Override
    public void update(Category updateCategory) {
        if (updateCategory.getName() != null) {
            Category categoryOld = categoryMapper.selectByName(updateCategory.getName());
            //如果传进来的id和数据库里的id不一样的话而且你们名字还是一样的话,此时便拒绝掉 (这里便是避免了重名操作,比如id为31的小黄鱼去改id=8的鱼类是不允许的)
            if (categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
            }
        }
        int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if ((count == 0)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    public void delete(Integer id) {
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        //查不到记录,无法删除,删除失败
        if (categoryOld == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if ((count == 0)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        //实现分页功能
        //排序规则: 首先按照type作为第一优先级排序,然后同样的type下再按照order_num进行排序
        PageHelper.startPage(pageNum, pageSize, "type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }
}
