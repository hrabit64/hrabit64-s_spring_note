package com.hrabit64.hrabit64s_spring_note.domain.posts;

import com.hrabit64.hrabit64s_spring_note.domain.category.Category;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
public class PostsCustomRepositoryImpl implements PostsCustomRepository {

    @Autowired
    private final MongoTemplate mongoTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * find post by id
     * @param postID target post id
     * @return posts object
     */
    public Posts findByPostID(Long postID){
        Query query = new Query(where("_id").is(postID));
        return mongoTemplate.findOne(query,Posts.class);
    };


    /**
     * find category by category id
     * @param categoryID target category's category id
     * @return category objecct
     */
    public Category findCategoryByCategoryID(String categoryID){
        Query query = new Query(where("_id").is(new ObjectId(categoryID)));
        Category targetCategory =  mongoTemplate.findOne(query,Category.class);
        return targetCategory;
    }


    /**
     * add new post, and post's category's post id add target post
     * @param posts target post
     * @return new post's id
     */
    public Long addPosts(Posts posts){

        Query categoryQuery = new Query(where("_id").is(new ObjectId(posts.getCategoryID())));
        Category targetCategory =  mongoTemplate.findOne(categoryQuery,Category.class);
        targetCategory.getPostsID().add(posts.getPostID());
        Update categoryUpdate = Update.update("POST_POST_ID",targetCategory.getPostsID());
        mongoTemplate.updateFirst(categoryQuery,categoryUpdate,Category.class);

        return mongoTemplate.insert(posts).getPostID();
    }

    /**
     * del post and del post id from category
     * @param posts target post
     */
    public void delPosts(Posts posts){
        Query postsQuery = new Query(where("_id").is(posts.getPostID()));
        Query categoryQuery = new Query(where("_id").is(new ObjectId(posts.getCategoryID())));

        Category targetCategory =  mongoTemplate.findOne(categoryQuery,Category.class);
        targetCategory.getPostsID().remove(posts.getPostID());

        Update categoryUpdate = Update.update("POST_POST_ID",targetCategory.getPostsID());
        mongoTemplate.updateFirst(categoryQuery,categoryUpdate,Category.class);
        mongoTemplate.remove(postsQuery,Posts.class);

    }

    /**
     * update post
     * @param newPosts new post
     * @return new post
     */
    public Posts updatePosts(Posts newPosts){
        Query postsQuery = new Query(where("_id").is(newPosts.getPostID()));
        Update postsUpdate = new Update();
        postsUpdate.set("POST_TITLE",newPosts.getTitle());
        postsUpdate.set("CATEGORY_CATEGORY_ID",newPosts.getCategoryID());
        postsUpdate.set("POST_TAG",newPosts.getTags());
        postsUpdate.set("POST_CONTENT",newPosts.getContent());

        mongoTemplate.updateMulti(postsQuery,postsUpdate,Posts.class);


        return newPosts;
    }

    /**
     * update post and remove post's id from before category and add post's id from new category
     * @param newPosts new post
     * @param beforeCategoryID before category's ID
     * @return new post
     */
    public Posts updatePosts(Posts newPosts,String beforeCategoryID){

        updatePosts(newPosts);

        Query newCategoryQuery = new Query(where("_id").is(new ObjectId(newPosts.getCategoryID())));
        Query beforeCategoryQuery = new Query(where("_id").is(new ObjectId(beforeCategoryID)));

        Category newCategory =  mongoTemplate.findOne(newCategoryQuery,Category.class);
        newCategory.getPostsID().add(newPosts.getPostID());
        Update newCategoryUpdate = Update.update("POST_POST_ID",newCategory.getPostsID());
        mongoTemplate.updateFirst(newCategoryQuery,newCategoryUpdate,Category.class);

        Category beforeCategory = mongoTemplate.findOne(beforeCategoryQuery,Category.class);
        beforeCategory.getPostsID().remove(newPosts.getPostID());
        Update beforeCategoryUpdate = Update.update("POST_POST_ID",beforeCategory.getPostsID());
        mongoTemplate.updateFirst(beforeCategoryQuery,beforeCategoryUpdate,Category.class);

        return newPosts;
    }
    public void delAllPosts(){
        mongoTemplate.remove(new Query(),Posts.class);
    }
}
