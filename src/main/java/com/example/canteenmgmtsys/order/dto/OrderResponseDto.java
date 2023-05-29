package com.example.canteenmgmtsys.order.dto;

import com.example.canteenmgmtsys.customer.model.Customer;
import com.example.canteenmgmtsys.order_foodItem.dto.FoodItemOrderedResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private String customerId;
    private List<FoodItemOrderedResponseDto> foodItems;
    private double totalPrice;
    private LocalDateTime orderDate;
}
