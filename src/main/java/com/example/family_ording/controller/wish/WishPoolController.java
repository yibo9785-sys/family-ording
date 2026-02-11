package com.example.family_ording.controller.wish;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.family_ording.Result;
import com.example.family_ording.model.entity.WishPool;
import com.example.family_ording.service.WishPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wish/v1")
public class WishPoolController {

    @Autowired
    private WishPoolService wishPoolService;

    @GetMapping("/{id}")
    public Result<WishPool> getWishById(@PathVariable Long id) {
        WishPool wish = wishPoolService.getWishById(id);
        return wish != null ? Result.success(wish) : Result.error(404, "许愿不存在");
    }

    @GetMapping
    public Result<List<WishPool>> getAllWishes() {
        List<WishPool> wishes = wishPoolService.list();
        return Result.success(wishes);
    }

    @GetMapping("/page")
    public Result<Page<WishPool>> getWishesByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<WishPool> page = new Page<>(pageNum, pageSize);
        Page<WishPool> result = wishPoolService.page(page);
        return Result.success(result);
    }

    @GetMapping("/user/{userId}")
    public Result<List<WishPool>> getWishesByUserId(@PathVariable Long userId) {
        LambdaQueryWrapper<WishPool> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WishPool::getUserId, userId);
        wrapper.orderByDesc(WishPool::getCreatedAt);
        List<WishPool> wishes = wishPoolService.list(wrapper);
        return Result.success(wishes);
    }

    @GetMapping("/status/{status}")
    public Result<List<WishPool>> getWishesByStatus(@PathVariable String status) {
        LambdaQueryWrapper<WishPool> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WishPool::getStatus, status);
        wrapper.orderByDesc(WishPool::getCreatedAt);
        List<WishPool> wishes = wishPoolService.list(wrapper);
        return Result.success(wishes);
    }

    @PostMapping
    public Result<WishPool> createWish(@RequestBody WishPool wish) {
        boolean saved = wishPoolService.save(wish);
        return saved ? Result.success(wish) : Result.error(500, "创建许愿失败");
    }

    @PutMapping("/{id}")
    public Result<WishPool> updateWish(@PathVariable Long id, @RequestBody WishPool wish) {
        wish.setId(id);
        boolean updated = wishPoolService.updateById(wish);
        return updated ? Result.success(wish) : Result.error(500, "更新许愿失败");
    }

    @PutMapping("/{id}/status")
    public Result<WishPool> updateWishStatus(@PathVariable Long id, @RequestParam String status) {
        WishPool wish = wishPoolService.getById(id);
        if (wish == null) {
            return Result.error(404, "许愿不存在");
        }
        wish.setStatus(status);
        boolean updated = wishPoolService.updateById(wish);
        return updated ? Result.success(wish) : Result.error(500, "更新许愿状态失败");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteWish(@PathVariable Long id) {
        boolean deleted = wishPoolService.removeById(id);
        return deleted ? Result.success(null) : Result.error(500, "删除许愿失败");
    }

    @DeleteMapping
    public Result<?> deleteWishes(@RequestBody List<Long> ids) {
        boolean deleted = wishPoolService.removeByIds(ids);
        return deleted ? Result.success(null) : Result.error(500, "批量删除许愿失败");
    }

}
