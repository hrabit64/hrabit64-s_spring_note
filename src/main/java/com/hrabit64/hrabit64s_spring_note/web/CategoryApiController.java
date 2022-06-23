package com.hrabit64.hrabit64s_spring_note.web;

import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.hrabit64.hrabit64s_spring_note.service.PostsService;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.CategoryUpdateRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDto> findAllCategory(){

        return categoryService.findAllCategory();
    }

    @GetMapping("{categoryName}")
    public List<CategoryResponseDto> findAllByCategoryName(@PathVariable String categoryName){

        return categoryService.findAllCategoryByCategoryName(categoryName);

    }

    @PostMapping
    public String addCategory(@RequestBody CategoryAddRequestDto categoryAddRequestDto){

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
