package com.example.family_ording.controller.shopping;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.family_ording.Result;
import com.example.family_ording.model.entity.ShoppingList;
import com.example.family_ording.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shopping/v1")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @GetMapping("/{id}")
    public Result<ShoppingList> getListById(@PathVariable Long id) {
        ShoppingList list = shoppingListService.getListById(id);
        return list != null ? Result.success(list) : Result.error(404, "购物清单不存在");
    }

    @GetMapping
    public Result<List<ShoppingList>> getAllLists() {
        List<ShoppingList> lists = shoppingListService.list();
        return Result.success(lists);
    }

    @GetMapping("/page")
    public Result<Page<ShoppingList>> getListsByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<ShoppingList> page = new Page<>(pageNum, pageSize);
        Page<ShoppingList> result = shoppingListService.page(page);
        return Result.success(result);
    }

    @GetMapping("/date/{date}")//获取指定日期的购物清单
    public Result<List<ShoppingList>> getListsByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        LambdaQueryWrapper<ShoppingList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingList::getListDate, localDate);
        List<ShoppingList> lists = shoppingListService.list(wrapper);
        return Result.success(lists);
    }

    @GetMapping("/date-range")
    public Result<List<ShoppingList>> getListsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        LambdaQueryWrapper<ShoppingList> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(ShoppingList::getListDate, start, end);
        wrapper.orderByDesc(ShoppingList::getListDate);
        List<ShoppingList> lists = shoppingListService.list(wrapper);
        return Result.success(lists);
    }

    @GetMapping("/status/{status}")//获取指定状态的购物清单
    public Result<List<ShoppingList>> getListsByStatus(@PathVariable String status) {
        LambdaQueryWrapper<ShoppingList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingList::getStatus, status);
        wrapper.orderByDesc(ShoppingList::getListDate);
        List<ShoppingList> lists = shoppingListService.list(wrapper);
        return Result.success(lists);
    }

    @PostMapping
    public Result<ShoppingList> createList(@RequestBody ShoppingList list) {
        boolean saved = shoppingListService.save(list);
        return saved ? Result.success(list) : Result.error(500, "创建购物清单失败");
    }

    @PutMapping("/{id}")
    public Result<ShoppingList> updateList(@PathVariable Long id, @RequestBody ShoppingList list) {
        list.setId(id);
        boolean updated = shoppingListService.updateById(list);
        return updated ? Result.success(list) : Result.error(500, "更新购物清单失败");
    }

    @PutMapping("/{id}/status")
    public Result<ShoppingList> updateListStatus(@PathVariable Long id, @RequestParam String status) {
        ShoppingList list = shoppingListService.getById(id);
        if (list == null) {
            return Result.error(404, "购物清单不存在");
        }
        list.setStatus(status);
        boolean updated = shoppingListService.updateById(list);
        return updated ? Result.success(list) : Result.error(500, "更新购物清单状态失败");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteList(@PathVariable Long id) {
        boolean deleted = shoppingListService.removeById(id);
        return deleted ? Result.success(null) : Result.error(500, "删除购物清单失败");
    }

    @DeleteMapping
    public Result<?> deleteLists(@RequestBody List<Long> ids) {
        boolean deleted = shoppingListService.removeByIds(ids);
        return deleted ? Result.success(null) : Result.error(500, "批量删除购物清单失败");
    }

}
