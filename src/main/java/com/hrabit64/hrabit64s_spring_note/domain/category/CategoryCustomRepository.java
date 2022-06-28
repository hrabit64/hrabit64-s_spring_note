package com.hrabit64.hrabit64s_spring_note.domain.category;

import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryUpdateRequestDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryCustomRepository{
    List<Posts> findAllPostsByCategoryID(String categoryName);
    Category findByCategoryName(String categoryName);
    Category findByCategoryID(String categoryID);
    Category updateCategory(Category newCategory);
    void delCategory(Category category);
}
