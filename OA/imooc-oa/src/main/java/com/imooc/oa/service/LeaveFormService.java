package com.imooc.oa.service;

import com.imooc.oa.dao.EmployeeDao;
import com.imooc.oa.dao.LeaveFormDao;
import com.imooc.oa.dao.NoticeDao;
import com.imooc.oa.dao.ProcessFlowDao;
import com.imooc.oa.entity.Employee;
import com.imooc.oa.entity.LeaveForm;
import com.imooc.oa.entity.Notice;
import com.imooc.oa.entity.ProcessFlow;
import com.imooc.oa.service.exception.BusinessException;
import com.imooc.oa.utils.MybatisUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 请假单流程服务
 */
public class LeaveFormService {
    /**
     * 创建请假单
     *
     * @param form 前端输入的请假单数据
     * @return 持久化后的请假单对象
     */
    public LeaveForm createLeaveForm(LeaveForm form) {
        LeaveForm savedForm = (LeaveForm) MybatisUtils.executeUpdate(sqlSession -> {
            //1.持久化form表单数据，8级以下员工表单状态为processing,8级（总经理）状态为approved
            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
            Employee employee = employeeDao.selectById(form.getEmployeeId());
            if (employee.getLevel() == 8) {
                form.setState("approved");
            } else {
                form.setState("processing");
            }
            LeaveFormDao leaveFormDao = sqlSession.getMapper(LeaveFormDao.class);
            leaveFormDao.insert(form);

            //2.增加第一条流程数据，说明表单已提交，状态为complete
            ProcessFlowDao processFlowDao = sqlSession.getMapper(ProcessFlowDao.class);
            ProcessFlow flow1 = new ProcessFlow();
            flow1.setFormId(form.getFormId());
            flow1.setOperatorId(employee.getEmployeeId());
            flow1.setAction("apply");
            flow1.setCreateTime(new Date());
            flow1.setOrderNo(1);
            flow1.setState("complete");
            flow1.setIsLast(0); //作为当前的这个流程，是否为最后一个节点 （0 不是）
            processFlowDao.insert(flow1);   //将第一个流程数据放入其中
            //3.分情况创建其余流程数据
            //3.1   7级以下员工，生成部门经理审批任务，请假时间大于72小时，还需生成总经理审批任务
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");  //具体的时间格式
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
            if (employee.getLevel() < 7) {
                Employee dmanager = employeeDao.selectLeader(employee);     //这里得到的是部门经理    dmanager
                ProcessFlow flow2 = new ProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(dmanager.getEmployeeId());
                flow2.setAction("audit");
                flow2.setCreateTime(new Date());
                flow2.setOrderNo(2);
                flow2.setState("process");
                long diff = form.getEndTime().getTime() - form.getStartTime().getTime();    //得到单位是毫秒的值
                float hours = diff / (1000 * 60 * 60) * 1f;     //转为小时
                if (hours >= BusinessConstants.MANAGER_AUDIT_HOURS) {
                    flow2.setIsLast(0);
                    processFlowDao.insert(flow2);
                    Employee manager = employeeDao.selectLeader(dmanager);
                    ProcessFlow flow3 = new ProcessFlow();
                    flow3.setFormId(form.getFormId());
                    flow3.setOperatorId(manager.getEmployeeId());
                    flow3.setAction("audit");
                    flow3.setCreateTime(new Date());
                    flow3.setState("ready");
                    flow3.setOrderNo(3);
                    flow3.setIsLast(1);
                    processFlowDao.insert(flow3);
                } else {
                    flow2.setIsLast(1);
                    processFlowDao.insert(flow2);
                }
                //请假单已提交消息
                String noticeContent = String.format("您的请假申请[%s-%s]已提交，请等待上级审批", sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
                noticeDao.insert(new Notice(employee.getEmployeeId(), noticeContent));

                //通知总经理审批消息
                noticeContent = String.format("%s-%s提起请假申请[%s-%s],请尽快审批", employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
                noticeDao.insert(new Notice(dmanager.getEmployeeId(), noticeContent));   //上级审核的id
            } else if (employee.getLevel() == 7) {    //部门经理
                //3.2   7级员工，生成总经理审批任务
                Employee manger = employeeDao.selectLeader(employee);
                ProcessFlow flow = new ProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(manger.getEmployeeId());
                flow.setAction("audit");
                flow.setCreateTime(new Date());
                flow.setState("process");
                flow.setOrderNo(2);     //任务流程中的第二个节点
                flow.setIsLast(1);
                processFlowDao.insert(flow);

            } else if (employee.getLevel() == 8) {
                //3.3   8级员工，生成总经理审批任务，系统自动通过
                ProcessFlow flow = new ProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(employee.getEmployeeId());
                flow.setAction("audit");
                flow.setResult("approved"); //通过
                flow.setReason("自动通过");
                flow.setCreateTime(new Date());
                flow.setState("complete");
                flow.setAuditTime(new Date());
                flow.setOrderNo(2);
                flow.setIsLast(1);
                processFlowDao.insert(flow);

                String noticeContent = String.format("您的请假申请[%s-%s]系统已自动批准通过。", sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
                noticeDao.insert(new Notice(employee.getEmployeeId(), noticeContent));
            }
            return form;
            // 注：将文字描述的代码放入代码块中，这样可以保证所有的程序操作要么一次性全部提交成功，
            // 要么全部回滚
        });
        return savedForm;
    }

    //    这是经过LeaveFromDaoTest.java 测试之后才写在这里的
    public List<Map> getLeaveFormList(String pfState, Long operatorId) {
        return (List<Map>) MybatisUtils.executeQuery(sqlSession -> {
            LeaveFormDao dao = sqlSession.getMapper(LeaveFormDao.class);
            List<Map> formList = dao.selectByParams(pfState, operatorId);
            return formList;
        });
    }

    /**
     * 审核请假单
     *
     * @param formId     表单编号
     * @param operatorId 经办人（当前登录员工）
     * @param result     审批结果
     * @param reason     审批意见
     */
    public void audit(Long formId, Long operatorId, String result, String reason) {
        MybatisUtils.executeUpdate(sqlSession -> {
            //1.无论同意/驳回，当前任务状态更变为complete
            ProcessFlowDao processFlowDao = sqlSession.getMapper(ProcessFlowDao.class);//得到实现类
            List<ProcessFlow> flowList = processFlowDao.selectByFormId(formId);
            if (flowList.size() == 0) {
                throw new BusinessException("PF001", "无效的审批流程");
            }
            //获取当前任务ProcessFlow对象
            List<ProcessFlow> processList = flowList.stream().filter(p -> p.getOperatorId().equals(operatorId) && p.getState().equals("process")).collect(Collectors.toList());//filter() 对流数据进行筛选 p 代表每一个流程数据 当前节点的经办人和当前用户的编号是匹配的 最后筛选完成之后再生成一个新的list
            ProcessFlow process = null;
            if (processList.size() == 0) {
                throw new BusinessException("PF002", "未找到待处理任务");
            } else {
                process = processList.get(0);
                process.setState("complete");   //如果找到符合筛选条件的，将process改为complete （经过操作后）
                process.setResult(result);
                process.setReason(reason);
                process.setAuditTime(new Date());
                processFlowDao.update(process); //完成更新工作
            }

            LeaveFormDao leaveFormDao = sqlSession.getMapper(LeaveFormDao.class);
            LeaveForm form = leaveFormDao.selectById(formId);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");  //具体的时间格式
            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
            Employee employee = employeeDao.selectById(form.getEmployeeId()); //表单提交人信息
            Employee operator = employeeDao.selectById(operatorId); //任务经办人信息
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);

            //2.如果当前任务是最后一个节点，代表流程结束，更新请假单状态为对应的approved/refused
            if (process.getIsLast() == 1) {    //已经是最后一个节点则需要改变请假单状态
                form.setState(result);//approved | refused 只有两种状态，从前台传递过来的
                leaveFormDao.update(form);  //更新表单状态

                String strResult = null;
                if (result.equals("approved")) {
                    strResult = "批准";
                } else if (result.equals("refused")) {
                    strResult = "驳回";
                }
                String noticeContent = String.format("您的请假申请[%s-%s]%s%s已%s,审批意见:%s.审批流程已结束。",
                        sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                        operator.getTitle(), operator.getName(),
                        strResult, reason);//发给表单提交人的通知
                noticeDao.insert(new Notice(form.getEmployeeId(), noticeContent));

                noticeContent = String.format("%s-%s提起请假申请[%s-%s]您已%s,审批意见:%s,审批流程已结束",
                        employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                        strResult, reason);//发给审批人的通知
                noticeDao.insert(new Notice(operator.getEmployeeId(), noticeContent));


            } else {
                //流式处理  readyList 包含所有后续任务节点
                List<ProcessFlow> readyList = flowList.stream().filter(p -> p.getState().equals("ready")).collect(Collectors.toList());
                //3.如果当前任务不是最后一个节点且审批通过，那下一个节点的状态从ready变为process
                if (result.equals("approved")) {  //也就是审核通过
                    ProcessFlow readyProcess = readyList.get(0);
                    readyProcess.setState("process");
                    processFlowDao.update(readyProcess);

                    //消息1:通知表单提交人，部门经理已经审批通过，交由上级继续审批
                    String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s已批准,审批意见:%s.请继续等待总经理审批。",
                            sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                            operator.getTitle(), operator.getName(), reason);
                    noticeDao.insert(new Notice(form.getEmployeeId(), noticeContent1));

                    //消息2:通知总经理有新的审批任务
                    String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s],请尽快审批",
                            sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                            employee.getTitle(), employee.getName(), reason);
                    noticeDao.insert(new Notice(form.getEmployeeId(), noticeContent2));

                    //消息3:通知部门经理（当前经办人），员工的申请单你已批准，交由上级继续审批
                    String noticeContent3 = String.format("%s-%s提起请假申请[%s-%s]您已批准，审批意见%s",
                            sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                            employee.getTitle(), employee.getName(), reason);
                    noticeDao.insert(new Notice(form.getEmployeeId(), noticeContent3));

                } else if (result.equals("refused")) {
                    //4.如果当前任务不是最后一个节点且审批驳回，则后续所有任务状态变为cancel，请假单状态变为refused
                    for (ProcessFlow p : readyList) {
                        p.setState("cancel");   //为嘛是遍历？
                        processFlowDao.update(p);
                    }
                    form.setState("refused");
                    leaveFormDao.update(form);

                    //消息1:通知申请人表单已被驳回
                    String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s,已驳回",
                            sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                            operator.getTitle(), operator.getName(), reason);
                    noticeDao.insert(new Notice(form.getEmployeeId(), noticeContent1));

                    //消息2:通知经办人表单"您已驳回"
                    String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s],您已驳回",
                            employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                            reason);
                    noticeDao.insert(new Notice(operator.getEmployeeId(), noticeContent2));
                }
            }
            return null;
        });
    }
}
