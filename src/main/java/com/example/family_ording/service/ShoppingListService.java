package com.example.family_ording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.family_ording.model.entity.ShoppingList;

public interface ShoppingListService extends IService<ShoppingList> {

    ShoppingList getListById(Long id);

}
