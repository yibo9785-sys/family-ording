package com.example.family_ording.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("shopping_list")
public class ShoppingList {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long menuId;

    private String name;

    private String status;

    private Long creatorId;

    private BigDecimal totalAmount;

    private LocalDate listDate; // 新增字段：购物日期

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)//更新时填充字段
    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deleted;

}
