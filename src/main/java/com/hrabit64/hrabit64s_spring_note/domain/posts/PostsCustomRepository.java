package com.hrabit64.hrabit64s_spring_note.domain.posts;


import com.hrabit64.hrabit64s_spring_note.domain.category.Category;

import java.util.List;

public interface PostsCustomRepository {
    Posts findByPostID(Long postID);
    Long addPosts(Posts posts);
    Category findCategoryByCategoryID(String categoryID);
    void delPosts(Posts posts);
    Posts updatePosts(Posts newPosts);
    Posts updatePosts(Posts newPosts,String beforeCategoryID);
    void delAllPosts();
    List<Posts> findPostsOrderByCreatedTime();
    List<Posts> findPostsOrderByView();
}
