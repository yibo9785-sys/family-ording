package com.example.family_ording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.family_ording.mapper.IngredientMapper;
import com.example.family_ording.model.entity.Ingredient;
import com.example.family_ording.service.IngredientService;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl extends ServiceImpl<IngredientMapper, Ingredient> implements IngredientService {

    @Override
    public Ingredient getIngredientById(Long id) {
        return this.getById(id);
    }

}
