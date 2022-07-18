package com.hrabit64.hrabit64s_spring_note.web;

import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.hrabit64.hrabit64s_spring_note.service.PostsService;
import com.hrabit64.hrabit64s_spring_note.service.SequenceGeneratorService;
import com.hrabit64.hrabit64s_spring_note.utils.PageMaker;
import com.hrabit64.hrabit64s_spring_note.web.data.category.CategoryAllResponseData;
import com.hrabit64.hrabit64s_spring_note.web.data.category.CategoryPostsResponseData;
import com.hrabit64.hrabit64s_spring_note.web.data.category.CategoryResponseData;
import com.hrabit64.hrabit64s_spring_note.web.data.posts.PostsOtherPostResponseData;
import com.hrabit64.hrabit64s_spring_note.web.data.posts.PostsRecentlyResponseData;
import com.hrabit64.hrabit64s_spring_note.web.data.posts.PostsRecommendResponseData;
import com.hrabit64.hrabit64s_spring_note.web.data.posts.PostsResponseData;
import com.hrabit64.hrabit64s_spring_note.web.dto.category.CategoryAllResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.category.CategoryPostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.posts.PostsRecentlyResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.posts.PostsRecommendResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class MVCController {

    @Autowired
    private final PostsService postsService;

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private final PageMaker pageMaker;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index(Model model){
        List<PostsRecentlyResponseData> postsRecentlyResponseData = new ArrayList<>();
        List<PostsRecentlyResponseDto> recentlyPosts = postsService.findRecentlyPosts();
        for (PostsRecentlyResponseDto recentlyPost:recentlyPosts) {
            postsRecentlyResponseData.add(new PostsRecentlyResponseData(recentlyPost));
        }
        logger.debug(postsRecentlyResponseData.toString());

        List<PostsRecommendResponseData> postsRecommendResponseData = new ArrayList<>();
        List<PostsRecommendResponseDto> recommendPosts = postsService.findRecommendPosts();
        for (PostsRecommendResponseDto recommendPost:recommendPosts) {
            postsRecommendResponseData.add(new PostsRecommendResponseData(recommendPost));
        }
        logger.debug(postsRecentlyResponseData.toString());
        model.addAttribute("recently_posts",postsRecentlyResponseData);
        model.addAttribute("recommend_posts",postsRecommendResponseData);
        return "index";
    }

    @GetMapping("/posts")
    public String posts(Model model, @PageableDefault(page = 0, size = 20) Pageable pageable){
        List<PostsResponseDto> postsResponseDtos = postsService.findAllPosts(Sort.by(Sort.Direction.DESC,"createdDateTime"));
        List<PostsResponseData> postsResponseDatas = new ArrayList<>();
        for (PostsResponseDto postsResponseDto:postsResponseDtos) {
            postsResponseDatas.add(new PostsResponseData(postsResponseDto));
        }

        Page<PostsResponseData> page = (Page<PostsResponseData>) pageMaker.toPage(postsResponseDatas,pageable);

        model.addAttribute("posts",page);
        return "posts";
    }

    @GetMapping("/categories")
    public String categories(Model model, @PageableDefault(page = 0, size = 20) Pageable pageable){
        List<CategoryAllResponseDto> categoryAllResponseDtos = categoryService.findAllCategoryAddPostCnt();
        List<CategoryAllResponseData> categoryAllResponseDatas = new ArrayList<>();
        for (CategoryAllResponseDto categoryAllResponseDto:categoryAllResponseDtos) {
            categoryAllResponseDatas.add(new CategoryAllResponseData(categoryAllResponseDto));
        }

        Page<PostsResponseData> page = (Page<PostsResponseData>)
                pageMaker.toPage(
                        categoryAllResponseDatas.stream()
                                .sorted(Comparator.comparing(CategoryAllResponseData::getPostsCnt).reversed())
                                .collect(Collectors.toList())
                        ,pageable);

        model.addAttribute("categories",page);
        return "categories";
    }

    @GetMapping("/categories/{categoryID}/posts")
    public String categories(Model model,@PathVariable String categoryID, @PageableDefault(page = 0, size = 20) Pageable pageable){
        //category model
        CategoryResponseData categoryResponseData = new CategoryResponseData(categoryService.findByCategoryID(categoryID));

        //posts model
        List<CategoryPostsResponseDto> categoryPostsResponseDtos = categoryService.findPostsByCategoryID(categoryID);

        List<CategoryPostsResponseData> categoryPostsResponseDatas = new ArrayList<>();
        for (CategoryPostsResponseDto categoryPostsResponseDto:categoryPostsResponseDtos) {
            categoryPostsResponseDatas.add(new CategoryPostsResponseData(categoryPostsResponseDto));
        }

        Page<PostsResponseData> page = (Page<PostsResponseData>)
                pageMaker.toPage(
                        categoryPostsResponseDatas.stream()
                                .sorted(Comparator.comparing(CategoryPostsResponseData::getCreatedDateTime).reversed())
                                .collect(Collectors.toList())
                        ,pageable);

        model.addAttribute("category",categoryResponseData);
        model.addAttribute("posts",page);

        return "categories-posts";
    }

    @GetMapping("/posts/{postID}")
    public String categories(Model model,@PathVariable Long postID){
        //post model
        PostsResponseDto postsResponseDto = postsService.findByPostID(postID);
        PostsResponseData postsResponseData = new PostsResponseData(postsResponseDto);
        model.addAttribute("post",postsResponseData);

        //other posts
        List<Long> otherCategories = categoryService.findByCategoryName(postsResponseDto.getCategoryName()).getPosts();

        Collections.sort(otherCategories);

        Integer targetPostLoc = otherCategories.indexOf(postsResponseData.getPostID());
        Long nextPostID = null;
        Long prevPostID = null;

        if(targetPostLoc+1 <otherCategories.size()){
            nextPostID = otherCategories.get(targetPostLoc+1);
        }

        if(targetPostLoc -1 >= 0){
            prevPostID = otherCategories.get(targetPostLoc-1);
        }

        model.addAttribute("nextPost",(nextPostID != null) ?
            new PostsOtherPostResponseData(postsService.findByPostID(nextPostID)) : null );

        model.addAttribute("prevPost",(prevPostID != null) ?
            new PostsOtherPostResponseData(postsService.findByPostID(prevPostID)) : null );

        return "post-view";
    }
    @GetMapping("/post/write")
    public String addPost(Model model){
        List<CategoryResponseData> categoryListResponseData =
                categoryService.findAllCategory().stream()
                .map(CategoryResponseData::new)
                .collect(Collectors.toList());
        model.addAttribute("categories",categoryListResponseData);
        return "post-write";
    }

    @GetMapping("/post/{postID}/edit")
    public String editPost(Model model ,@PathVariable Long postID){

        PostsResponseDto postsResponseDto = postsService.findByPostID(postID);
        PostsResponseData postsResponseData = new PostsResponseData(postsResponseDto);

        model.addAttribute("post",postsResponseData);

        List<CategoryResponseData> categoryListResponseData =
                categoryService.findAllCategory().stream()
                        .map(CategoryResponseData::new)
                        .collect(Collectors.toList());

        model.addAttribute("categories",categoryListResponseData);
        return "post-edit";
    }
}
