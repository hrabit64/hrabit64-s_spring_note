package com.hrabit64.hrabit64s_spring_note.domain.category;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends MongoRepository<Category, ObjectId> {


    Optional<Category> findByCategoryName(String categoryName);

    List<Category> findAllBy();
    List<Category> findAllByCategoryName(String categoryName);
}
