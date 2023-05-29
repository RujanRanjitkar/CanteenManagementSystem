package com.example.canteenmgmtsys.order.dto;

import com.example.canteenmgmtsys.order_foodItem.dto.FoodItemOrderedResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderByCustomerIdResponseDto {
    private List<FoodItemOrderedResponseDto> foodItems;
    private double totalPrice;
    private LocalDateTime orderDate;
}
