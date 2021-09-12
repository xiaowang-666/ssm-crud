package com.atguigu.controller;

import com.atguigu.entity.Department;
import com.atguigu.entity.Employee;
import com.atguigu.entity.Msg;
import com.atguigu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @ResponseBody
    @RequestMapping("/depts")
    public Msg getDepts(){
        List<Department> list =departmentService.getDepts();
        return Msg.success().add("depts",list);
    }



}
