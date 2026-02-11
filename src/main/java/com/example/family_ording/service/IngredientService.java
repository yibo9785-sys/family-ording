package com.example.family_ording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.family_ording.model.entity.Ingredient;

public interface IngredientService extends IService<Ingredient> {

    Ingredient getIngredientById(Long id);

}
