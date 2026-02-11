package com.example.family_ording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.family_ording.model.entity.DailyMenu;

public interface DailyMenuService extends IService<DailyMenu> {

    DailyMenu getMenuById(Long id);

}
