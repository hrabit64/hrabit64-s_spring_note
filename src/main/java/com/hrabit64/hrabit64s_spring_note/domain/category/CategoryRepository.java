package com.hrabit64.hrabit64s_spring_note.domain.category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CategoryRepository extends MongoRepository<Category,String> {


    Category findByCategoryName(String categoryName);

    List<Category> findAllBy();
    List<Category> findAllByCategoryName(String categoryName);
}
