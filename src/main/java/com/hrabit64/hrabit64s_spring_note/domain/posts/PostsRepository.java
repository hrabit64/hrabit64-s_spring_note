package com.hrabit64.hrabit64s_spring_note.domain.posts;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostsRepository extends MongoRepository<Posts,Long>, PostsCustomRepository {

    List<Posts> findAllBy();
}
