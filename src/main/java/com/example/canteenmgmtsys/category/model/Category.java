package com.example.canteenmgmtsys.category.model;

import com.example.canteenmgmtsys.foodItem.model.FoodItem;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<FoodItem> foodItem;

    private String categoryName;

    private String description;
}
