package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/index.html")
    public ModelAndView showIndex() {
        return new ModelAndView("/management/evaluation");
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 20;
        }
        //缺少book.bookName和member.username
        Map result = new HashMap();
        IPage<Evaluation> evaluationObject = evaluationService.paging(page, limit);
        List<Evaluation> all_Evaluation = evaluationService.selectAll();
        List<Long> list = new ArrayList<>();
        for (Evaluation evaluation : all_Evaluation) {
            Long bookId = evaluation.getBookId();
            list.add(bookId);
        }
        LinkedHashSet<Long> hashSet = new LinkedHashSet<>(list);
        ArrayList<Long> bookIdList = new ArrayList<>(hashSet); //bookId去重
        System.out.println("=============");
        System.out.println(bookIdList);
        ArrayList evaluationList = new ArrayList();
        for (int i = 0; i < bookIdList.size(); i++) {
//            evaluationList.add(evaluationService.selectByBookId(bookIdList.get(i)));
            List<Evaluation> evaluations = evaluationService.selectByBookId(bookIdList.get(i));
//            evaluations += evaluations;
            result.put("data", evaluations);

        }
        result.put("code", 0);
        result.put("msg", "success");
        result.put("data", evaluationObject.getRecords());//当前页面数据
//        result.put("data",evaluationList);
        result.put("count", evaluationObject.getTotal());//未分页时记录总数
        return result;
    }

    //测试
    @GetMapping("/list/t")
    @ResponseBody
    public Map list() {
        Long bookId = 1L;
        List<Evaluation> evaluations = evaluationService.selectByBookId(bookId);
        Map result = new HashMap();
        result.put("code", 0);
        result.put("data", evaluations);
        System.out.println(evaluations);
        return result;


    }

    //这里本质上还是更新操作,先查询原始拿到原始数据再把要更新的值进行更新,,
    @PostMapping("/disable")
    @ResponseBody
    public Map disableEvaluation(Evaluation evaluation) {
        Map result = new HashMap();
        try {
            Evaluation rawEvaluation = evaluationService.selectById(evaluation.getEvaluationId());
            rawEvaluation.setState("disable");
            rawEvaluation.setDisableTime(new Date());
            rawEvaluation.setDisableReason(evaluation.getDisableReason());

            evaluationService.disableEvaluation(evaluation);
            result.put("code", 0);
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }
}
