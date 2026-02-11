package com.example.family_ording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.family_ording.mapper.ShoppingListMapper;
import com.example.family_ording.model.entity.ShoppingList;
import com.example.family_ording.service.ShoppingListService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListServiceImpl extends ServiceImpl<ShoppingListMapper, ShoppingList> implements ShoppingListService {

    @Override
    public ShoppingList getListById(Long id) {
        return this.getById(id);
    }

}
