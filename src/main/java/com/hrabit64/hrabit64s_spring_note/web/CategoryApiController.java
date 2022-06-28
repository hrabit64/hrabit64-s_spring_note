package com.hrabit64.hrabit64s_spring_note.web;

import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryPostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryApiController {

    @Autowired
    private final CategoryService categoryService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAllCategory(){

        logger.info("GET");
        List<CategoryResponseDto> categoryResponseDto;
        try {
            categoryResponseDto = categoryService.findAllCategory();
        } catch (NullPointerException nullPointerException){
            logger.info("can't find category......");
            return new ResponseEntity<List<CategoryResponseDto>>(null,null, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<List<CategoryResponseDto>>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<CategoryResponseDto>>(categoryResponseDto,null,HttpStatus.OK);

    }

    @GetMapping("{categoryName}/posts")
    public ResponseEntity<List<CategoryPostsResponseDto>> findPostsByCategoryName(@PathVariable String categoryName){
        logger.info("GET {} 's posts",categoryName);
        List<CategoryPostsResponseDto> categoryPostsResponseDto;
        try {
            categoryPostsResponseDto = categoryService.findPostsByCategoryName(categoryName);
        } catch (NullPointerException nullPointerException){
            logger.info("can't find category...... request category name {}",categoryName);
            return new ResponseEntity<List<CategoryPostsResponseDto>>(null,null, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<List<CategoryPostsResponseDto>>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<CategoryPostsResponseDto>>(categoryPostsResponseDto,null,HttpStatus.OK);

    }
    @GetMapping("{categoryName}")
    public ResponseEntity<CategoryResponseDto> findByCategoryName(@PathVariable String categoryName){
        logger.info("GET {}",categoryName);
        CategoryResponseDto categoryResponseDto;
        try {
            categoryResponseDto = categoryService.findByCategoryName(categoryName);
        } catch (NullPointerException nullPointerException){
            logger.info("can't find category...... request category name {}",categoryName);
            return new ResponseEntity<CategoryResponseDto>(null,null, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<CategoryResponseDto>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseDto>(categoryResponseDto,null,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<String> addCategory
            (@RequestBody @Validated CategoryAddRequestDto categoryAddRequestDto, BindingResult bindingResult){

        logger.debug("POST {}",categoryAddRequestDto.toString());

        if(bindingResult.hasErrors()) return new ResponseEntity<String>(null,null, HttpStatus.BAD_REQUEST);

        String categoryName;

        try {
            categoryName = categoryService.add(categoryAddRequestDto);
        } catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<String>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(categoryName,null, HttpStatus.OK);

    }

    @PutMapping("{categoryName}")
    public ResponseEntity<String> updateCategory
            (@PathVariable String categoryName,
             @RequestBody @Validated CategoryUpdateRequestDto categoryUpdateRequestDto, BindingResult bindingResult){

        logger.debug("PUT {}",categoryUpdateRequestDto.toString());

        if(bindingResult.hasErrors()) return new ResponseEntity<String>(null,null, HttpStatus.BAD_REQUEST);

        String newCategoryName;

        try {
            newCategoryName = categoryService.updateCategory(categoryName,categoryUpdateRequestDto);
        } catch (NullPointerException nullPointerException) {
            logger.info("can't find category...... request category name {}", categoryName);
            return new ResponseEntity<String>(null, null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<String>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(newCategoryName,null, HttpStatus.OK);

    }

    @DeleteMapping("{categoryName}")
    public ResponseEntity<String> delCategory (@PathVariable String categoryName) {
        logger.debug("DELETE {}",categoryName);

        try {
            categoryService.delCategory(categoryName);
        } catch (NullPointerException nullPointerException) {
            logger.info("can't find category...... request category name {}", categoryName);
            return new ResponseEntity<String>(null, null, HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            logger.info("can't delete {}! it has some posts", categoryName);
            return new ResponseEntity<String>("Target category has some posts", null, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            logger.error("INTERNAL SERVER ERROR! {}",e.toString());
            return new ResponseEntity<String>(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(categoryName,null, HttpStatus.OK);

    }

}
