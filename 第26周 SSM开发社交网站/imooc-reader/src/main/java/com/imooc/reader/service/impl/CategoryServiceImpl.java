package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Category;
import com.imooc.reader.mapper.CategoryMapper;
import com.imooc.reader.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

//作为beanId通常是和接口保持一致的。why? 因为在具体使用时引入的是接口,而生成的属性是categoryService,保持属性和beanId一致是一个基本的规则。详情见CategoryServiceImplTest引入
@Service("categoryService")
//事务控制的注解。[事务的传播方式]意味着在当前方法中默认情况下所有方法是不需要使用事务的 (如果遇到了某个方法需要写操作的话,那需要额外的在方法上增加@Transactional来开启事务)
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有图书分类
     * @return  图书分类List
     */
    @Override
    public List<Category> selectAll() {
        //创建了一个全新的对象,里边没有设置任何条件就意味着查询所有
        List<Category> list = categoryMapper.selectList(new QueryWrapper<Category>());
        return list;
    }
}
