package com.example.canteenmgmtsys.foodItem.service;

import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.category.repo.CategoryRepo;
import com.example.canteenmgmtsys.customException.exceptions.ResourceAlreadyExistsException;
import com.example.canteenmgmtsys.customException.exceptions.ResourceNotFoundException;
import com.example.canteenmgmtsys.foodItem.dto.FoodItemRequestDto;
import com.example.canteenmgmtsys.foodItem.dto.FoodItemResponseDto;
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
public class FoodItemServiceImpl implements FoodItemService {
    private final FoodItemRepo foodItemRepo;
    private final CategoryRepo categoryRepo;

    Logger logger = LoggerFactory.getLogger(FoodItemServiceImpl.class);

    @Override
    public FoodItem addNewFoodItem(FoodItemRequestDto foodItemRequestDto) {

        Category category = new Category();

        logger.info("Setting category");
        category.setCategoryName(foodItemRequestDto.getCategory().getCategoryName().toLowerCase());
        category.setDescription(foodItemRequestDto.getCategory().getDescription());

        logger.info("Getting existing category according to category name");
        Optional<Category> existingCategory = Optional.ofNullable(categoryRepo.findByCategoryName(category.getCategoryName()));

        FoodItem foodItem = new FoodItem();

        logger.info("Converting foodItemRequestDto into foodItem");
        foodItem.setFoodItemName(foodItemRequestDto.getFoodItemName());
        foodItem.setFoodItemStatus(FoodItemStatus.Available);
        foodItem.setDescription(foodItemRequestDto.getDescription());
        foodItem.setPrice(foodItemRequestDto.getPrice());
        foodItem.setStock(foodItemRequestDto.getStock());

        if (foodItem.getStock() == 0) {

            logger.info("Setting status to out-of-stock if stock is 0");
            foodItem.setFoodItemStatus(FoodItemStatus.OutOfStock);
        }

        List<String> foodItemName = new ArrayList<>();

        if (existingCategory.isPresent()) {

            logger.info("Setting existing category if categoryName already exists");
            foodItem.setCategory(existingCategory.get());

            Long categoryId = existingCategory.get().getCategoryId();

            logger.info("Getting list of food item according to category id from database table");
            List<FoodItem> foodItemList = foodItemRepo.getFoodItemByCategoryId(categoryId);

            for (FoodItem foodItems : foodItemList) {
                foodItemName.add(foodItems.getFoodItemName());
            }

            if (foodItemName.contains(foodItemRequestDto.getFoodItemName())) {

                logger.error("FoodItem name already exists");
                throw new ResourceAlreadyExistsException(foodItemRequestDto.getFoodItemName() + " already exists");
            }
        } else {

            logger.info("Setting new category if categoryName does not exists");
            foodItem.setCategory(category);
        }

        logger.info("Adding new foodItem in database");
        return foodItemRepo.save(foodItem);
    }

    @Override
    public List<FoodItemResponseDto> getFoodItemByCategoryName(String categoryName) {

        logger.info("Getting category by name from database table");
        Optional<Category> category = Optional.ofNullable(categoryRepo.findByCategoryName(categoryName.toLowerCase()));

        if (!category.isPresent()) {

            logger.error("Category does not exists");
            throw new ResourceNotFoundException("Category " + categoryName + " does not exists");
        }

        Long categoryId = category.get().getCategoryId();

        logger.info("Getting list of food item according to category id from database table");
        List<FoodItem> foodItemList = foodItemRepo.getFoodItemByCategoryId(categoryId);

        List<FoodItemResponseDto> foodItemResponseDtoList = new ArrayList<>();

        for (FoodItem foodItem : foodItemList) {
            FoodItemResponseDto foodItemResponseDto = new FoodItemResponseDto();

            foodItemResponseDto.setFoodItemName(foodItem.getFoodItemName());
            foodItemResponseDto.setPrice(foodItem.getPrice());
            foodItemResponseDto.setDescription(foodItem.getDescription());
            foodItemResponseDto.setFoodItemStatus(foodItem.getFoodItemStatus());
            foodItemResponseDto.setStock(foodItem.getStock());

            logger.info("Adding foodItemResponseDto in foodItemResponseDtoList");
            foodItemResponseDtoList.add(foodItemResponseDto);
        }

        logger.info("Returning list of food item");
        return foodItemResponseDtoList;
    }
}
