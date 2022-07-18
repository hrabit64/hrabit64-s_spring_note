package com.hrabit64.hrabit64s_spring_note.web.data.posts;

import com.hrabit64.hrabit64s_spring_note.web.dto.posts.PostsResponseDto;
import lombok.Builder;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
public class PostsOtherPostResponseData implements Serializable {

    public Long postID;
    public String title;
    @Builder
    public PostsOtherPostResponseData(PostsResponseDto posts) {

        this.postID = posts.getPostID();
        this.title = posts.getTitle();
    }

}
