package com.hrabit64.hrabit64s_spring_note.domain.posts;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends MongoRepository<Posts, ObjectId> {

    List<Posts> findAllByPostID(ObjectId postID);
    Optional<Posts> findByPostID(ObjectId postID);
    List<Posts> findAllByOrderByCreatedDateAsc();
    List<Posts> findAllByTitleContainsOrderByCreatedDateAsc(String title);
    List<Posts> findAllByCategory_CategoryName(String category_categoryName);
    List<Posts> findAllByContentContainsOrderByCreatedDateAsc(String content);
}
