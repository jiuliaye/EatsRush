package com.jiuliaye.eatsRush.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuliaye.eatsRush.entity.OrderDetail;
import com.jiuliaye.eatsRush.mapper.OrderDetailMapper;
import com.jiuliaye.eatsRush.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}