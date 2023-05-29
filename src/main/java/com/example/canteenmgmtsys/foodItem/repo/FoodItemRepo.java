package com.example.canteenmgmtsys.foodItem.repo;

import com.example.canteenmgmtsys.foodItem.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepo extends JpaRepository<FoodItem, Long> {

    @Query(value = "select * from food_item where category_id=?1", nativeQuery = true)
    List<FoodItem> getFoodItemByCategoryId(Long categoryId);

    @Query(value = "select * from food_item where food_item_name=?1 and category_id=?2",nativeQuery = true)
    FoodItem findByFoodItemNameAndCategoryId(String foodName, Long categoryId);
}
