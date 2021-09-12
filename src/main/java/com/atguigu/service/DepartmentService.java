package com.atguigu.service;

import com.atguigu.dao.DepartmentMapper;
import com.atguigu.dao.EmployeeMapper;
import com.atguigu.entity.Department;
import com.atguigu.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;


    public List<Department> getDepts() {

        List<Department> departments = departmentMapper.selectByExample(null);
        return departments;
    }

}
