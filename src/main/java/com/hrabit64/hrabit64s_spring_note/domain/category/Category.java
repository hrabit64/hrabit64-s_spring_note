package com.hrabit64.hrabit64s_spring_note.domain.category;


import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
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

    public void setCategoryID(String categoryID){
        this.categoryID = categoryID;
    }

}