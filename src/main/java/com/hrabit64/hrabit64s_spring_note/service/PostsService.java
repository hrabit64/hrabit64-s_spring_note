package com.hrabit64.hrabit64s_spring_note.service;

import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.domain.posts.PostsRepository;
import com.hrabit64.hrabit64s_spring_note.web.dto.posts.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    @Autowired
    private final PostsRepository postsRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * add new post
     * @param postsAddRequestDto new post info
     * @return new post's id
     */
    @Transactional
    public Long add(PostsAddRequestDto postsAddRequestDto){
        Posts newPost = postsAddRequestDto.toEntity();
        newPost.setCreatedDateTime(LocalDateTime.now());
        return postsRepository.addPosts(newPost);
    }


    /**
     * find all post
     * @return posts info
     */
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAllPosts(){
        List<Posts> posts = postsRepository.findAllBy();
        List<PostsResponseDto> returnPosts = new ArrayList<PostsResponseDto>();
        for (Posts targetPost: posts) {
            PostsResponseDto indexPost = new PostsResponseDto(targetPost);
            indexPost.setCategoryName(postsRepository.findCategoryByCategoryID(targetPost.getCategoryID()).getCategoryName());
            returnPosts.add(indexPost);
        }
        return returnPosts;
    }

    /**
     * find all post
     * @return posts info
     */
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAllPosts(Sort sort){
        List<Posts> posts = postsRepository.findAllBy(sort);
        List<PostsResponseDto> returnPosts = new ArrayList<PostsResponseDto>();
        for (Posts targetPost: posts) {
            PostsResponseDto indexPost = new PostsResponseDto(targetPost);
            indexPost.setCategoryName(postsRepository.findCategoryByCategoryID(targetPost.getCategoryID()).getCategoryName());
            returnPosts.add(indexPost);
        }
        return returnPosts;
    }

    /**
     * find post by post id
     * @param postID target post id
     * @return post info
     */
    @Transactional(readOnly = true)
    public PostsResponseDto findByPostID(Long postID){

        Posts targetPost = postsRepository.findByPostID(postID);
        PostsResponseDto responsePost = new PostsResponseDto(targetPost);
        responsePost.setCategoryName(postsRepository.findCategoryByCategoryID(targetPost.getCategoryID()).getCategoryName());
        return responsePost;
    }

    @Transactional(readOnly = true)
    public List<PostsRecentlyResponseDto> findRecentlyPosts(){

        List<Posts> targetPosts = postsRepository.findPostsOrderByCreatedTime();
        List<PostsRecentlyResponseDto> returnPosts = new ArrayList<PostsRecentlyResponseDto>();
        for (Posts targetPost: targetPosts) {
            PostsRecentlyResponseDto indexPost = new PostsRecentlyResponseDto(targetPost);
            indexPost.setCategoryName(postsRepository.findCategoryByCategoryID(targetPost.getCategoryID()).getCategoryName());
            returnPosts.add(indexPost);
        }
        return returnPosts;
    }

    @Transactional(readOnly = true)
    public List<PostsRecommendResponseDto> findRecommendPosts(){

        List<Posts> targetPosts = postsRepository.findPostsOrderByView();
        List<PostsRecommendResponseDto> returnPosts = new ArrayList<PostsRecommendResponseDto>();
        for (Posts targetPost: targetPosts) {
            PostsRecommendResponseDto indexPost = new PostsRecommendResponseDto(targetPost);
            indexPost.setCategoryName(postsRepository.findCategoryByCategoryID(targetPost.getCategoryID()).getCategoryName());
            returnPosts.add(indexPost);
        }
        return returnPosts;
    }
    /**
     * delete post
     * @param postID target post
     */
    @Transactional
    public void delPostByPostID(Long postID){
        Posts targetPost = postsRepository.findByPostID(postID);
        postsRepository.delPosts(targetPost);
    }

    @Transactional
    public Long updatePost(Long postID, PostsUpdateRequestDto postsUpdateRequestDto){
        Posts targetPost = postsRepository.findByPostID(postID);
        String beforeCategory =
                (targetPost.getCategoryID().equals(postsUpdateRequestDto.getCategoryID()))?
                null : targetPost.getCategoryID();

        targetPost.update(postsUpdateRequestDto);
        if(beforeCategory != null) return postsRepository.updatePosts(targetPost,beforeCategory).getPostID();
        return postsRepository.updatePosts(targetPost).getPostID();
    }

    @Transactional
    public void delAllPosts(){
        postsRepository.delAllPosts();
    }
}
