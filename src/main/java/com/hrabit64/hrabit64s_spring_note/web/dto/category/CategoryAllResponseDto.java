package com.hrabit64.hrabit64s_spring_note.web.dto.category;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CategoryAllResponseDto {

    private String categoryName;
    private String index;
    private Integer postsCnt;
    private String categoryID;

    @Builder
    public CategoryAllResponseDto(Category category){
        this.categoryName = category.getCategoryName();
        this.index = category.getIndex();
        this.postsCnt = category.getPostsID().size();
        this.categoryID = category.getCategoryID();
    }
}
