package com.wei.cloud.mall.practice.categoryproduct.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.cloud.mall.practice.categoryproduct.model.dao.CategoryMapper;
import com.wei.cloud.mall.practice.categoryproduct.model.pojo.Category;
import com.wei.cloud.mall.practice.categoryproduct.model.request.AddCategoryReq;
import com.wei.cloud.mall.practice.categoryproduct.model.vo.CategoryVO;
import com.wei.cloud.mall.practice.categoryproduct.service.CategoryService;
import com.wei.mall.practice.common.exception.ImoocMallException;
import com.wei.mall.practice.common.exception.ImoocMallExceptionEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
//        System.out.println("categoryOld:  " + categoryOld.toString()); 这里不能加这个,如果是新增没有的商品分类名称会报空指针异常
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

    @Override
    @Cacheable(value = "listCategoryForCustomer") //value即它在存储中的key值 (给用户端缓存目录列表)
    public List<CategoryVO> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        //怎么往里面添加数据?
        //对于一级目录而言,他的父id为0,
        recursivelyFindCategories(categoryVOList, parentId);
        return categoryVOList;
    }

    //往往在这个方法中需要做一些额外数据处理的话,新写一个方法会比较合适,让每个方法有自己独自的职能,叫做单一原则
    //recursivelyFindCategories 递归的去查找目录
    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        //ArrayList<CategoryVO> categoryVOList 传入这个参数的目的是为了往里面添加数据。第二个参数parentId就是我们父目录的类别
        //递归获取所有子类别,并组合成为一个"目录树"
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        //对集合进行空判断用这种方法不是特别好(categoryList==null),也许它被初始化但里面没有内容,所以可以用一个更好的方法 如下
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                categoryVOList.add(categoryVO);
                //设置childCategory的值
                recursivelyFindCategories(categoryVO.getChildCategory(), categoryVO.getId());
            }
        }
    }
}
