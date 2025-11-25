package com.jiuliaye.eatsRush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuliaye.eatsRush.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee>{
    //继承自MyBatisPlus的BaseMapper类，它与IService是相辅相成的，也就是说BaseMapper会生成selectByID，IService会生成对应的getByID，我们只需要在controller中调用service.getByID()即可
}
