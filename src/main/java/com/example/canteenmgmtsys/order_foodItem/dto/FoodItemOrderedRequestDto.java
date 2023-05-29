package com.example.canteenmgmtsys.order_foodItem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItemOrderedRequestDto {
    private String foodItemName;
    private String categoryName;
    private int quantity;
}
