package com.hrabit64.hrabit64s_spring_note.domain.category;

import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;

import java.util.List;


public interface CategoryCustomRepository{
    List<Posts> findAllPostsByCategoryID(String categoryName);
    Category findByCategoryName(String categoryName);
    Category findByCategoryID(String categoryID);
    Category updateCategory(Category newCategory);
    void delCategory(Category category);
    void delAllCategory();
}
