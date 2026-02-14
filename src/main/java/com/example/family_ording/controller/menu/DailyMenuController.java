package com.example.family_ording.controller.menu;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.family_ording.Result;
import com.example.family_ording.model.entity.DailyMenu;
import com.example.family_ording.service.DailyMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/menu/v1")
public class DailyMenuController {

    @Autowired
    private DailyMenuService dailyMenuService;

    @GetMapping("/{id}")
    public Result<DailyMenu> getMenuById(@PathVariable Long id) {
        DailyMenu menu = dailyMenuService.getMenuById(id);
        return menu != null ? Result.success(menu) : Result.error(404, "菜单不存在");
    }

    @GetMapping
    public Result<List<DailyMenu>> getAllMenus() {
        List<DailyMenu> menus = dailyMenuService.list();
        return Result.success(menus);
    }

    @GetMapping("/page")
    public Result<Page<DailyMenu>> getMenusByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<DailyMenu> page = new Page<>(pageNum, pageSize);
        Page<DailyMenu> result = dailyMenuService.page(page);
        return Result.success(result);
    }

    @GetMapping("/date/{date}")
    public Result<DailyMenu> getMenuByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        LambdaQueryWrapper<DailyMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyMenu::getMenuDate, localDate);
        DailyMenu menu = dailyMenuService.getOne(wrapper);
        return menu != null ? Result.success(menu) : Result.error(404, "该日期菜单不存在");
    }

    @GetMapping("/date-range")
    public Result<List<DailyMenu>> getMenusByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        LambdaQueryWrapper<DailyMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(DailyMenu::getMenuDate, start, end);
        wrapper.orderByDesc(DailyMenu::getMenuDate);
        List<DailyMenu> menus = dailyMenuService.list(wrapper);
        return Result.success(menus);
    }

    @PostMapping
    public Result<DailyMenu> createMenu(@RequestBody DailyMenu menu) {
        boolean saved = dailyMenuService.save(menu);
        return saved ? Result.success(menu) : Result.error(500, "创建菜单失败");
    }

    @PutMapping("/{id}")
    public Result<DailyMenu> updateMenu(@PathVariable Long id, @RequestBody DailyMenu menu) {
        menu.setId(id);
        boolean updated = dailyMenuService.updateById(menu);
        return updated ? Result.success(menu) : Result.error(500, "更新菜单失败");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteMenu(@PathVariable Long id) {
        boolean deleted = dailyMenuService.removeById(id);
        return deleted ? Result.success(null) : Result.error(500, "删除菜单失败");
    }

    @DeleteMapping
    public Result<?> deleteMenus(@RequestBody List<Long> ids) {
        boolean deleted = dailyMenuService.removeByIds(ids);
        return deleted ? Result.success(null) : Result.error(500, "批量删除菜单失败");
    }

}
