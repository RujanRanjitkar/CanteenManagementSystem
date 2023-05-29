package com.example.canteenmgmtsys.foodItem.model;

import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.foodItem.enums.FoodItemStatus;
import com.example.canteenmgmtsys.order_foodItem.model.OrderedFoodItem;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodItemId;

    private String foodItemName;
    private String description;
    private double price;
    private int stock;

    @Enumerated(EnumType.STRING)
    private FoodItemStatus foodItemStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

}
