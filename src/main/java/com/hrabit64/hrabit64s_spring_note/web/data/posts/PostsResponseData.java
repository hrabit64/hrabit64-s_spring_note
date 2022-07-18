package com.hrabit64.hrabit64s_spring_note.web.data.posts;


import com.hrabit64.hrabit64s_spring_note.web.dto.posts.PostsResponseDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Data
public class PostsResponseData implements Serializable {

    public Long postID;
    public String title;
    public String categoryName;
    public Integer view;
    public LocalDateTime createdDateTime;
    public String content;
    @Builder
    public PostsResponseData(PostsResponseDto posts) {

        this.postID = posts.getPostID();
        this.title = posts.getTitle();
        this.view = posts.getView();
        this.categoryName = posts.getCategoryName();
        this.createdDateTime = posts.getCreatedDateTime();
        this.content = posts.getContent();
    }
}
