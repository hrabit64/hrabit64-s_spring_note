package com.hrabit64.hrabit64s_spring_note.domain.category;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.mongodb.lang.NonNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.index.Indexed;
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
public class Category{


    @Id
    @Field("_id")
    private String categoryID;

    @Indexed(unique = true)
    @Field("CATEGORY_CATEGORY_NM")
    private String categoryName;

    @Field("CATEGORY_INDEX")
    private String index = "입력된 설명이 없습니다!";

    @Field("POST_POST_ID")
    private List<Long> postsID = new ArrayList<Long>();

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

