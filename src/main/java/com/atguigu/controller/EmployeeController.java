package com.atguigu.controller;

import com.atguigu.entity.Employee;
import com.atguigu.entity.Msg;
import com.atguigu.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;


    /**
     * 删除员工
     */
    @ResponseBody
    @RequestMapping(value = "emp/{ids}",method = RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids") String ids){
        if(ids.contains("-")){
            List<Integer> integers = new ArrayList<>();
            String[] split = ids.split("-");
            for(String s:split){
                integers.add(Integer.parseInt(s));
            }
            employeeService.deleteBatch(integers);
        }else{
            int id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return Msg.success();
    }

    /**
     * 更新员工
     * @param employee
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "emp/{empId}",method = RequestMethod.PUT)
    public Msg updateEmp(Employee employee, HttpServletRequest request){
        System.out.println("请求体中的值："+request.getParameter("gender"));
        System.out.println("将要更新的员工数据："+employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee=employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    /**
     * 数据库数据校验
     * @param empName
     * @return
     */
    @RequestMapping("/checkuser")
    @ResponseBody
    public Msg checkUser(@RequestParam("empName")String empName){
        //正则表达式
        String regx="(^[a-zA-Z0-9_-]{3,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        boolean matches = empName.matches(regx);
        if(!matches){
            return Msg.fail().add("va_msg","用户名可以是2-5位中文或者3-16位英文和数字的组合");
        }
        //数据库 用户名校验
        Boolean b =employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg","用户名不可用，已存在");
        }
    }

    /**
     *添加员工 后端数据校验
     * @param employee
     * @param result
     * @return
     */
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        if(result.hasErrors()){
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError:errors){
                System.out.println("错误的字段名"+fieldError.getField());
                System.out.println("错误的信息"+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);

        }else{
            employeeService.saveEmp(employee);
            return Msg.success();
        }

    }


    @RequestMapping(value = "/emps")
    @ResponseBody
    public Msg getEmployeeWithJson(@RequestParam(value = "pn",defaultValue = "1") Integer pa){

        PageHelper.startPage(pa,5);
        List<Employee> employees = employeeService.getALL();
        PageInfo pageInfo = new PageInfo(employees,5);

        return Msg.success().add("pageInfo",pageInfo);
    }

    //@RequestMapping(value = "/emps")
    public String getAll(Model model, @RequestParam(value = "pn",defaultValue = "1") Integer pa){

        //引入pagehelper分页查询   传递页码pa 和每页5条数据
        PageHelper.startPage(pa,5);
        List<Employee> employees = employeeService.getALL();

        //使用pagehelper包装查询后的结果 只需要把 pagehelper 交给页面
        //封装了详细的分页信息         传入连续显示的页数->5
        PageInfo pageInfo = new PageInfo(employees,5);

        model.addAttribute("pageInfo",pageInfo);

        return "list";
    }
}
