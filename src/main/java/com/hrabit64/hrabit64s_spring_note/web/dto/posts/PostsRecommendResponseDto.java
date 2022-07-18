package com.hrabit64.hrabit64s_spring_note.web.dto.posts;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@NoArgsConstructor
public class PostsRecommendResponseDto implements Serializable {

    private Long postID;
    private String title;
    private String categoryName;
    private LocalDateTime createdDateTime;
    private Integer view;

    @Builder
    public PostsRecommendResponseDto(Posts posts) {

        this.postID = posts.getPostID();
        this.title = posts.getTitle();
        this.createdDateTime = posts.getCreatedDateTime();
        this.view = posts.getView();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
