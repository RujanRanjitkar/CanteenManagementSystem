package com.example.canteenmgmtsys.foodItem.dto;

import com.example.canteenmgmtsys.foodItem.enums.FoodItemStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItemResponseDto {
    private String foodItemName;
    private String description;
    private double price;
    private int stock;
    private FoodItemStatus foodItemStatus;
}
