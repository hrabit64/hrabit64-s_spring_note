package com.hrabit64.hrabit64s_spring_note.domain.posts;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostsRepository extends MongoRepository<Posts,Long>, PostsCustomRepository {

    List<Posts> findAllBy();
    List<Posts> findAllBy(Sort sort);
}
