package com.example.family_ording.model.entity;

import lombok.Data;

@Data
public class Dish {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String difficulty;
    private Integer cookTime;
}
