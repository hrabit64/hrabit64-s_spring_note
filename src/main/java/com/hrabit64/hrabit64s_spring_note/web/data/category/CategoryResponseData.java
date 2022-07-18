package com.hrabit64.hrabit64s_spring_note.web.data.category;

import com.hrabit64.hrabit64s_spring_note.web.dto.category.CategoryResponseDto;
import lombok.*;

import java.io.Serializable;


@ToString
@Data
public class CategoryResponseData implements Serializable {

    public String categoryID;
    public String categoryName;
    public String index;

    @Builder
    public CategoryResponseData(CategoryResponseDto categoryResponseDto){
        this.categoryName = categoryResponseDto.getCategoryName();
        this.index = categoryResponseDto.getIndex();
        this.categoryID = categoryResponseDto.getCategoryID();
    }

}
