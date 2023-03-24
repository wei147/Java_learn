package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.exception.OAException;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.dao.EmployeeMapper;
import com.wei.oa_spring.model.dao.LeaveFormMapper;
import com.wei.oa_spring.model.dao.NoticeMapper;
import com.wei.oa_spring.model.dao.ProcessFlowMapper;
import com.wei.oa_spring.model.pojo.Employee;
import com.wei.oa_spring.model.pojo.LeaveForm;
import com.wei.oa_spring.model.pojo.Notice;
import com.wei.oa_spring.model.pojo.ProcessFlow;
import com.wei.oa_spring.service.LeaveFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//数据库事务 (需要检查)
//@Transactional(propagation = Propagation.REQUIRED)
//@Service("leaveFormService")
//public class LeaveFormServiceImpl implements LeaveFormService {
//    @Resource
//    LeaveFormMapper leaveFormMapper;
//
//    @Resource
//    ProcessFlowMapper processFlowMapper;
//
//    @Override
//    public void createLeaveForm(LeaveForm form) {
//        LeaveForm form = new LeaveForm();
//        //category.setName(addCategoryReq.getName());
//        //copyProperties它就会把两个类里面如果字段类型一样,字段名一样的话,他就会拷贝过去,省得我们一个一个去写
//        //copyProperties(source,target)  第一个参数source从哪里拷贝 第一个参数target拷贝到哪里
//        BeanUtils.copyProperties(createLeaveFormReq, form);
//        form.setState("processing");
//        form.setCreateTime(new Date());
//        int count = leaveFormMapper.insertSelective(form);
//        if ((count == 0)) {
//            throw new OAException(OAExceptionEnum.CREATE_FAILED);
//        }
//    }
//
//    @Override
//    public List<Map> getLeaveFormList(String pfState, Long operatorId) {
//        return leaveFormMapper.selectByParams(pfState, operatorId);
//    }
//
//    @Override
//    public void audit(AuditProcessFlowReq auditProcessFlowReq) {
//        //1.无论同意/驳回，当前任务状态更变为complete
//        List<ProcessFlow> flowList = processFlowMapper.selectByFormId(auditProcessFlowReq.getFormId());
//        if (flowList.size() == 0) {
//            throw new OAException(OAExceptionEnum.INVALID_APPROVAL_PROCESS);
//        }
//        //获取当前任务ProcessFlow对象
//        //filter() 对流数据进行筛选 p 代表每一个流程数据 当前节点的经办人和当前用户的编号是匹配的 最后筛选完成之后再生成一个新的list
//        List<ProcessFlow> processList = flowList.stream().filter(p -> p.getOperatorId().equals(auditProcessFlowReq.getOperatorId()) && p.getState().equals("process")).collect(Collectors.toList());
//        ProcessFlow process = null;
//        if ((processList.size()==0)) {
//            throw new OAException(OAExceptionEnum.NO_PENDING_TASKS_FOUND);
//        }else {
//            process = processList.get(0);
//            process.setState("complete");   //如果找到符合筛选条件的，将process改为complete （经过操作后）
//            process.setResult(auditProcessFlowReq.getResult());
//            process.setReason(auditProcessFlowReq.getReason());
//            process.setAuditTime(new Date());
//            processFlowMapper.update(process); //完成更新工作
//        }
//    }
//}


/**
 * 请假单流程服务
 */
@Service("leaveFormService")
public class LeaveFormServiceImpl implements LeaveFormService {

    @Resource
    EmployeeMapper employeeMapper;

    @Resource
    LeaveFormMapper leaveFormMapper;

    @Resource
    ProcessFlowMapper processFlowMapper;

    @Resource
    NoticeMapper noticeMapper;


