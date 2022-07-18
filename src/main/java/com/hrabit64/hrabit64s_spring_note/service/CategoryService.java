package com.hrabit64.hrabit64s_spring_note.service;

import com.hrabit64.hrabit64s_spring_note.config.GlobalConfig;
import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.domain.category.CategoryRepository;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.domain.posts.PostsRepository;
import com.hrabit64.hrabit64s_spring_note.web.dto.category.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final PostsRepository postsRepository;

    @Autowired
    private final GlobalConfig globalConfig;

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
     * find all category with Sort option
     * @return all category's info
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAllCategory(Sort sort){
        return categoryRepository.findAllBy(sort).stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * find all category and add category's id's cnt
     * @return all category's info
     */
    @Transactional(readOnly = true)
    public List<CategoryAllResponseDto> findAllCategoryAddPostCnt(){
        return categoryRepository.findAllBy().stream()
                .map(CategoryAllResponseDto::new)
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
     * find posts, by category id
     * @param categoryID target category id
     * @return posts info
     */
    @Transactional(readOnly = true)
    public List<CategoryPostsResponseDto> findPostsByCategoryID(String categoryID){
        Category targetCategory = categoryRepository.findByCategoryID(categoryID);
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

    /**
     * find category by category's id
     * @param categoryID target category's id
     * @return category's info
     */
    @Transactional(readOnly = true)
    public CategoryResponseDto findByCategoryID(String categoryID){
        return new CategoryResponseDto(categoryRepository.findByCategoryID(categoryID));

    }

    @Transactional
    public String updateCategory(String categoryName, CategoryUpdateRequestDto categoryUpdateRequestDto){
        Category targetCategory = categoryRepository.findByCategoryName(categoryName);
        targetCategory.update(categoryUpdateRequestDto.getCategoryName(),categoryUpdateRequestDto.getIndex());
        return categoryRepository.updateCategory(targetCategory).getCategoryName();

    }

    @Transactional
    public void delCategory(String categoryID){
        if(categoryID.equals(globalConfig.getDefaultCategory()))
            throw new IllegalArgumentException();

        Category targetCategory = categoryRepository.findByCategoryID(categoryID);

        if(targetCategory.getPostsID().size() != 0) {
            Category defaultCategory = categoryRepository.findByCategoryID(globalConfig.getDefaultCategory());
            for (Long postID:targetCategory.getPostsID()) {
                Posts post = postsRepository.findByPostID(postID);
                post.setCategoryID(globalConfig.getDefaultCategory());
                postsRepository.updatePosts(post);
                defaultCategory.getPostsID().add(postID);
            }
            logger.debug("{}",defaultCategory.getPostsID().toString());
            categoryRepository.updateCategory(defaultCategory);
        }
        categoryRepository.delCategory(targetCategory);
    }

    @Transactional
    public void delAllCategory(){
        categoryRepository.delAllCategory();
    }
}
