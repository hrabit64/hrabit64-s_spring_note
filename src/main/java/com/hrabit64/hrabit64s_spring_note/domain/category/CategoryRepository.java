package com.hrabit64.hrabit64s_spring_note.domain.category;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends MongoRepository<Category,String>, CategoryCustomRepository{

    List<Category> findAllBy();
    List<Category> findAllByCategoryName(String categoryName);
}
