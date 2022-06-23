package com.hrabit64.hrabit64s_spring_note.domain.posts;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hrabit64.hrabit64s_spring_note.domain.BaseTimeEntity;
import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Document(collection ="posts")
public class Posts extends BaseTimeEntity {

    //게시글의 ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    private Long postID;

    //게시글의 제목
    @NotNull
    @Column(name = "POST_TITLE")
    private String title;

    //게시글의 대분류
    @NotNull
    @ManyToOne
    @JoinColumn(name = "CATEGORY_NM")
    private Category category;

    //게시글의 소분류
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "POST_TAG")
    private Set<String> tags = new HashSet<String>();

    //게시글 본문
    @NotNull
    @Column(name = "POST_CONTENT")
    private String content;

    //게시글 조회수, 기본값은 0
    @Column(name = "POST_VIEW")
    private Integer view = 0;

    @Builder
    public Posts(Long postID, @NotNull String title, @NotNull Category category,
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

    public void setCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }
}
