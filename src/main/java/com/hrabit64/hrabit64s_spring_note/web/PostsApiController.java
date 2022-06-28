package com.hrabit64.hrabit64s_spring_note.web;

import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.hrabit64.hrabit64s_spring_note.service.PostsService;
import com.hrabit64.hrabit64s_spring_note.service.SequenceGeneratorService;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryPostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApiController {

    @Autowired
    private final PostsService postsService;

    @Autowired
    private final SequenceGeneratorService sequenceGeneratorService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<PostsResponseDto>> findAllPosts(){
        logger.info("GET");
        List<PostsResponseDto> postsResponseDto;
        try {
            postsResponseDto = postsService.findAllPosts();
        } catch (NullPointerException nullPointerException){
            logger.info("can't find posts");
            return new ResponseEntity<List<PostsResponseDto>>(null,null, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<List<PostsResponseDto>>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<PostsResponseDto>>(postsResponseDto,null, HttpStatus.OK);

    }

    @GetMapping("{postID}")
    public ResponseEntity<PostsResponseDto> findByPostID(@PathVariable Long postID){

        logger.info("GET post id = {}",postID);
        PostsResponseDto postsResponseDto;
        try {
            postsResponseDto = postsService.findByPostID(postID);
        } catch (NullPointerException nullPointerException){
            logger.info("can't find posts");
            return new ResponseEntity<PostsResponseDto>(null,null, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<PostsResponseDto>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PostsResponseDto>(postsResponseDto,null, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Long> addPosts
            (@RequestBody @Valid PostsAddRequestDto postsAddRequestDto, BindingResult bindingResult){

        logger.info("POST Posts {}",postsAddRequestDto.toString());

        if(bindingResult.hasErrors()) return new ResponseEntity<Long>(null,null, HttpStatus.BAD_REQUEST);

        Long postID;

        try {
            postsAddRequestDto.setPostID(sequenceGeneratorService.generateSequence(Posts.SEQUENCE_NAME));
            postID = postsService.add(postsAddRequestDto);
        } catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<Long>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Long>(postID,null, HttpStatus.OK);
    }

    @DeleteMapping("{postID}")
    public ResponseEntity<Long> delPost(@PathVariable Long postID){
        logger.info("DELETE Posts {}",postID);
        try {
            postsService.delPostByPostID(postID);
        } catch (NullPointerException nullPointerException){
            logger.info("can't find posts");
            return new ResponseEntity<Long>(null,null, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<Long>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Long>(postID,null, HttpStatus.OK);
    }

    @PutMapping("{postID}")
    public ResponseEntity<Long> updatePost(@PathVariable Long postID,
                                           @RequestBody @Valid PostsUpdateRequestDto postsUpdateRequestDto,
                                           BindingResult bindingResult){
        logger.info("PUT Posts {}",postID);

        if(bindingResult.hasErrors()) return new ResponseEntity<Long>(null,null, HttpStatus.BAD_REQUEST);

        try {
            postsService.updatePost(postID,postsUpdateRequestDto);
        } catch (NullPointerException nullPointerException){
            logger.info("can't find posts");
            return new ResponseEntity<Long>(null,null, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<Long>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Long>(postID,null, HttpStatus.OK);
    }

}
