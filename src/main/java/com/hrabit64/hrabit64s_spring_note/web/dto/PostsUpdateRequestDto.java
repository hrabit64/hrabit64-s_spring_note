package com.hrabit64.hrabit64s_spring_note.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {


    @NotEmpty
    private String title;

    @NotEmpty
    private String categoryID;
    private Set<String> tags;

    @NotEmpty
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String categoryID, Set<String> tags, String content) {
        this.title = title;
        this.categoryID = categoryID;
        this.tags = tags;
        this.content = content;
    }



}
