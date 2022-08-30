package com.imooc.reader.mapper;

//针对于这个接口，我们就称之为 Mapper接口。它的职责就是完成与之对于的数据表增删改查操作。
//但是这个接口又有一个新问题: 系统中的接口有很多,并不是所有的接口都是相应的Mapper接口。
// 为了将这些特殊的Mapper和其他系统的标准接口区分开, 所以我们要进行mapper扫描器的配置

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    public void insertSample();
}
