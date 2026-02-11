package com.example.family_ording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.family_ording.model.entity.Dish;

public interface DishService extends IService<Dish> {

    Dish getDishById(Long id);

}
