package com.hrabit64.hrabit64s_spring_note.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@NoArgsConstructor
public class CategoryUpdateRequestDto {

    @NotEmpty
    private String categoryName;
    private String index;

    @Builder
    public CategoryUpdateRequestDto(String categoryName, String index){

        this.categoryName = categoryName;
        this.index = index;
    }

    public Category toEntity(){
        return Category.builder().categoryName(categoryName).index(index).build();
    }
}
