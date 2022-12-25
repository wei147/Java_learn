package com.wei.cloud.mall.practice.categoryproduct.controller;

import com.github.pagehelper.PageInfo;
import com.wei.cloud.mall.practice.categoryproduct.model.pojo.Category;
import com.wei.cloud.mall.practice.categoryproduct.model.request.AddCategoryReq;
import com.wei.cloud.mall.practice.categoryproduct.model.request.UpdateCategoryReq;
import com.wei.cloud.mall.practice.categoryproduct.model.vo.CategoryVO;
import com.wei.cloud.mall.practice.categoryproduct.service.CategoryService;
import com.wei.mall.practice.common.common.ApiRestResponse;
import com.wei.mall.practice.common.exception.ImoocMallExceptionEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 目录Controller
 */
@Controller
@CrossOrigin(origins = "*") //2022年10月27日01:24:54不知道为啥登录接口有跨域问题,加上这个配置试一下
public class CategoryController {
    //这里主要想做的是鉴权操作,判断用户或者是管理员有没有登录。但是网关已经做了这个功能了。所以可以删除掉,
    //@Resource
    //UserService userService;
    @Resource
    CategoryService categoryService;

    /**
     * 后台添加目录
     *
     * @param session
     * @param addCategoryReq
     * @return
     */
    @ApiOperation("后台添加分类目录")
    @PostMapping("admin/category/add")
    @ResponseBody
    //加了@RequestBody之后,我们的Spring就可以从我们的body中,去把这个AddCategoryReq类给对应起来
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        //1.要先做登录验证, 2.而且是管理员才能进行操作
        //下面的if判断校验交给@Valid来实现了,在AddCategoryReq中有参数校验
//        if (addCategoryReq.getName() == null || addCategoryReq.getType() == null ||
//                addCategoryReq.getParentId() == null || addCategoryReq.getOrderNum() == null) {
//            return ApiRestResponse.error(ImoocMallExceptionEnum.PARA_NOT_NULL);
//        }
//        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
//        //校验是否已经登录
//        if (currentUser == null) {
//            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
//        }
        //校验是否是管理员
//        boolean adminRole = userService.checkAdminRole(currentUser);
//        if (adminRole) {
//            //是管理员,执行操作
//            categoryService.add(addCategoryReq);
//            return ApiRestResponse.success();
//        } else {
//            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
//        }
    }


    @ApiOperation("后台更新分类目录")
    @PostMapping("admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(HttpSession session, @Valid @RequestBody UpdateCategoryReq updateCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(updateCategoryReq, category);
        categoryService.update(category);
        return ApiRestResponse.success();

}

    @ApiOperation("后台删除分类目录")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id) {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台分类目录列表")
    @GetMapping("admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        //分页有这么几个好处:  1.减少系统的消耗,提高性能,提高速度(实际上我们没必要把那么多少数据都查出来一次性返回给用户,用户也不一定会看)
        //2.符合用户习惯(用户一般看的是前30条)
        //3.对于前端来讲,需要有大小的一个限制。要不然你那么多数据,前端页面也不好设计
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("前台分类目录列表")
    @GetMapping("category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer(Integer parentId) {
        //对于前台用户而言,不需要传入页码或者pageSize,因为这是我们直接返回给他的,由我们来决定
        List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(0); //这里显示的就是所有的目录,所以传入0
        return ApiRestResponse.success(categoryVOList);
    }
}
