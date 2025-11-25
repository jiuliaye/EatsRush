package com.jiuliaye.eatsRush.dto;

import com.jiuliaye.eatsRush.entity.Dish;
import com.jiuliaye.eatsRush.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    //因为前端传入的Json数据是大于Dish的（实际上是Dish和多个flavor的结合体），所以我们无法像之前一样通过@RequesetBody Dish dish直接封装了，需要自定义一个dto类来扩展dish，然后在controller中分步骤截取dish和flavor来保存到数据库中

    //菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
