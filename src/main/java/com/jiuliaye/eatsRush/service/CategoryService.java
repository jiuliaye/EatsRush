package com.jiuliaye.eatsRush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuliaye.eatsRush.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}
