package com.example.canteenmgmtsys.foodItem.controller;

import com.example.canteenmgmtsys.foodItem.dto.FoodItemRequestDto;
import com.example.canteenmgmtsys.foodItem.dto.FoodItemResponseDto;
import com.example.canteenmgmtsys.foodItem.service.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/foodItem")
@RequiredArgsConstructor
public class FoodItemController {
    private final FoodItemService foodItemService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add_new_foodItem")
    public String addNewFoodItem(@RequestBody FoodItemRequestDto foodItemRequestDto) {
        foodItemService.addNewFoodItem(foodItemRequestDto);
        return "Food item added successfully";
    }

    @GetMapping("/get_foodItem_by_category_name/{categoryName}")
    public List<FoodItemResponseDto> getFoodItemByCategoryName(@PathVariable("categoryName") String categoryName) {
        return foodItemService.getFoodItemByCategoryName(categoryName);
    }
}
