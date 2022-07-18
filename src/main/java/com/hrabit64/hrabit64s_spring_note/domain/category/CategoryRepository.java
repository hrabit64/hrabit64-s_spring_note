package com.hrabit64.hrabit64s_spring_note.domain.category;


import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface CategoryRepository extends MongoRepository<Category,String>, CategoryCustomRepository{

    List<Category> findAllBy();
    List<Category> findAllBy(Sort sort);
    List<Category> findAllByCategoryName(String categoryName);
}
