package com.hrabit64.hrabit64s_spring_note.web.dto.category;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private String categoryID;
    private String categoryName;
    private String index;
    private List<Long> posts;

    @Builder
    public CategoryResponseDto(Category category){
        this.categoryName = category.getCategoryName();
        this.index = category.getIndex();
        this.categoryID = category.getCategoryID();
        this.posts = category.getPostsID();
    }

}
