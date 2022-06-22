package com.hrabit64.hrabit64s_spring_note.web.dto;


import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
public class PostsResponseDto {

    private Long postID;
    private String title;
    private String mainTag;
    private Set<String> subTags;
    private String content;
    private Integer view;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;

    @Builder
    public PostsResponseDto(Posts posts) {

        this.postID = posts.getPostID();
        this.title = posts.getTitle();
        this.mainTag = posts.getMainTag();
        this.subTags = posts.getSubTags();
        this.content = posts.getContent();
        this.view = posts.getView();
        this.modifiedDate = posts.getModifiedData();
        this.createdDate = posts.getCreatedDate();
    }

}
