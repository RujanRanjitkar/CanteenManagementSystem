package com.example.canteenmgmtsys.order_foodItem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItemOrderedResponseDto {
    private String foodItemName;
    private String categoryName;
    private double pricePerQuantity;
    private int quantity;
}
