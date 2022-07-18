package com.hrabit64.hrabit64s_spring_note.web.data.posts;


import com.hrabit64.hrabit64s_spring_note.web.dto.posts.PostsRecentlyResponseDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Data
public class PostsRecentlyResponseData implements Serializable {

    public Long postID;
    public String title;
    public String categoryName;
    public LocalDateTime createdDateTime;
    public Integer view;

    public PostsRecentlyResponseData(PostsRecentlyResponseDto postsRecentlyResponseDto){
        postID = postsRecentlyResponseDto.getPostID();
        title = postsRecentlyResponseDto.getTitle();
        categoryName = postsRecentlyResponseDto.getCategoryName();
        createdDateTime = postsRecentlyResponseDto.getCreatedDateTime();
        view = postsRecentlyResponseDto.getView();
    }
}
