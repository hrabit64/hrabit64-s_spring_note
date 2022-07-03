package com.hrabit64.hrabit64s_spring_note.service;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.domain.category.CategoryRepository;
import com.hrabit64.hrabit64s_spring_note.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * add new category, and return new category's name
     * @param categoryAddRequestDto new category's info
     * @return Created category's category name
     */
    @Transactional
    public String add(CategoryAddRequestDto categoryAddRequestDto){

        return categoryRepository.save(categoryAddRequestDto.toEntity()).getCategoryName();
    }

    /**
     * find all category
     * @return all category's info
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAllCategory(){
        return categoryRepository.findAllBy().stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }


    /**
     * find posts, by category name
     * @param categoryName target category name
     * @return posts info
     */
    @Transactional(readOnly = true)
    public List<CategoryPostsResponseDto> findPostsByCategoryName(String categoryName){
        Category targetCategory = categoryRepository.findByCategoryName(categoryName);
        return categoryRepository.findAllPostsByCategoryID(targetCategory.getCategoryID())
                .stream()
                .map(CategoryPostsResponseDto::new)
                .collect(Collectors.toList());
    }


    /**
     * find category by category's name
     * @param categoryName target category's name
     * @return category's info
     */
    @Transactional(readOnly = true)
    public CategoryResponseDto findByCategoryName(String categoryName){
        return new CategoryResponseDto(categoryRepository.findByCategoryName(categoryName));

    }

    @Transactional
    public String updateCategory(String categoryName,CategoryUpdateRequestDto categoryUpdateRequestDto){
        Category targetCategory = categoryRepository.findByCategoryName(categoryName);
        targetCategory.update(categoryUpdateRequestDto.getCategoryName(),categoryUpdateRequestDto.getIndex());
        return categoryRepository.updateCategory(targetCategory).getCategoryName();

    }

    @Transactional
    public void delCategory(String categoryName){
        Category targetCategory = categoryRepository.findByCategoryName(categoryName);
        if(targetCategory.getPostsID().size() != 0) throw new IllegalArgumentException();
        categoryRepository.delCategory(targetCategory);
    }

    @Transactional
    public void delAllCategory(){
        categoryRepository.delAllCategory();
    }
}
