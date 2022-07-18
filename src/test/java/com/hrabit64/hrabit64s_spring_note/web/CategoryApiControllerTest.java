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
import com.hrabit64.hrabit64s_spring_note.web.dto.category.CategoryAddRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.category.CategoryResponseDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.category.CategoryUpdateRequestDto;
import com.hrabit64.hrabit64s_spring_note.web.dto.posts.PostsResponseDto;
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
class CategoryApiControllerTest {

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
    void findAllCategory() {
        //given
        Category testCategory1 = Category.builder()
                .categoryName("test 1")
                .index("this is test category")
                .build();

        categoryRepository.save(testCategory1);

        Category testCategory2 = Category.builder()
                .categoryName("test 2")
                .index("this is test category")
                .build();

        categoryRepository.save(testCategory2);

        testCategory1.setCategoryID(categoryService.findByCategoryName(testCategory1.getCategoryName()).getCategoryID());
        testCategory2.setCategoryID(categoryService.findByCategoryName(testCategory2.getCategoryName()).getCategoryID());

        List<CategoryResponseDto> testCase = new ArrayList<CategoryResponseDto>();
        testCase.add(new CategoryResponseDto(testCategory1));
        testCase.add(new CategoryResponseDto(testCategory2));

        logger.debug("given // {} \n {}",testCategory1.toString(),testCategory2.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/category";
        logger.debug("when // GET {}",url);
        ResponseEntity<CategoryResponseDto[]> responseEntity = testRestTemplate.getForEntity(url, CategoryResponseDto[].class);
        List<CategoryResponseDto> responseBody = Arrays.asList(responseEntity.getBody());

        //then
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertTrue(testUtils.isListSame(responseBody,testCase));

    }

    @Test
    void findPostsByCategoryID() {
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


        List<PostsResponseDto> testCase = new ArrayList<PostsResponseDto>();
        testCase.add(new PostsResponseDto(testPost1));
        testCase.add(new PostsResponseDto(testPost2));

        logger.debug("given // {} \n {}",testPost1.toString(),testPost2.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/category/"+testCategoryID+"/posts";
        logger.debug("when // GET {}",url);
        ResponseEntity<PostsResponseDto[]> responseEntity = testRestTemplate.getForEntity(url, PostsResponseDto[].class);
        List<PostsResponseDto> responseBody = Arrays.asList(responseEntity.getBody());

        //then
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertTrue(testUtils.isListSame(responseBody,testCase));
    }

    @Test
    void findByCategoryID() {
        //given
        Category testCategory = Category.builder()
                .categoryName("test")
                .index("this is test category")
                .build();

        categoryRepository.save(testCategory);
        String testCategoryID = categoryRepository.findByCategoryName("test").getCategoryID();
        testCategory.setCategoryID(testCategoryID);
        CategoryResponseDto testCase = new CategoryResponseDto(testCategory);

        logger.debug("given // {}",testCategory.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/category/" + testCategoryID;
        logger.debug("when // GET {}",url);
        ResponseEntity<CategoryResponseDto> responseEntity = testRestTemplate.getForEntity(url, CategoryResponseDto.class);
        CategoryResponseDto responseBody = responseEntity.getBody();

        //then
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(testCase.toString(),responseBody.toString());
    }

    @Test
    void addCategory() {
        //given
        Category testCategory = Category.builder()
                .categoryName("test")
                .index("this is test category")
                .build();


        CategoryAddRequestDto testCase = CategoryAddRequestDto.builder()
                .categoryName(testCategory.getCategoryName())
                .index(testCategory.getIndex())
                .build();

        logger.debug("given // {}",testCase.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/category";
        logger.debug("when // POST {}",url);
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url,testCase,String.class);
        Category foundCategory = categoryRepository.findByCategoryName("test");
        testCategory.setCategoryID(foundCategory.getCategoryID());
        //then
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        Assertions.assertEquals(testCategory.toString(),foundCategory.toString());
    }

    @Test
    void updateCategory() {
        //given
        Category beforeCategory = Category.builder()
                .categoryName("test")
                .index("this is test category")
                .build();

        categoryRepository.save(beforeCategory);

        Category afterCategory = categoryRepository.findByCategoryName("test");

        afterCategory.setIndex("this is changed category's index");
        afterCategory.setCategoryName("Sigong");
        CategoryUpdateRequestDto testCase = CategoryUpdateRequestDto.builder()
                .index(afterCategory.getIndex())
                .categoryName(afterCategory.getCategoryName())
                .build();

        logger.debug("given // before : {}\nafter : {}",beforeCategory.toString(),afterCategory.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/category/test";
        logger.debug("when // PUT {}",url);
        testRestTemplate.put(url, testCase,String.class);
        //then
        Assertions.assertEquals(afterCategory.toString(),categoryRepository.findByCategoryID(afterCategory.getCategoryID()).toString());
    }

    @Test
    void delCategory() {

        //given
        Category testCategory = Category.builder()
                .categoryName("test")
                .index("this is test category")
                .build();

        logger.debug("given // {}",testCategory.toString());

        //when
        String url = "http://localhost:" + port + "/api/v1/category/test";
        logger.debug("when // DEL {}",url);
        testRestTemplate.delete(url);

        //then
        Assertions.assertTrue(categoryRepository.findByCategoryName("test") == null);
    }
}