package com.hrabit64.hrabit64s_spring_note.service;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.domain.category.CategoryRepository;
import com.hrabit64.hrabit64s_spring_note.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    //category add
    @Transactional
    public String add(CategoryAddRequestDto categoryAddRequestDto){
        return categoryRepository.save(categoryAddRequestDto.toEntity()).getCategoryName();
    }


    //category update
    @Transactional
    public String update(String categoryName,CategoryUpdateRequestDto categoryUpdateRequestDto){
        Category targetCategory = categoryRepository.findById(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("(ID = "+categoryName+") cannot found......"));

        targetCategory.update(
                categoryUpdateRequestDto.getCategoryName(),
                categoryUpdateRequestDto.getIndex()
        );

        return categoryName;
    }

    //find all Category
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAllCategory(){
        return categoryRepository.findAllBy().stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }

    //find all Category
    @Transactional(readOnly = true)
    public Category findByCategoryName(String categoryName){
        return categoryRepository.findByCategoryName(categoryName);
    }

    //find all Category
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAllCategoryByCategoryName(String categoryName){
        return categoryRepository.findAllByCategoryName(categoryName).stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }

    //del category
    @Transactional
    public String delete(String categoryName){
        categoryRepository.deleteById(categoryName);
        return categoryName;
    }


}
