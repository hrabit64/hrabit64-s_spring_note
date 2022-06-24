package com.hrabit64.hrabit64s_spring_note.web;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.hrabit64.hrabit64s_spring_note.service.PostsService;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApiController {

    private final PostsService postsService;
    private final CategoryService categoryService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public List<PostsResponseDto> findAllPosts(){

        return postsService.findAllPosts();

    }

    @GetMapping("{postID}")
    public PostsResponseDto findByPostID(@PathVariable ObjectId postID){

        return postsService.findByPostID(postID);

    }

    @PostMapping
    public ObjectId addPosts(@RequestBody PostsAddRequestDto postsAddRequestDto){
        logger.info("POST // addPosts {}",postsAddRequestDto.toString());
        postsAddRequestDto.setCategory(
                categoryService.findByCategoryName(postsAddRequestDto.getCategoryName()));
        logger.info("POST // addPosts {}",postsAddRequestDto.toString());
        return postsService.add(postsAddRequestDto);

    }

    @PutMapping("{postID}")
    public ObjectId updatePosts(@PathVariable ObjectId postID,@RequestBody PostsUpdateRequestDto postsUpdateRequestDto){
        postsUpdateRequestDto.setCategory(
                categoryService.findByCategoryName(postsUpdateRequestDto.getCategoryName()));
        return postsService.update(postID,postsUpdateRequestDto);

    }

    @DeleteMapping("{postID}")
    public ObjectId delPosts(@PathVariable ObjectId postID){

        return postsService.delPosts(postID);

    }


}
