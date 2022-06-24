package com.hrabit64.hrabit64s_spring_note.service;

import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.domain.posts.PostsRepository;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    //post add
    @Transactional
    public ObjectId add(PostsAddRequestDto postsAddRequestDto){
        Posts newPost = postsAddRequestDto.toEntity();

        newPost.setCategory();
        return postsRepository.save(newPost).getPostID();
    }


    //post update
    @Transactional
    public ObjectId update(ObjectId postID, PostsUpdateRequestDto postsUpdateRequestDto){
        Posts targetPost = postsRepository.findByPostID(postID)
                .orElseThrow(() -> new IllegalArgumentException("(ID = "+postID+") cannot found......"));

        targetPost.update(postsUpdateRequestDto.getTitle(),
                postsUpdateRequestDto.getCategory(),
                postsUpdateRequestDto.getTags(),
                postsUpdateRequestDto.getContent());

        return postID;
    }

    //find all post
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAllPosts(){
        return postsRepository.findAllByOrderByCreatedDateAsc().stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }


    //find post by postID
    @Transactional(readOnly = true)
    public PostsResponseDto findByPostID(ObjectId postID){
        Posts targetPost = postsRepository.findByPostID(postID)
                .orElseThrow(() -> new IllegalArgumentException("(ID = "+postID+") cannot found......"));
        return new PostsResponseDto(targetPost);
    }

    //find posts By title
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findByTitle(String title){
        return postsRepository.findAllByTitleContainsOrderByCreatedDateAsc(title).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    //find posts By content
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findByContent(String content){
        return postsRepository.findAllByContentContainsOrderByCreatedDateAsc(content).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    //find posts By category
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findByCategory(String category){
        return postsRepository.findAllByCategory_CategoryName(category).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    //post remove
    @Transactional
    public ObjectId delPosts(ObjectId postID){
        Posts targetPost = postsRepository.findByPostID(postID)
                .orElseThrow(() -> new IllegalArgumentException("(ID = "+postID+") cannot found......"));
        postsRepository.delete(targetPost);
        return postID;
    }
}
