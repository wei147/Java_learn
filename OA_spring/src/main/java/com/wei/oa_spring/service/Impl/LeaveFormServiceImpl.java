package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.exception.OAException;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.dao.LeaveFormMapper;
import com.wei.oa_spring.model.dao.ProcessFlowMapper;
import com.wei.oa_spring.model.pojo.LeaveForm;
import com.wei.oa_spring.model.pojo.ProcessFlow;
import com.wei.oa_spring.model.request.AuditProcessFlowReq;
import com.wei.oa_spring.model.request.CreateLeaveFormReq;
import com.wei.oa_spring.service.LeaveFormService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaveFormServiceImpl implements LeaveFormService {
    @Resource
    LeaveFormMapper leaveFormMapper;

    @Resource
    ProcessFlowMapper processFlowMapper;

    @Override
    public void createLeaveForm(CreateLeaveFormReq createLeaveFormReq) {
        LeaveForm form = new LeaveForm();
        //category.setName(addCategoryReq.getName());
        //copyProperties它就会把两个类里面如果字段类型一样,字段名一样的话,他就会拷贝过去,省得我们一个一个去写
        //copyProperties(source,target)  第一个参数source从哪里拷贝 第一个参数target拷贝到哪里
        BeanUtils.copyProperties(createLeaveFormReq, form);
        int count = leaveFormMapper.insertSelective(form);
        if ((count != 0)) {
            throw new OAException(OAExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public List<Map> getLeaveFormList(String pfState, Long operatorId) {
        return leaveFormMapper.selectByParams(pfState, operatorId);
    }

    public void audit(AuditProcessFlowReq auditProcessFlowReq) {
        //1.无论同意/驳回，当前任务状态更变为complete
        List<ProcessFlow> flowList = processFlowMapper.selectByFormId(auditProcessFlowReq.getFormId());
        if (flowList.size() == 0) {
            throw new OAException(OAExceptionEnum.INVALID_APPROVAL_PROCESS);
        }
        //获取当前任务ProcessFlow对象
        //filter() 对流数据进行筛选 p 代表每一个流程数据 当前节点的经办人和当前用户的编号是匹配的 最后筛选完成之后再生成一个新的list
        List<ProcessFlow> processList = flowList.stream().filter(p -> p.getOperatorId().equals(auditProcessFlowReq.getOperatorId()) && p.getState().equals("process")).collect(Collectors.toList());
        ProcessFlow process = null;
        if ((processList.size()==0)) {
            throw new OAException(OAExceptionEnum.NO_PENDING_TASKS_FOUND);
        }else {
            process = processList.get(0);
            process.setState("complete");   //如果找到符合筛选条件的，将process改为complete （经过操作后）
            process.setResult(auditProcessFlowReq.getResult());
            process.setReason(auditProcessFlowReq.getReason());
            process.setAuditTime(new Date());
            processFlowMapper.update(process); //完成更新工作
        }


    }
}
