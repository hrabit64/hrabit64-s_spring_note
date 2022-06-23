package com.hrabit64.hrabit64s_spring_note.web.dto;


import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private String title;
    private Category category;
    private Set<String> tags;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, Category category, Set<String> tags, String content) {
        this.title = title;
        this.category = category;
        this.tags = tags;
        this.content = content;
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
