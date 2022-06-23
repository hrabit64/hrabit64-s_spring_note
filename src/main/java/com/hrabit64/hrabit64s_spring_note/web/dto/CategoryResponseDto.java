package com.hrabit64.hrabit64s_spring_note.web.dto;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private String categoryName;
    private String index;

    @Builder
    public CategoryResponseDto(Category category){
        this.categoryName = category.getCategoryName();
        this.index = category.getIndex();
    }

}
