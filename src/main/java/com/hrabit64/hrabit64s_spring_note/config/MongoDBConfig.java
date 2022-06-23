//package com.hrabit64.hrabit64s_spring_note.config;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//
//import java.net.UnknownHostException;
//
//@Configuration
//public class MongoDBConfig {
//
//    @Value("${spring.data.mongodb.uri}")
//    private String connectionString;
//
//    @Bean
//    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
//        return new SimpleMongoDbFactory(new MongoClient(),connectionString);
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws UnknownHostException {
//
//        return new MongoTemplate(mongoDbFactory());
//
//    }
//}
