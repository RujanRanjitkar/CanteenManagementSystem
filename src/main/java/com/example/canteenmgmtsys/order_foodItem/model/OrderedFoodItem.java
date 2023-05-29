package com.example.canteenmgmtsys.order_foodItem.model;

import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.foodItem.model.FoodItem;
import com.example.canteenmgmtsys.order.model.Order;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_food_items")
public class OrderedFoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private int quantity;

}
