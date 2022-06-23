package com.hrabit64.hrabit64s_spring_note.domain.posts;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends MongoRepository<Posts,Long> {

    @Override
    Iterable<Posts> findAllById(Iterable<Long> longs);

    @Override
    Optional<Posts> findById(Long aLong);

    List<Posts> findAllByOrderByCreatedDateAsc();

    List<Posts> findAllByTitleContainsOrderByCreatedDateAsc(String title);

    List<Posts> findAllByCategory_CategoryName(String category_categoryName);
    List<Posts> findAllByContentContainsOrderByCreatedDateAsc(String content);
}
