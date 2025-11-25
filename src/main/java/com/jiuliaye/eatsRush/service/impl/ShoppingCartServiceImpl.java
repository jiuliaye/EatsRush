package com.jiuliaye.eatsRush.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuliaye.eatsRush.entity.ShoppingCart;
import com.jiuliaye.eatsRush.mapper.ShoppingCartMapper;
import com.jiuliaye.eatsRush.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
