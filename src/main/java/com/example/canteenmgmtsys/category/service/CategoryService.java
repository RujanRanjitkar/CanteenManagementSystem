package com.example.canteenmgmtsys.category.service;

import com.example.canteenmgmtsys.category.dto.CategoryRequestDto;
import com.example.canteenmgmtsys.category.dto.CategoryResponseDto;
import com.example.canteenmgmtsys.category.model.Category;

import java.util.List;

public interface CategoryService {
    Category addNewCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> getAllCategory();
}
