package com.hrabit64.hrabit64s_spring_note.web.data.posts;


import com.hrabit64.hrabit64s_spring_note.web.dto.posts.PostsRecommendResponseDto;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Data
public class PostsRecommendResponseData implements Serializable {

    public Long postID;
    public String title;
    public String categoryName;
    public LocalDateTime createdDateTime;
    public Integer view;

    public PostsRecommendResponseData(PostsRecommendResponseDto postsRecommendResponseDto){
        postID = postsRecommendResponseDto.getPostID();
        title = postsRecommendResponseDto.getTitle();
        categoryName = postsRecommendResponseDto.getCategoryName();
        createdDateTime = postsRecommendResponseDto.getCreatedDateTime();
        view = postsRecommendResponseDto.getView();
    }
}
