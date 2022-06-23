package com.hrabit64.hrabit64s_spring_note.domain.category;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.BaseTimeEntity;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Document(collection ="category")

public class Category extends BaseTimeEntity {

    //게시글의 ID
    @Id
    @Column(name = "CATEGORY_NM")
    private String categoryName;

    @Column(name = "CATEGORY_INDEX")
    private String index = "입력된 설명이 없습니다!";

    @OneToMany(mappedBy = "category")
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
