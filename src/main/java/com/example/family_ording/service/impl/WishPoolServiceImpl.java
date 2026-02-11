package com.example.family_ording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.family_ording.mapper.WishPoolMapper;
import com.example.family_ording.model.entity.WishPool;
import com.example.family_ording.service.WishPoolService;
import org.springframework.stereotype.Service;

@Service
public class WishPoolServiceImpl extends ServiceImpl<WishPoolMapper, WishPool> implements WishPoolService {

    @Override
    public WishPool getWishById(Long id) {
        return this.getById(id);
    }

}
