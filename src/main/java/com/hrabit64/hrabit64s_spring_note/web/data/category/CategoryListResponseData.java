package com.hrabit64.hrabit64s_spring_note.web.data.category;

import com.hrabit64.hrabit64s_spring_note.web.dto.category.CategoryResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@ToString
@Data
public class CategoryListResponseData implements Serializable {

    public String categoryID;
    public String categoryName;

    @Builder
    public CategoryListResponseData(CategoryResponseDto categoryResponseDto){
        this.categoryName = categoryResponseDto.getCategoryName();
        this.categoryID = categoryResponseDto.getCategoryID();
    }

}
