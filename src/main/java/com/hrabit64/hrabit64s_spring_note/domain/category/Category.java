package com.hrabit64.hrabit64s_spring_note.domain.category;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.BaseTimeEntity;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
@Document(collection ="category")

public class Category extends BaseTimeEntity {

    //게시글의 ID
    @Id
    @Field("_id")
    private ObjectId id;

    @UniqueElements
    @Field("CATEGORY_CATEGORY_NM")
    private String categoryName;

    @Field("CATEGORY_INDEX")
    private String index = "입력된 설명이 없습니다!";

    @DocumentReference(lazy = true)
    @Field("POSTS")
    private List<Posts> posts = new ArrayList<Posts>();

    @Builder
    public Category(String categoryName,String index) {
        this.categoryName = categoryName;
        this.index = index;
    }
    public void update(String categoryName,String index){

        this.categoryName = categoryName;
        this.index = index;
    }

}
