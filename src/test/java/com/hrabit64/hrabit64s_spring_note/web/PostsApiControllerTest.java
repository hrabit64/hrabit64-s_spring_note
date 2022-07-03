package com.hrabit64.hrabit64s_spring_note.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrabit64.hrabit64s_spring_note.TestUtils;
import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import com.hrabit64.hrabit64s_spring_note.domain.category.CategoryRepository;
import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import com.hrabit64.hrabit64s_spring_note.domain.posts.PostsRepository;
import com.hrabit64.hrabit64s_spring_note.service.CategoryService;
import com.hrabit64.hrabit64s_spring_note.service.PostsService;
import com.hrabit64.hrabit64s_spring_note.service.SequenceGeneratorService;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.PostsUpdateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Profile("test")
@Slf4j
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TestUtils testUtils = new TestUtils();
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostsService postsService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private ObjectMapper mapper;


    @LocalServerPort
    private String port;

    @AfterEach
    void tearDown() {
        postsRepository.delAllPosts();
        categoryRepository.delAllCategory();
        sequenceGeneratorService.resetSequence(Posts.SEQUENCE_NAME);
    }

    @Test
    void findAllPosts() throws Exception {
        //given
        Category testCategory = Category.builder()
                .categoryName("test")
                .index("this is test category")
                .build();

        categoryRepository.save(testCategory);

        String testCategoryID = categoryRepository.findByCategoryName(testCategory.getCategoryName()).getCategoryID();

        Posts testPost1 = Posts.builder()
                .postID(1L)
                .title("test case 1")
                .categoryID(testCategoryID)
                .view(0)
                .content("this is test for findAllPosts. and I also 시공 좋아")
                .tags(new HashSet<String>(Arrays.asList("test","sigong")))
                .build();

        postsRepository.addPosts(testPost1);

        Posts testPost2 = Posts.builder()
                .postID(2L)
                .title("test case 2")
                .categoryID(testCategoryID)
                .view(0)
                .content("this is test for findAllPosts. and I also 시공 좋아")
                .tags(new HashSet<String>(Arrays.asList("test","sigong")))
                .build();

        postsRepository.addPosts(testPost2);

        PostsResponseDto testCase1 = new PostsResponseDto(testPost1);
        testCase1.setCategoryName("test");
        PostsResponseDto testCase2 = new PostsResponseDto(testPost2);
        testCase2.setCategoryName("test");
        List<PostsResponseDto> testCase = new ArrayList<PostsResponseDto>();
        testCase.add(testCase1);
        testCase.add(testCase2);

        logger.debug("given // {} \n {}",testPost1.toString(),testPost2.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/posts";
        logger.debug("when // GET {}",url);
        ResponseEntity<PostsResponseDto[]> responseEntity = testRestTemplate.getForEntity(url, PostsResponseDto[].class);
        List<PostsResponseDto> responseBody = Arrays.asList(responseEntity.getBody());

        //then
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertTrue(testUtils.isListSame(responseBody,testCase));

    }

    @Test
    void findByPostID() {
        //given
        Category testCategory = Category.builder()
                .categoryName("test")
                .index("this is test category")
                .build();

        categoryRepository.save(testCategory);

        String testCategoryID = categoryRepository.findByCategoryName(testCategory.getCategoryName()).getCategoryID();

        Posts testPost = Posts.builder()
                .postID(1L)
                .title("test case 1")
                .categoryID(testCategoryID)
                .view(0)
                .content("this is test for findAllPosts. and I also 시공 좋아")
                .tags(new HashSet<String>(Arrays.asList("test","sigong")))
                .build();

        postsRepository.addPosts(testPost);
        PostsResponseDto testCase = new PostsResponseDto(testPost);
        testCase.setCategoryName(categoryRepository.findByCategoryName("test").getCategoryName());
        logger.debug("given // {}",testPost.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/posts/1";
        logger.debug("when // GET {}",url);
        ResponseEntity<PostsResponseDto> responseEntity = testRestTemplate.getForEntity(url, PostsResponseDto.class);
        PostsResponseDto responseBody = responseEntity.getBody();

        //then
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        Assertions.assertEquals(testCase.toString(),responseBody.toString());
    }

    @Test
    void addPosts() {
        //given
        Category testCategory = Category.builder()
                .categoryName("test")
                .index("this is test category")
                .build();

        categoryRepository.save(testCategory);

        String testCategoryID = categoryRepository.findByCategoryName(testCategory.getCategoryName()).getCategoryID();

        Posts testPost = Posts.builder()
                .postID(1L)
                .title("test case 1")
                .categoryID(testCategoryID)
                .view(0)
                .content("this is test for findAllPosts. and I also 시공 좋아")
                .tags(new HashSet<String>(Arrays.asList("test","sigong")))
                .build();

        PostsAddRequestDto testCase = PostsAddRequestDto.builder()
                .categoryID(testPost.getCategoryID())
                .content(testPost.getContent())
                .tags(testPost.getTags())
                .title(testPost.getTitle())
                .build();

        logger.debug("given // {}",testCase.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/posts";
        logger.debug("when // POST {}",url);
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url,testCase,String.class);
        String responseBody = responseEntity.getBody();

        //then
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        Assertions.assertEquals("1",responseBody);
        testPost.setCreatedDateTime(postsRepository.findByPostID(1L).getCreatedDateTime());
        Assertions.assertEquals(testPost.toString(),postsRepository.findByPostID(1L).toString());
    }

    @Test
    void delPost() {
        //given
        Category testCategory = Category.builder()
                .categoryName("test")
                .index("this is test category")
                .build();

        categoryRepository.save(testCategory);

        String testCategoryID = categoryRepository.findByCategoryName(testCategory.getCategoryName()).getCategoryID();

        Posts testPost = Posts.builder()
                .postID(1L)
                .title("test case 1")
                .categoryID(testCategoryID)
                .view(0)
                .content("this is test for findAllPosts. and I also 시공 좋아")
                .tags(new HashSet<String>(Arrays.asList("test","sigong")))
                .build();
        postsRepository.addPosts(testPost);

        logger.debug("given // {}",testPost.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/posts/1";
        logger.debug("when // DEL {}",url);
        testRestTemplate.delete(url);

        //then
        Assertions.assertTrue(postsRepository.findByPostID(1L) == null);
    }

    @Test
    void updatePost() {
        //given
        Category testCategory = Category.builder()
                .categoryName("test")
                .index("this is test category")
                .build();

        categoryRepository.save(testCategory);
        String testCategoryID = categoryRepository.findByCategoryName(testCategory.getCategoryName()).getCategoryID();

        Posts beforePost = Posts.builder()
                .postID(1L)
                .title("test case 1")
                .categoryID(testCategoryID)
                .view(0)
                .content("this is test for findAllPosts. and I also 시공 좋아")
                .tags(new HashSet<String>(Arrays.asList("test","sigong")))
                .build();

        postsRepository.addPosts(beforePost);
        Posts afterPosts = postsRepository.findByPostID(1L);

        afterPosts.setContent("Changed Post");
        PostsUpdateRequestDto testCase = PostsUpdateRequestDto.builder()
                .title(afterPosts.getTitle())
                .content(afterPosts.getContent())
                .tags(afterPosts.getTags())
                .categoryID(afterPosts.getCategoryID())
                .build();

        logger.debug("given // before : {}\nafter : {}",beforePost.toString(),testCase.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/posts/1";
        logger.debug("when // PUT {}",url);
        testRestTemplate.put(url, testCase,Long.class);
        //then
        Assertions.assertEquals(afterPosts.toString(),postsRepository.findByPostID(1L).toString());
    }
}