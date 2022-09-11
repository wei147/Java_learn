package com.imooc.reader.task;

import com.imooc.reader.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 完成自动计算任务
 */
@Component //通用的组件注解
public class ComputeTask {
    @Resource
    private BookService bookService;

    //任务调度 (定时调度)       [对应是 每分钟0秒的时候自动的执行下面的定时任务]
    @Scheduled(cron = "0 * * * * ?")
    public void updateEvaluation(){
        bookService.updateEvaluation();
        System.out.println("已更新所有图书评分");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        System.out.println("现在的时间是 "+time);
    }
}
