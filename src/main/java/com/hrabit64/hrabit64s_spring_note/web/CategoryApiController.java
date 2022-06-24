package com.hrabit64.hrabit64s_spring_note.web;

import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.hrabit64.hrabit64s_spring_note.service.PostsService;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryUpdateRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryApiController {

    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public List<CategoryResponseDto> findAllCategory(){

        return categoryService.findAllCategory();
    }

    @GetMapping("{categoryName}")
    public CategoryResponseDto findByCategoryName(@PathVariable String categoryName){
        return new CategoryResponseDto(categoryService.findByCategoryName(categoryName));

    }

    @PostMapping
    public String addCategory(@RequestBody @Validated CategoryAddRequestDto categoryAddRequestDto){
        logger.debug("POST// {}",categoryAddRequestDto.toString());
        return categoryService.add(categoryAddRequestDto);

    }

    @PutMapping("{categoryName}")
    public String updateCategory(@PathVariable String categoryName,
                                 @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto){

        return categoryService.update(categoryName,categoryUpdateRequestDto);

    }

    @DeleteMapping("{categoryName}")
    public String delCategory(@PathVariable String categoryName){

        return categoryService.delete(categoryName);

    }
}
