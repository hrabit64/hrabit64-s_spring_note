package com.hrabit64.hrabit64s_spring_note.web;

import com.hrabit64.hrabit64s_spring_note.service.PostsService;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApiController {

    private final PostsService postsService;

    @GetMapping
    public List<PostsResponseDto> findAllPosts(){
        return postsService.findAllPosts();
    }

    @GetMapping("{postID}")
    public PostsResponseDto findByPostID(@RequestParam(name = "postID") Long postID){
        return postsService.findByPostID(postID);
    }

    @GetMapping("{mainTag}")
    public List<PostsResponseDto> findByMainTag(@PathVariable String mainTag){
        return postsService.findByMainTag(mainTag);
    }

    //TODO 검색엔진 추가하기

}
