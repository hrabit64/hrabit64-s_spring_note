package com.hrabit64.hrabit64s_spring_note.service;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.domain.category.CategoryRepository;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //category add
    @Transactional
    public String add(CategoryAddRequestDto categoryAddRequestDto){

        return categoryRepository.save(categoryAddRequestDto.toEntity()).getCategoryName();
    }


    //category update
    @Transactional
    public String update(String categoryName,CategoryUpdateRequestDto categoryUpdateRequestDto){
        Category targetCategory = categoryRepository.findByCategoryName(categoryName)
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

    //find Category
    @Transactional(readOnly = true)
    public Category findByCategoryName(String categoryName){
        Category targetCategory = categoryRepository.findByCategoryName(categoryName).
                orElseThrow(() -> new IllegalArgumentException("(ID = "+categoryName+") cannot found......"));
        logger.info("FIND // addPosts {}",targetCategory.toString());
        return targetCategory;
    }

    //del category
    @Transactional
    public String delete(String categoryName){
        Category targetCategory = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("(ID = "+categoryName+") cannot found......"));
        categoryRepository.delete(targetCategory);
        return categoryName;
    }


}
