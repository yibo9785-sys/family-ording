package com.example.family_ording.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("dish_ingredient")
public class DishIngredient {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long dishId;

    private Long ingredientId;

    private BigDecimal quantity;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

}
