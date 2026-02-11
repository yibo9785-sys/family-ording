package com.example.family_ording.controller.dish;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.family_ording.Result;
import com.example.family_ording.model.entity.Dish;
import com.example.family_ording.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish/v1")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/{id}")
    public Result<Dish> getDishById(@PathVariable Long id) {
        Dish dish = dishService.getDishById(id);
        return dish != null ? Result.success(dish) : Result.error(404, "菜品不存在");
    }

    @GetMapping
    public Result<List<Dish>> getAllDishes() {
        List<Dish> dishes = dishService.list();
        return Result.success(dishes);
    }

    @GetMapping("/page")
    public Result<Page<Dish>> getDishesByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Dish> page = new Page<>(pageNum, pageSize);
        Page<Dish> result = dishService.page(page);
        return Result.success(result);
    }

    @GetMapping("/type/{type}")
    public Result<List<Dish>> getDishesByType(@PathVariable String type) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getType, type);
        List<Dish> dishes = dishService.list(wrapper);
        return Result.success(dishes);
    }

    @PostMapping
    public Result<Dish> createDish(@RequestBody Dish dish) {
        boolean saved = dishService.save(dish);
        return saved ? Result.success(dish) : Result.error(500, "创建菜品失败");
    }

    @PutMapping("/{id}")
    public Result<Dish> updateDish(@PathVariable Long id, @RequestBody Dish dish) {
        dish.setId(id);
        boolean updated = dishService.updateById(dish);
        return updated ? Result.success(dish) : Result.error(500, "更新菜品失败");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteDish(@PathVariable Long id) {
        boolean deleted = dishService.removeById(id);
        return deleted ? Result.success(null) : Result.error(500, "删除菜品失败");
    }

    @DeleteMapping
    public Result<?> deleteDishes(@RequestBody List<Long> ids) {
        boolean deleted = dishService.removeByIds(ids);
        return deleted ? Result.success(null) : Result.error(500, "批量删除菜品失败");
    }

}
