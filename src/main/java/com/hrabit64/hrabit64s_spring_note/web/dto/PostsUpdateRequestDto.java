package com.hrabit64.hrabit64s_spring_note.web.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private String title;
    private String categoryName;
    private Category category;
    private Set<String> tags;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String categoryName, Set<String> tags, String content) {
        this.title = title;
        this.categoryName = categoryName;
        this.tags = tags;
        this.content = content;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Posts toEntity(){
        return Posts.builder().
                title(title).
                content(content).
                category(category).
                tags(tags).
                build();
    }
}
