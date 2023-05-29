package com.example.canteenmgmtsys.order.dto;

import com.example.canteenmgmtsys.customer.model.Customer;
import com.example.canteenmgmtsys.order_foodItem.dto.FoodItemOrderedRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private String customerId;
    private List<FoodItemOrderedRequestDto> foodItems;
}
