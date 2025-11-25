package com.jiuliaye.eatsRush.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuliaye.eatsRush.entity.User;
import com.jiuliaye.eatsRush.mapper.UserMapper;
import com.jiuliaye.eatsRush.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{
}
