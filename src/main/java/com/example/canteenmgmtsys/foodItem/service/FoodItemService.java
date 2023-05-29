package com.example.canteenmgmtsys.foodItem.service;

import com.example.canteenmgmtsys.foodItem.dto.FoodItemRequestDto;
import com.example.canteenmgmtsys.foodItem.dto.FoodItemResponseDto;
import com.example.canteenmgmtsys.foodItem.model.FoodItem;

import java.util.List;

public interface FoodItemService {
    FoodItem addNewFoodItem(FoodItemRequestDto foodItemRequestDto);

    List<FoodItemResponseDto> getFoodItemByCategoryName(String categoryName);
}
