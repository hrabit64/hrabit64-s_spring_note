package com.hrabit64.hrabit64s_spring_note.web.data.category;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.web.dto.category.CategoryPostsResponseDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@ToString
@Data
public class CategoryPostsResponseData implements Serializable {

    public Long postID;
    public String title;
    public Set<String> tags;
    public String content;
    public Integer view;
    public LocalDateTime createdDateTime;

    @Builder
    public CategoryPostsResponseData(CategoryPostsResponseDto categoryPostsResponseDto) {

        this.postID = categoryPostsResponseDto.getPostID();
        this.title = categoryPostsResponseDto.getTitle();
        this.tags = categoryPostsResponseDto.getTags();
        this.content = categoryPostsResponseDto.getContent();
        this.view = categoryPostsResponseDto.getView();
        this.createdDateTime = categoryPostsResponseDto.getCreatedDateTime();
    }

}
