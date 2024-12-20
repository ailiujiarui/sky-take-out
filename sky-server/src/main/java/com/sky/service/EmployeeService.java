package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import java.util.List;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void addEmployee(EmployeeDTO employee);

    Employee getEmployeeById(long id);

    PageResult getEmp(EmployeePageQueryDTO epdto);

    void updateEmpStatus(Integer status, Long ids);

    void updateEmp(EmployeeDTO employee);
}
