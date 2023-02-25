package com.wei.interview;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RestController
public class RestfulController {
    @GetMapping("/emp/list/{DEPT}")
    @ResponseBody
    public String list(@PathVariable("DEPT") String department) {
        List<Employee> list = new ArrayList<Employee>();
        if (department.equals("RESEARCH")) {
            list.add(new Employee("JAMES", 38, "RESEARCH"));
            list.add(new Employee("ANDY", 23, "RESEARCH"));
            list.add(new Employee("SMITH", 31, "RESEARCH"));
        }
        return JSON.toJSONString(list);
    }

    @GetMapping("/")
    @ResponseBody
    public String getList() {
        List<Employee> list = new ArrayList<Employee>();
        list.add(new Employee("JAMES", 38, "RESEARCH"));
        list.add(new Employee("ANDY", 23, "RESEARCH"));
        list.add(new Employee("SMITH", 31, "RESEARCH"));
        return JSON.toJSONString(list);
    }
}
