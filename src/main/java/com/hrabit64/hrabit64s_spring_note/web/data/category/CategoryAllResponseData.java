package com.hrabit64.hrabit64s_spring_note.web.data.category;

import com.hrabit64.hrabit64s_spring_note.web.dto.category.CategoryAllResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class CategoryAllResponseData implements Serializable {

    public String categoryName;
    public String index;
    public Integer postsCnt;
    public String categoryID;

    @Builder
    public CategoryAllResponseData(CategoryAllResponseDto categoryAllResponseDto){
        categoryName = categoryAllResponseDto.getCategoryName();
        index = categoryAllResponseDto.getIndex();
        postsCnt = categoryAllResponseDto.getPostsCnt();
        categoryID = categoryAllResponseDto.getCategoryID();
    }
}
