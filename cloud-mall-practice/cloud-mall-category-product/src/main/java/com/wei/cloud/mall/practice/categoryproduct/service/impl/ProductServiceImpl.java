package com.wei.cloud.mall.practice.categoryproduct.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.cloud.mall.practice.categoryproduct.model.dao.ProductMapper;
import com.wei.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.wei.cloud.mall.practice.categoryproduct.model.query.ProductListQuery;
import com.wei.cloud.mall.practice.categoryproduct.model.request.AddProductReq;
import com.wei.cloud.mall.practice.categoryproduct.model.request.ProductListReq;
import com.wei.cloud.mall.practice.categoryproduct.model.vo.CategoryVO;
import com.wei.cloud.mall.practice.categoryproduct.service.CategoryService;
import com.wei.cloud.mall.practice.categoryproduct.service.ProductService;
import com.wei.mall.practice.common.common.Constant;
import com.wei.mall.practice.common.exception.ImoocMallException;
import com.wei.mall.practice.common.exception.ImoocMallExceptionEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品服务实现类
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductMapper productMapper;

    @Resource
    CategoryService categoryService;

    @Override
    public void add(AddProductReq addProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);
        Product productOld = productMapper.selectByName(addProductReq.getName());
        //判断是否重名
        if (productOld != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Product updateProduct) {
        Product productOld = productMapper.selectByName(updateProduct.getName());
        //同名且不同id,不能修改
        if (productOld != null && !productOld.getId().equals(updateProduct.getId())) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id) {
        Product productOld = productMapper.selectByPrimaryKey(id);
        //查不到该记录,无法删除
        //删除商品在业务上不是很推荐,因为为了保证数据库信息的沉淀,有商品可以对其更新、上下架。下架就是变相的软删除方式
        if (productOld == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) {
        //最重要的是对于mapper的实现
        productMapper.batchUpdateSellStatus(ids, sellStatus);
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }

    @Override
    public Product detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    //像这种复杂查询的时候,我们通常还有一个技巧 就是去构建一个query对象。这个query对象专门用于查询的,
    // 因为往往查询的条件越多,我们一个一个的去拼接这种打散装的结构,他们就会更加的不聚合,所以在后期拓展的时候会显得代码凌乱
    //新建包 com/imooc/mall/model/query
    @Override
    public PageInfo list(ProductListReq productListReq) {
        //构建query对象
        ProductListQuery productListQuery = new ProductListQuery();

        //搜索处理
        if (!StringUtils.isEmpty(productListReq.getKeyword())) {
            //StringBuffer() 是合成字符串用的
            //String keyword = "%" + productListReq.getKeyword() + "%"; 这样不行吗?  直接用加号拼接会造成内存空间浪费
            String keyword = new StringBuffer().append("%").append(productListReq.getKeyword()).append("%").toString();
            productListQuery.setKeyword(keyword);
        }

        //目录处理:如果查某个目录下的商品,不仅是需要查出该目录下的,,还要把所有子目录的所有商品查出来,所以要拿到一个目录id的List
        if (productListReq.getCategoryId() != null) {
            List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(productListReq.getCategoryId());
            //上面拿到的 categoryVOList是一个树状结构的,要将其平铺展开
            ArrayList<Integer> categoryIds = new ArrayList<>();
            getCategoryIds(categoryVOList, categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }
        //排序处理 (orderBy是从前端请求中拿到的)
        String orderBy = productListReq.getOrderBy();
        //contains 包含
        if (Constant.productListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize(), orderBy);
        } else {
            //说明传进来的参数不支持排序。不进行排序
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize());
        }
        List<Product> productList = productMapper.selectList(productListQuery);
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;

    }

    //上面拿到的 categoryVOList是一个树状结构的,要将其平铺展开,便利用这个方法实现
    private void getCategoryIds(List<CategoryVO> categoryVOList, ArrayList<Integer> categoryIds) {
        for (int i = 0; i < categoryVOList.size(); i++) {
            CategoryVO categoryVO = categoryVOList.get(i);
            if (categoryVO != null) {
                categoryIds.add(categoryVO.getId());
                //神奇的递归
                getCategoryIds(categoryVO.getChildCategory(), categoryIds);
            }
        }
    }
}
