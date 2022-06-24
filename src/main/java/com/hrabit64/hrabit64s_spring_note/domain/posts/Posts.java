package com.hrabit64.hrabit64s_spring_note.domain.posts;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.BaseTimeEntity;
import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Document(collection ="posts")
public class Posts extends BaseTimeEntity {

    //게시글의 ID
    @Id
    @Field("_id")
    private ObjectId postID;

    //게시글의 제목
    @NotNull
    @Field("POST_TITLE")
    private String title;

    //게시글의 대분류
    @DocumentReference(lazy = true)
    @Field("CATEGORY_CATEGORY_NM")
    private Category category;

    //게시글의 소분류
    @ElementCollection(fetch = FetchType.LAZY)
    @Field("POST_TAG")
    private Set<String> tags = new HashSet<String>();

    //게시글 본문
    @Field("POST_CONTENT")
    private String content;

    //게시글 조회수, 기본값은 0
    @Field("POST_VIEW")
    private Integer view = 0;

    @Builder
    public Posts(ObjectId postID, @NotNull String title, @NotNull Category category,
                 Set<String> tags, @NotNull String content, Integer view) {
        this.postID = postID;
        this.title = title;
        this.category = category;
        this.tags = tags;
        this.content = content;
        this.view = view;
    }

    public void update(String title, Category category, Set<String> tags, String content){
        this.title = title;
        this.category = category;
        this.tags = tags;
        this.content = content;
    }

    public void setCategory() {
        this.category.getPosts().add(this);
    }
}
