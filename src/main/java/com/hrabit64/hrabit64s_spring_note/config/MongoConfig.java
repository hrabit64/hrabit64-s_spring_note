package com.hrabit64.hrabit64s_spring_note.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig{

    @Value("${spring.data.mongodb.uri}")
    private String connectionAddress;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;


    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString(connectionAddress);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .retryWrites(false)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), databaseName);
    }

}


