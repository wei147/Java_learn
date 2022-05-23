package com.imooc.oa.dao;

import com.imooc.oa.entity.LeaveForm;
import com.imooc.oa.utils.MybatisUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LeaveFromDaoTest {

    @Test
    public void insert() {
        MybatisUtils.executeUpdate(sqlSession -> {
            LeaveFromDao dao = sqlSession.getMapper(LeaveFromDao.class);
            LeaveForm form = new LeaveForm();
            form.setEmployeeId(4L); //员工编号
            form.setFormType(1);    //事假
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = null;  //起始时间
            Date endTime = null;    //结束时间
            try {
                startTime = sdf.parse("2020-03-25 08:00:00");
                endTime = sdf.parse("2020-04-01 08:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            form.setStartTime(startTime);
            form.setEndTime(endTime);
            form.setReason("回家探亲"); //请假事由
            form.setCreateTime(new Date()); //创建时间  （系统的当前时间）
            form.setState("processing");    //当前状态
            dao.insert(form);
            return null;
        });
    }

    @Test
    public void selectByParams() {
        MybatisUtils.executeQuery(sqlSession -> {
            LeaveFromDao dao = sqlSession.getMapper(LeaveFromDao.class);
            List<Map> list = dao.selectByParams("process", 2L);
            System.out.println(list);   //这里打印的字段名是和数据表的字段名一致的（原始字段名）。因为是通过多表查询的，后面使用也要用该字段名 {start_time=2022-05-22 00:00:00.0, reason=我有事,要请假, create_time=2022-05-22 23:18:33.0, department_id=2, employee_id=4, department_name=研发部, form_id=26, end_time=2022-06-23 00:00:00.0, name=宋彩妮, form_type=1, state=processing}, {start_time=2022-05-23 00:00:00.0, reason=有事，我请一天假, create_time=2022-05-23 10:59:57.0, department_id=2, employee_id=4, department_name=研发部, form_id=27, end_time=2022-05-23 23:00:00.0, name=宋彩妮, form_type=1, state=processing}
            return list;
        });
    }
}


























