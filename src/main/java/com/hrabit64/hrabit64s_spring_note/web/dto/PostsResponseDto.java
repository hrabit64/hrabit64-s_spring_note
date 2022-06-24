package com.hrabit64.hrabit64s_spring_note.web.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Set;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@NoArgsConstructor
public class PostsResponseDto {

    private ObjectId postID;
    private String title;
    private Category category;
    private Set<String> tags;
    private String content;
    private Integer view;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;

    @Builder
    public PostsResponseDto(Posts posts) {

        this.postID = posts.getPostID();
        this.title = posts.getTitle();
        this.category = posts.getCategory();
        this.tags = posts.getTags();
        this.content = posts.getContent();
        this.view = posts.getView();
        this.modifiedDate = posts.getModifiedData();
        this.createdDate = posts.getCreatedDate();
    }

}
