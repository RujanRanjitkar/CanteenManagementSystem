package com.example.canteenmgmtsys.category.service;

import com.example.canteenmgmtsys.category.dto.CategoryRequestDto;
import com.example.canteenmgmtsys.category.dto.CategoryResponseDto;
import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.category.repo.CategoryRepo;
import com.example.canteenmgmtsys.customException.exceptions.ResourceAlreadyExistsException;
import com.example.canteenmgmtsys.foodItem.enums.FoodItemStatus;
import com.example.canteenmgmtsys.foodItem.model.FoodItem;
import com.example.canteenmgmtsys.foodItem.repo.FoodItemRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final FoodItemRepo foodItemRepo;
    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public Category addNewCategory(CategoryRequestDto categoryRequestDto) {

        logger.info("Getting list of foodItem from categoryRequestDto");
        List<FoodItem> foodItemList = categoryRequestDto.getFoodItem();

        Category category = new Category();

        logger.info("Converting categoryRequestDto into category");
        category.setCategoryName(categoryRequestDto.getCategoryName().toLowerCase());
        category.setDescription(categoryRequestDto.getDescription());

        logger.info("Getting existing category according to category name");
        Optional<Category> existingCategory = Optional.ofNullable(categoryRepo.findByCategoryName(category.getCategoryName()));

        List<String> foodItemName=new ArrayList<>();

        if (existingCategory.isPresent()) {
            category = existingCategory.get();

            Long categoryId = existingCategory.get().getCategoryId();

            logger.info("Getting list of food item according to category id from database table");
            List<FoodItem> foodItemLists = foodItemRepo.getFoodItemByCategoryId(categoryId);

            for(FoodItem foodItems: foodItemLists){
                foodItemName.add(foodItems.getFoodItemName());
            }
        }

        for (FoodItem foodItem : foodItemList) {

            if(foodItemName.contains(foodItem.getFoodItemName())){

                logger.error("FoodItem name already exists");
                throw new ResourceAlreadyExistsException(foodItem.getFoodItemName() + " already exists");
            }

            else if (foodItem.getStock() == 0) {

                logger.info("Setting status to out-of-stock if stock is 0");
                foodItem.setFoodItemStatus(FoodItemStatus.OutOfStock);
            }
            else{

                logger.info("Setting foodItem status to available");
                foodItem.setFoodItemStatus(FoodItemStatus.Available);
            }

            logger.info("Setting category to set value of category_id");
            foodItem.setCategory(category);
        }

        category.setFoodItem(foodItemList);

        logger.info("Adding new category in database");
        return categoryRepo.save(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {

        logger.info("Getting all the category list from database table");
        List<Category> categoryList = categoryRepo.findAll();

        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

            categoryResponseDto.setCategoryName(category.getCategoryName());
            categoryResponseDto.setDescription(category.getDescription());

            categoryResponseDtoList.add(categoryResponseDto);
        }

        logger.info("Returning list of category");
        return categoryResponseDtoList;
    }
}
