package com.example.family_ording.controller.ingredient;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.family_ording.Result;
import com.example.family_ording.model.entity.Ingredient;
import com.example.family_ording.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient/v1")
public class IngredientController {

    @Autowired//自动注入
    private IngredientService ingredientService;

    @GetMapping("/{id}")//获取单个食材
    public Result<Ingredient> getIngredientById(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getIngredientById(id);
        return ingredient != null ? Result.success(ingredient) : Result.error(404, "食材不存在");
    }

    @GetMapping
    public Result<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.list();
        return Result.success(ingredients);
    }

    @GetMapping("/page")
    public Result<Page<Ingredient>> getIngredientsByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Ingredient> page = new Page<>(pageNum, pageSize);
        Page<Ingredient> result = ingredientService.page(page);
        return Result.success(result);
    }

    @GetMapping("/category/{category}")
    public Result<List<Ingredient>> getIngredientsByCategory(@PathVariable String category) {
        LambdaQueryWrapper<Ingredient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Ingredient::getCategory, category);
        List<Ingredient> ingredients = ingredientService.list(wrapper);
        return Result.success(ingredients);
    }

    @GetMapping("/low-stock")
    public Result<List<Ingredient>> getLowStockIngredients(
            @RequestParam(defaultValue = "10") int threshold) {
        LambdaQueryWrapper<Ingredient> wrapper = new LambdaQueryWrapper<>();
        wrapper.le(Ingredient::getQuantity, threshold);
        List<Ingredient> ingredients = ingredientService.list(wrapper);
        return Result.success(ingredients);
    }

    @PostMapping
    public Result<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        boolean saved = ingredientService.save(ingredient);
        return saved ? Result.success(ingredient) : Result.error(500, "创建食材失败");
    }

    @PutMapping("/{id}")
    public Result<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        ingredient.setId(id);
        boolean updated = ingredientService.updateById(ingredient);
        return updated ? Result.success(ingredient) : Result.error(500, "更新食材失败");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteIngredient(@PathVariable Long id) {
        boolean deleted = ingredientService.removeById(id);
        return deleted ? Result.success(null) : Result.error(500, "删除食材失败");
    }

    @DeleteMapping
    public Result<?> deleteIngredients(@RequestBody List<Long> ids) {
        boolean deleted = ingredientService.removeByIds(ids);
        return deleted ? Result.success(null) : Result.error(500, "批量删除食材失败");
    }

}
