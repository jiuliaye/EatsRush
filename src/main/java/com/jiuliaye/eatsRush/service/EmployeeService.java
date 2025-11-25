package com.jiuliaye.eatsRush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuliaye.eatsRush.entity.Employee;

public interface EmployeeService extends IService<Employee> {
}

/*
    为什么要写一个service接口，然后再实现一个serviceImpl去调用mapper？
        1、解耦，面向接口编程，controller只与service耦合，后续修改serviceImpl与controller无关
        2、接口扩展，后续需要新的业务，只需要重新继承一个serviceImpl即可，如Alipay和TencentPay
    为什么要继承IService？
        这是MyBatisPlus的用法，在serviceImpl中可以省略很多基础的调用mapper的方法， 简化业务层代码实现。
 */
