package com.jiuliaye.eatsRush.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuliaye.eatsRush.entity.Employee;
import com.jiuliaye.eatsRush.mapper.EmployeeMapper;
import com.jiuliaye.eatsRush.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{
}
