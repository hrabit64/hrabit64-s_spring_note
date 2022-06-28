package com.hrabit64.hrabit64s_spring_note.domain.posts;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsUpdateRequestDto;
import com.mongodb.lang.NonNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;


import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
@Document(collection ="posts")
public class Posts{

    @Transient
    public static final String SEQUENCE_NAME = "posts_sequence";


    /**
     * post's id
     */
    @Id
    private Long postID;

    /**
     * post's title
     */
    @Field("POST_TITLE")
    private String title;

    /**
     * post's category's id
     */
    @Field("CATEGORY_CATEGORY_ID")
    private String categoryID;

    /**
     * post's tags
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @Field("POST_TAG")
    private Set<String> tags = new HashSet<String>();

    /**
     * post's contents
     */
    @Field("POST_CONTENT")
    private String content;

    /**
     * post's view, default value is 0
     */
    @Field("POST_VIEW")
    private Integer view = 0;

    @Field("POST_CREATE_DATE")
    private LocalDateTime createdDateTime;


    @Builder
    public Posts(Long postID,@NotNull String title, @NotNull String categoryID,
                 Set<String> tags, @NotNull String content, Integer view) {
        this.postID = postID;
        this.title = title;
        this.tags = tags;
        this.content = content;
        this.view = view;
        this.categoryID = categoryID;
    }


    public void update(String title, String categoryID, Set<String> tags, String content){
        this.title = title;
        this.categoryID = categoryID;
        this.tags = tags;
        this.content = content;
    }

    public void update(PostsUpdateRequestDto postsUpdateRequestDto){
        this.title = postsUpdateRequestDto.getTitle();
        this.categoryID = postsUpdateRequestDto.getCategoryID();
        this.tags = postsUpdateRequestDto.getTags();
        this.content = postsUpdateRequestDto.getContent();
    }
    public void setCategoryID(String categoryID){
        this.categoryID = categoryID;
    }
    public void setCreatedDateTime(LocalDateTime createdDateTime){this.createdDateTime = createdDateTime;}

}
