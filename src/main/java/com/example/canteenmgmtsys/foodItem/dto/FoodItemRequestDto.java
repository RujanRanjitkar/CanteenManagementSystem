package com.example.canteenmgmtsys.foodItem.dto;

import com.example.canteenmgmtsys.category.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItemRequestDto {
    private String foodItemName;
    private String description;
    private double price;
    private int stock;
    private Category category;
}
