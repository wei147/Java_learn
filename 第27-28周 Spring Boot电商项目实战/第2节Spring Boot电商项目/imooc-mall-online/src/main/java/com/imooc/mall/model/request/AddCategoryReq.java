package com.imooc.mall.model.request;



import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 添加目录的一个请求类
 */
public class AddCategoryReq {

    //分类名字符不能大于5个小于2个且不能为空
    @Size(min = 2,max = 5)
    @NotNull(message = "name不能为null")
    private String name;

    //分类名层级数最大为3
    @NotNull(message = "type不能为null")
    @Max(3)
    private Integer type;

    @NotNull(message = "parentId不能为null")
    private Integer parentId;

    @NotNull(message = "orderNum不能为null")
    private Integer orderNum;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 上线前的准备 2022年10月26日00:27:00
     * 1.第一步给 package com.imooc.mall.model.request;的请求类加上toString()方法
     *     为什么? 因为 package com.imooc.mall.filter;下的WebLogAspect方法
     *         log.info("ARGS(参数) : " + Arrays.toString(joinPoint.getArgs()));
     *         入参是一个对象的话需要toString把它的个个字段的内容给打印出来,以便在调试的时候更加方便
     */
    @Override
    public String toString() {
        return "AddCategoryReq{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
    }
}
