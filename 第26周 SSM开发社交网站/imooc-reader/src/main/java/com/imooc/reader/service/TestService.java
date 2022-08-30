package com.imooc.reader.service;

import com.imooc.reader.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestService {
    //运行时注入 TestMapper这个对象
    @Resource
    private TestMapper testMapper;

    //作为当前的事务控制注解,它的运行条件是当我们方法执行成功则进行全局提交;如果方法抛出了运行时异常则进行全局的回滚。
    // 通过这个注解可以做到要么数据全部完成,要么数据什么都不做
    @Transactional
    public void batchImport() {
        for (int i = 0; i < 5; i++) {
//            if (i == 3) {
//                throw new RuntimeException("预期之外的异常");
//            }
            testMapper.insertSample();
        }
    }
}
