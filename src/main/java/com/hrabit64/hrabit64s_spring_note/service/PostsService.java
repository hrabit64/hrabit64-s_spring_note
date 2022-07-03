package com.hrabit64.hrabit64s_spring_note.service;

import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.domain.posts.PostsRepository;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        return postsRepository.findAllBy().stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
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
    public Long updatePost(Long postID,PostsUpdateRequestDto postsUpdateRequestDto){
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