    /**
     * 创建请假单
     *
     * @param form 前端输入的请假单数据
     * @return 持久化后的请假单对象
     */
    public LeaveForm createLeaveForm(LeaveForm form) {
        System.out.println(form);
        //1.持久化form表单数据，8级以下员工表单状态为processing,8级（总经理）状态为approved
//            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        Employee employee = employeeMapper.selectByPrimaryKey(form.getEmployeeId());
        if (employee.getLevel() == 8) {
            form.setState("approved");
        } else {
            form.setState("processing");
        }
        leaveFormMapper.insertSelective(form);

        //2.增加第一条流程数据，说明表单已提交，状态为complete
//            ProcessFlowDao processFlowDao = sqlSession.getMapper(ProcessFlowDao.class);
        ProcessFlow flow1 = new ProcessFlow();
        flow1.setFormId(form.getFormId());
        flow1.setOperatorId(employee.getEmployeeId());
        flow1.setAction("apply");
        flow1.setCreateTime(new Date());
        flow1.setOrderNo(1);
        flow1.setState("complete");
        flow1.setIsLast(0); //作为当前的这个流程，是否为最后一个节点 （0 不是）
        processFlowMapper.insertSelective(flow1);   //将第一个流程数据放入其中
        //3.分情况创建其余流程数据
        //3.1   7级以下员工，生成部门经理审批任务，请假时间大于72小时，还需生成总经理审批任务
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");  //具体的时间格式
//            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
        if (employee.getLevel() < 7) {
            Employee dmanager = employeeMapper.selectLeader(employee);     //这里得到的是部门经理    dmanager
            ProcessFlow flow2 = new ProcessFlow();
            flow2.setFormId(form.getFormId());
            flow2.setOperatorId(dmanager.getEmployeeId());
            flow2.setAction("audit");
            flow2.setCreateTime(new Date());
            flow2.setOrderNo(2);
            flow2.setState("process");
            long diff = form.getEndTime().getTime() - form.getStartTime().getTime();    //得到单位是毫秒的值
            float hours = diff / (1000 * 60 * 60) * 1f;     //转为小时
            if (hours >= Constant.MANAGER_AUDIT_HOURS) {
                flow2.setIsLast(0);
                processFlowMapper.insertSelective(flow2);
                Employee manager = employeeMapper.selectLeader(dmanager);
                ProcessFlow flow3 = new ProcessFlow();
                flow3.setFormId(form.getFormId());
                flow3.setOperatorId(manager.getEmployeeId());
                flow3.setAction("audit");
                flow3.setCreateTime(new Date());
                flow3.setState("ready");
                flow3.setOrderNo(3);
                flow3.setIsLast(1);
                processFlowMapper.insert(flow3);
            } else {
                flow2.setIsLast(1);
                processFlowMapper.insert(flow2);
            }
            //请假单已提交消息
            String noticeContent = String.format("您的请假申请[%s-%s]已提交，请等待上级审批", sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            noticeMapper.insertSelective(new Notice(employee.getEmployeeId(), noticeContent));

            //通知部门经理审批消息
            noticeContent = String.format("%s-%s提起请假申请[%s-%s],请尽快审批", employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            noticeMapper.insertSelective(new Notice(dmanager.getEmployeeId(), noticeContent));   //上级审核的id
        } else if (employee.getLevel() == 7) {    //部门经理
            //3.2   7级员工，生成总经理审批任务
            Employee manger = employeeMapper.selectLeader(employee);
            ProcessFlow flow = new ProcessFlow();
            flow.setFormId(form.getFormId());
            flow.setOperatorId(manger.getEmployeeId());
            flow.setAction("audit");
            flow.setCreateTime(new Date());
            flow.setState("process");
            flow.setOrderNo(2);     //任务流程中的第二个节点
            flow.setIsLast(1);
            processFlowMapper.insertSelective(flow);

            //请假单已提交消息
            String noticeContent = String.format("您的请假申请[%s-%s]已提交，请等待上级审批", sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            noticeMapper.insert(new Notice(employee.getEmployeeId(), noticeContent));

            //通知总经理审批消息
            noticeContent = String.format("%s-%s提起请假申请[%s-%s],请尽快审批", employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            noticeMapper.insert(new Notice(manger.getEmployeeId(), noticeContent));

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
            processFlowMapper.insert(flow);

            String noticeContent = String.format("您的请假申请[%s-%s]系统已自动批准通过。",
                    sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            noticeMapper.insertSelective(new Notice(employee.getEmployeeId(), noticeContent));
        }
        return form;
        // 注：将文字描述的代码放入代码块中，这样可以保证所有的程序操作要么一次性全部提交成功，
        // 要么全部回滚
    }

    //    这是经过LeaveFromDaoTest.java 测试之后才写在这里的
    public List<Map> getLeaveFormList(String pfState, Long operatorId) {
        return leaveFormMapper.selectByParams(pfState, operatorId);
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
        //1.无论同意/驳回，当前任务状态更变为complete
//            ProcessFlowDao processFlowDao = sqlSession.getMapper(ProcessFlowDao.class);//得到实现类

        List<ProcessFlow> flowList = processFlowMapper.selectByFormId(formId);
        if (flowList.size() == 0) {
            throw new OAException(OAExceptionEnum.INVALID_APPROVAL_PROCESS);
        }
        //获取当前任务ProcessFlow对象
        List<ProcessFlow> processList = flowList.stream().filter(p -> p.getOperatorId().equals(operatorId) && p.getState().equals("process")).collect(Collectors.toList());//filter() 对流数据进行筛选 p 代表每一个流程数据 当前节点的经办人和当前用户的编号是匹配的 最后筛选完成之后再生成一个新的list
        ProcessFlow process = null;
        if (processList.size() == 0) {
            //这里会抛出异常? 2023年3月24日23:53:01 未解决
            throw new OAException(OAExceptionEnum.NO_PENDING_TASKS_FOUND);
        } else {
            process = processList.get(0);
            process.setState("complete");   //如果找到符合筛选条件的，将process改为complete （经过操作后）
            process.setResult(result);
            process.setReason(reason);
            process.setAuditTime(new Date());
            processFlowMapper.update(process); //完成更新工作
        }

//            LeaveFormDao leaveFormDao = sqlSession.getMapper(LeaveFormDao.class);
        LeaveForm form = leaveFormMapper.selectByPrimaryKey(formId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");  //具体的时间格式
//            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        Employee employee = employeeMapper.selectByPrimaryKey(form.getEmployeeId()); //表单提交人信息
        Employee operator = employeeMapper.selectByPrimaryKey(operatorId); //任务经办人信息
//            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);

        //2.如果当前任务是最后一个节点，代表流程结束，更新请假单状态为对应的approved/refused
        if (process.getIsLast() == 1) {    //已经是最后一个节点则需要改变请假单状态
            form.setState(result);//approved | refused 只有两种状态，从前台传递过来的
            leaveFormMapper.updateByPrimaryKeySelective(form);  //更新表单状态

            String strResult = null;
            if (result.equals("approved")) {
                strResult = "批准";
            } else if (result.equals("refused")) {
                strResult = "驳回";
            }
            String noticeContent = String.format("您的请假申请[%s-%s]%s%s已%s,审批意见:%s,审批流程已结束",
                    sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                    operator.getTitle(), operator.getName(),
                    strResult, reason);//发给表单提交人的通知
            noticeMapper.insertSelective(new Notice(form.getEmployeeId(), noticeContent));

            noticeContent = String.format("%s-%s提起请假申请[%s-%s]您已%s,审批意见:%s,审批流程已结束",
                    employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                    strResult, reason);//发给审批人的通知
            noticeMapper.insert(new Notice(operator.getEmployeeId(), noticeContent));


        } else {
            //流式处理  readyList 包含所有后续任务节点
            List<ProcessFlow> readyList = flowList.stream().filter(p -> p.getState().equals("ready")).collect(Collectors.toList());
            //3.如果当前任务不是最后一个节点且审批通过，那下一个节点的状态从ready变为process
            if (result.equals("approved")) {  //也就是审核通过
                ProcessFlow readyProcess = readyList.get(0);
                readyProcess.setState("process");
                processFlowMapper.update(readyProcess);

                //消息1:通知表单提交人，部门经理已经审批通过，交由上级继续审批
                String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s已批准,审批意见:%s,请继续等待上级审批。",
                        sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                        operator.getTitle(), operator.getName(), reason);
                noticeMapper.insert(new Notice(form.getEmployeeId(), noticeContent1));

                //消息2:通知总经理有新的审批任务
                String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s],请尽快审批",
                        employee.getTitle(), employee.getName(),
                        sdf.format(form.getStartTime()), sdf.format(form.getEndTime()), reason);
                noticeMapper.insert(new Notice(readyProcess.getOperatorId(), noticeContent2));

                //消息3:通知部门经理（当前经办人），员工的申请单你已批准，交由上级继续审批
                String noticeContent3 = String.format("%s-%s提起请假申请[%s-%s]您已批准，审批意见:%s,申请转至上级领导继续审批",
                        employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()),
                        sdf.format(form.getEndTime()), reason);
                noticeMapper.insert(new Notice(operator.getEmployeeId(), noticeContent3));

            } else if (result.equals("refused")) {
                //4.如果当前任务不是最后一个节点且审批驳回，则后续所有任务状态变为cancel，请假单状态变为refused
                for (ProcessFlow p : readyList) {
                    p.setState("cancel");   //为嘛是遍历？
                    processFlowMapper.update(p);
                }
                form.setState("refused");
                leaveFormMapper.updateByPrimaryKeySelective(form);

                //消息1:通知申请人表单已被驳回
                String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s,已驳回",
                        sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                        operator.getTitle(), operator.getName(), reason);
                noticeMapper.insert(new Notice(form.getEmployeeId(), noticeContent1));

                //消息2:通知经办人表单"您已驳回"
                String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s],您已驳回",
                        employee.getTitle(), employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                        reason);
                noticeMapper.insert(new Notice(operator.getEmployeeId(), noticeContent2));
            }
        }
    }
}
