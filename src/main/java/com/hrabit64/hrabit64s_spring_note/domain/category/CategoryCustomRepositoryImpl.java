package com.hrabit64.hrabit64s_spring_note.domain.category;

import com.hrabit64.hrabit64s_spring_note.domain.posts.Posts;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

    @Autowired
    private final MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * find posts by category id
     * @param categoryID target category's id
     * @return posts ojbect
     */
    public List<Posts> findAllPostsByCategoryID(String categoryID){
//        MatchOperation matchOperation = Aggregation.match(Criteria.where("CATEGORY_CATEGORY_NM").is(categoryName));
//        LookupOperation lookupOperation = Aggregation.lookup("posts", "CATEGORY_CATEGORY_ID", "POST_POST_ID", "posts_info");
//        UnwindOperation unwindOperation = Aggregation.unwind("POST_POST_ID");
//        Aggregation aggregation = Aggregation.newAggregation(matchOperation,lookupOperation,unwindOperation);
//        List<Posts> results = mongoTemplate.aggregate(aggregation, "posts", Posts.class).getMappedResults();
//        return results;
        Query query = new Query(where("CATEGORY_CATEGORY_ID").is(categoryID));
        List<Posts> posts = mongoTemplate.find(query,Posts.class);
        return posts;
    }


    /**
     * find category by category's name
     * @param categoryName target category's name
     * @return category object
     */
    @Override
    public Category findByCategoryName(String categoryName) {
        Query query = new Query(where("CATEGORY_CATEGORY_NM").is(categoryName));
        Category category = mongoTemplate.findOne(query,Category.class);
        return category;
    }

    /**
     * find category by category's id
     * @param categoryID target category's id
     * @return category object
     */
    public Category findByCategoryID(String categoryID) {
        Query query = new Query(where("_id").is(new ObjectId(categoryID)));
        Category category = mongoTemplate.findOne(query,Category.class);
        return category;
    }

    /**
     * update category
     * @param newCategory new category
     * @return new category
     */
    public Category updateCategory(Category newCategory){
        Query query = new Query(where("_id").is(new ObjectId(newCategory.getCategoryID())));
        Update update = new Update();
        update.set("CATEGORY_CATEGORY_NM",newCategory.getCategoryName());
        update.set("CATEGORY_INDEX",newCategory.getIndex());
        mongoTemplate.updateFirst(query,update,Category.class);
        return newCategory;
    }

    public void delCategory(Category category){
        Query query = new Query(where("_id").is(new ObjectId(category.getCategoryID())));
        mongoTemplate.remove(query,Category.class);

    }

    public void delAllCategory(){
        mongoTemplate.remove(new Query(),Category.class);
    }

}
