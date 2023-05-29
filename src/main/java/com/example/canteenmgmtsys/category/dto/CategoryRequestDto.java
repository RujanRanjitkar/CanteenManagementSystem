package com.example.canteenmgmtsys.category.dto;

import com.example.canteenmgmtsys.foodItem.model.FoodItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestDto {

    private String categoryName;
    private List<FoodItem> foodItem;
    private String description;
}
