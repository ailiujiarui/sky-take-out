package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;
import sun.security.rsa.RSASignature;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //进行md5加密 11.11.2024
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

   public void  addEmployee(EmployeeDTO employee) {
        Employee employee1 = new Employee();
        employee1.setName(employee.getName());
        employee1.setPhone(employee.getPhone());
        employee1.setSex(employee.getSex());
        employee1.setIdNumber(employee.getIdNumber());
        employee1.setUsername(employee.getUsername());
        //有默认值不必写
      //  employee1.setStatus(StatusConstant.ENABLE);
        //用常量写方便修改
        employee1.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //创建时间和修改时间
        employee1.setCreateTime(LocalDateTime.now());
        employee1.setUpdateTime(LocalDateTime.now());
        //用户id
        employee1.setUpdateUser(BaseContext.getCurrentId());
        employee1.setCreateUser(BaseContext.getCurrentId());
        employeeMapper.addEmployee(employee1);
    }


    public Employee getEmployeeById(long id) {
        Employee employeeById = employeeMapper.getEmployeeById(id);
        return employeeById;
    }
}