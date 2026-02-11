package com.example.family_ording.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("menu_item")
public class MenuItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long menuId;

    private Long dishId;

    private Integer votes;

    private Boolean isConfirmed;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}
