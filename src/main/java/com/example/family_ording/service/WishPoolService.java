package com.example.family_ording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.family_ording.model.entity.WishPool;

public interface WishPoolService extends IService<WishPool> {

    WishPool getWishById(Long id);

}
