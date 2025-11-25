package com.jiuliaye.eatsRush.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuliaye.eatsRush.dto.DishDto;
import com.jiuliaye.eatsRush.entity.Dish;
import com.jiuliaye.eatsRush.entity.DishFlavor;
import com.jiuliaye.eatsRush.mapper.DishMapper;
import com.jiuliaye.eatsRush.service.DishFlavorService;
import com.jiuliaye.eatsRush.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     *
     * @param dishDto
     */
    @Transactional  //事务，保证数据一致性
    public void saveWithFlavor(DishDto dishDto) {
        //1、可以直接保存dish
        this.save(dishDto);

        //2、将DishDto中的flavors列表的每一个对象添加一个dish_id已匹配表结构
        Long dishId = dishDto.getId();//菜品id
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //3、调用dishFlavor表的saveBatch方法，为批量保存
        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {
        //1、查Dish
        Dish dish = this.getById(id);

        //2、查flavors
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        //3、封装成Dto
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        dishDto.setFlavors(flavors);

        //4、返回Dto
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        //对于在update时有修改，有新增，也有可能删除的数据时， 先删除，后添加。
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {  //封装flavor
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存flavor
        dishFlavorService.saveBatch(flavors);
    }
}
