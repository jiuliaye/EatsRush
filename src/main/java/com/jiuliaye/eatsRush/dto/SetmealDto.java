package com.jiuliaye.eatsRush.dto;

import com.jiuliaye.eatsRush.entity.Setmeal;
import com.jiuliaye.eatsRush.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
