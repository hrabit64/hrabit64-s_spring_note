package com.hrabit64.hrabit64s_spring_note.web;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.hrabit64.hrabit64s_spring_note.service.PostsService;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApiController {

    private final PostsService postsService;
    private final CategoryService categoryService;

    @GetMapping
    public List<PostsResponseDto> findAllPosts(){

        return postsService.findAllPosts();

    }

    @GetMapping("{postID}")
    public PostsResponseDto findByPostID(@PathVariable Long postID){

        return postsService.findByPostID(postID);

    }

    @PostMapping
    public Long addPosts(@RequestBody PostsAddRequestDto postsAddRequestDto){

        return postsService.add(postsAddRequestDto);

    }

    @PutMapping("{postID}")
    public Long updatePosts(@PathVariable Long postID,@RequestBody PostsUpdateRequestDto postsUpdateRequestDto){

        return postsService.update(postID,postsUpdateRequestDto);

    }

    @DeleteMapping("{postID}")
    public Long delPosts(@PathVariable Long postID){

        return postsService.delPosts(postID);

    }



    //TODO 검색엔진 추가하기

}
