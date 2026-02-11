package com.example.family_ording.model.dto;

import lombok.Data;

@Data
public class DishDTO {

    private String name;

    private String description;

    private String type;

    private String difficulty;

    private Integer cookTime;

}
