package com.hrabit64.hrabit64s_spring_note.web.dto.posts;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@NoArgsConstructor
public class PostsResponseDto {

    private Long postID;
    private String title;
    private String categoryName;
    private Set<String> tags;
    private String content;
    private Integer view;
    private LocalDateTime createdDateTime;

    @Builder
    public PostsResponseDto(Posts posts) {

        this.postID = posts.getPostID();
        this.title = posts.getTitle();
        this.tags = posts.getTags();
        this.content = posts.getContent();
        this.view = posts.getView();
        this.createdDateTime = posts.getCreatedDateTime();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
