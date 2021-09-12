package com.atguigu.test;

import com.atguigu.dao.DepartmentMapper;
import com.atguigu.dao.EmployeeMapper;
import com.atguigu.entity.Department;
import com.atguigu.entity.Employee;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * 1.导入spring test 模块
 * 2.@contextconfiguration 导入spring配置文件
 * 3.直接使用 @autowired 即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:bean.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;
    @Test
    public void test1(){
        //System.out.println(departmentMapper);
//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));
//        employeeMapper.insertSelective(new Employee(null,"liwang","M","liwang@qq.com",1));
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for(int i=0;i<1000;i++){
            String uuid=UUID.randomUUID().toString().substring(0,5)+i;
            employeeMapper.insertSelective(new Employee(null,uuid,"M",uuid+"@qq.com",1));
        }
    }
}
