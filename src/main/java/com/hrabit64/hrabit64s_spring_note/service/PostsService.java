package com.hrabit64.hrabit64s_spring_note.service;

import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.domain.posts.PostsRepository;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
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
    public Long add(PostsAddRequestDto postsAddRequestDto){
        return postsRepository.save(postsAddRequestDto.toEntity()).getPostID();
    }


    //post update
    @Transactional
    public Long update(Long postID,PostsUpdateRequestDto postsUpdateRequestDto){
        Posts targetPost = postsRepository.findById(postID)
                .orElseThrow(() -> new IllegalArgumentException("(ID = "+postID+") cannot found......"));

        targetPost.update(postsUpdateRequestDto.getTitle(),
                postsUpdateRequestDto.getMainTag(),
                postsUpdateRequestDto.getSubTags(),
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
    public PostsResponseDto findByPostID(Long postID){
        Posts targetPost = postsRepository.findById(postID)
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

    //find posts By mainTag
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findByMainTag(String mainTag){
        return postsRepository.findAllByMainTagOrderByCreatedDateAsc(mainTag).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    //find posts By mainTag
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findBySubTag(String subTag){
        return postsRepository.findAllBySubTagsOrderByCreatedDateAsc(subTag).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

}
