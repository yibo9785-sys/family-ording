package com.example.family_ording.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DishVO {

    private Long id;

    private String name;

    private String description;

    private String type;

    private String difficulty;

    private Integer cookTime;

    private Integer healthScore;

    private LocalDateTime createdAt;

}
