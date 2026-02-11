package com.example.family_ording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.family_ording.mapper.DailyMenuMapper;
import com.example.family_ording.model.entity.DailyMenu;
import com.example.family_ording.service.DailyMenuService;
import org.springframework.stereotype.Service;

@Service
public class DailyMenuServiceImpl extends ServiceImpl<DailyMenuMapper, DailyMenu> implements DailyMenuService {

    @Override
    public DailyMenu getMenuById(Long id) {
        return this.getById(id);
    }

}
