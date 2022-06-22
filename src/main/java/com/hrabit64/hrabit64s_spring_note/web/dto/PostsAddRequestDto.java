package com.hrabit64.hrabit64s_spring_note.web.dto;


import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class PostsAddRequestDto {

    private String title;
    private String mainTag;
    private Set<String> subTags;
    private String content;

    @Builder
    public PostsAddRequestDto(String title, String mainTag, Set<String> subTags, String content) {
        this.title = title;
        this.mainTag = mainTag;
        this.subTags = subTags;
        this.content = content;
    }

    public Posts toEntity(){
        return Posts.builder().
                title(title).
                content(content).
                mainTag(mainTag).
                subTags(subTags).
                build();
    }
}
