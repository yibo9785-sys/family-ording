package com.example.family_ording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.family_ording.mapper.DishMapper;
import com.example.family_ording.model.entity.Dish;
import com.example.family_ording.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Override
    public Dish getDishById(Long id) {
        return this.getById(id);
    }

}
