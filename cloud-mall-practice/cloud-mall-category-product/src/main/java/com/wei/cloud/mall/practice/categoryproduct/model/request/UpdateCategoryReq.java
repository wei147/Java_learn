package com.wei.cloud.mall.practice.categoryproduct.model.request;



import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 更新分类目录的一个请求类
 */
public class UpdateCategoryReq {

    @NotNull(message = "id不能为空")
    private Integer id; //更新分类目录必须要传入id,其他的可以为空

    //分类名字符不能大于5个小于2个
    @Size(min = 2,max = 5)
    private String name;

    //分类名层级数最大为3
    @Max(3)
    private Integer type;

    private Integer parentId;

    private Integer orderNum;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "UpdateCategoryReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
    }
}
