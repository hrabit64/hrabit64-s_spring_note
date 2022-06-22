package com.hrabit64.hrabit64s_spring_note.domain.posts;

import com.hrabit64.hrabit64s_spring_note.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@Entity
@Document("POST")
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
    @Column(name = "POST_MAIN_TAG")
    private String mainTag;

    //게시글의 소분류
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "POST_SUB_TAG")
    private Set<String> subTags = new HashSet<String>();

    //게시글 본문
    @NotNull
    @Column(name = "POST_CONTENT")
    private String content;

    //게시글 조회수, 기본값은 0
    @Column(name = "POST_VIEW")
    private Integer view = 0;

    @Builder
    public Posts(Long postID, @NotNull String title, @NotNull String mainTag, Set<String> subTags, @NotNull String content, Integer view) {
        this.postID = postID;
        this.title = title;
        this.mainTag = mainTag;
        this.subTags = subTags;
        this.content = content;
        this.view = view;
    }

    public void update(String title, String mainTag, Set<String> subTags, String content){
        this.title = title;
        this.mainTag = mainTag;
        this.subTags = subTags;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + postID +
                ", title='" + title + '\'' +
                ", mainTag='" + mainTag + '\'' +
                ", subTags='" + subTags + '\'' +
                ", content='" + content + '\'' +
                ", view=" + view +
                '}';
    }
}
