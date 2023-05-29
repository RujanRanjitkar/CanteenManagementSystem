package com.example.canteenmgmtsys.order.util;

import com.example.canteenmgmtsys.foodItem.model.FoodItem;
import org.springframework.stereotype.Component;

@Component
public class Util {
    public double calculateTotalPriceOfOrderList(FoodItem foodItem, int quantity) {
        double sum = 0.0;
        sum += foodItem.getPrice() * quantity;
        return sum;
    }
}
